package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.CoreBankingPort;
import com.financial.payments.infrastructure.exceptions.CoreBankingServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CoreBankingAdapter implements CoreBankingPort {

    private static final Logger LOGGER = Logger.getLogger(CoreBankingAdapter.class.getName());

    @ConfigProperty(name = "corebanking.api.url", defaultValue = "http://localhost:8081/api")
    String coreBankingApiUrl;

    @ConfigProperty(name = "corebanking.api.timeout", defaultValue = "5000")
    long apiTimeout;

    @ConfigProperty(name = "corebanking.api.max-connections", defaultValue = "100")
    int maxConnections;

    private final Map<String, CoreBankingAccount> accountCache = new ConcurrentHashMap<>();

    @Override
    @CircuitBreaker(name = "coreBankingCircuitBreaker", fallbackMethod = "creditAccountFallback")
    @Retry(name = "coreBankingRetry")
    public Uni<CoreBankingResult> creditAccount(Payment payment) {
        LOGGER.infof("Iniciando acreditacion para pago: %s", payment.transactionReference());

        return Uni.createFrom().item(() -> {
            String accountNumber = payment.destinationAccount();
            BigDecimal amount = payment.amount();

            CoreBankingAccount account = accountCache.get(accountNumber);
            if (account == null) {
                account = new CoreBankingAccount(accountNumber, BigDecimal.ZERO, AccountStatus.ACTIVE);
                accountCache.put(accountNumber, account);
            }

            if (account.status() != AccountStatus.ACTIVE) {
                throw new CoreBankingServiceException(
                    "Cuenta no esta activa para acreditacion: " + accountNumber,
                    "ACCOUNT_NOT_ACTIVE",
                    400
                );
            }

            BigDecimal newBalance = account.balance().add(amount);
            CoreBankingAccount updatedAccount = new CoreBankingAccount(
                accountNumber,
                newBalance,
                AccountStatus.ACTIVE
            );
            accountCache.put(accountNumber, updatedAccount);

            String coreReference = UUID.randomUUID().toString();
            Instant timestamp = Instant.now();

            LOGGER.infof("Acreditacion exitosa. Referencia core: %s, Nuevo balance: %s",
                coreReference, newBalance);

            return new CoreBankingResult(
                true,
                coreReference,
                timestamp,
                "Credito aplicado exitosamente",
                newBalance
            );
        });
    }

    @Override
    @CircuitBreaker(name = "coreBankingCircuitBreaker", fallbackMethod = "reverseCreditFallback")
    @Retry(name = "coreBankingRetry")
    public Uni<CoreBankingResult> reverseCredit(String coreReference, String reason) {
        LOGGER.infof("Iniciando reversa para referencia core: %s", coreReference);

        return Uni.createFrom().item(() -> {
            if (coreReference == null || coreReference.isBlank()) {
                throw new CoreBankingServiceException(
                    "Referencia de core invalida para reversa",
                    "INVALID_REFERENCE",
                    400
                );
            }

            Instant timestamp = Instant.now();

            LOGGER.infof("Reversa exitosa para referencia: %s", coreReference);

            return new CoreBankingResult(
                true,
                "REV-" + coreReference,
                timestamp,
                "Reversa aplicada: " + reason,
                BigDecimal.ZERO
            );
        });
    }

    @Override
    @CircuitBreaker(name = "coreBankingCircuitBreaker", fallbackMethod = "getAccountBalanceFallback")
    @Retry(name = "coreBankingRetry")
    public Uni<CoreBankingAccount> getAccountBalance(String accountNumber) {
        LOGGER.infof("Consultando balance para cuenta: %s", accountNumber);

        return Uni.createFrom().item(() -> {
            CoreBankingAccount account = accountCache.get(accountNumber);
            if (account == null) {
                account = new CoreBankingAccount(accountNumber, BigDecimal.ZERO, AccountStatus.ACTIVE);
                accountCache.put(accountNumber, account);
            }

            LOGGER.infof("Balance consultado: %s", account.balance());
            return account;
        });
    }

    private Uni<CoreBankingResult> creditAccountFallback(Payment payment, Throwable t) {
        LOGGER.warnf("Fallback activado para creditAccount. Pago: %s, Error: %s",
            payment.transactionReference(), t.getMessage());

        return Uni.createFrom().failure(new CoreBankingServiceException(
            "Core bancario no disponible despues de reintentos",
            "SERVICE_UNAVAILABLE",
            503
        ));
    }

    private Uni<CoreBankingResult> reverseCreditFallback(String coreReference, String reason, Throwable t) {
        LOGGER.warnf("Fallback activado para reverseCredit. Referencia: %s, Error: %s",
            coreReference, t.getMessage());

        return Uni.createFrom().failure(new CoreBankingServiceException(
            "No se pudo reversar el credito en el core bancario",
            "REVERSAL_FAILED",
            500
        ));
    }

    private Uni<CoreBankingAccount> getAccountBalanceFallback(String accountNumber, Throwable t) {
        LOGGER.warnf("Fallback activado para getAccountBalance. Cuenta: %s, Error: %s",
            accountNumber, t.getMessage());

        return Uni.createFrom().failure(new CoreBankingServiceException(
            "No se pudo consultar el balance de la cuenta",
            "BALANCE_QUERY_FAILED",
            503
        ));
    }

    public record CoreBankingResult(
        boolean success,
        String coreReference,
        Instant timestamp,
        String message,
        BigDecimal newBalance
    ) {}

    public record CoreBankingAccount(
        String accountNumber,
        BigDecimal balance,
        AccountStatus status
    ) {
        public CoreBankingAccount {
            if (accountNumber == null || accountNumber.isBlank()) {
                throw new IllegalArgumentException("Numero de cuenta no puede ser nulo o vacio");
            }
            if (balance == null) {
                throw new IllegalArgumentException("Balance no puede ser nulo");
            }
            if (status == null) {
                throw new IllegalArgumentException("Estado de cuenta no puede ser nulo");
            }
        }
    }

    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        BLOCKED,
        CLOSED
    }
}