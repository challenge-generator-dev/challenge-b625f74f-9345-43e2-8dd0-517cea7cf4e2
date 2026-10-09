package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.ports.RiskBureauPort;
import com.financial.payments.domain.ports.RiskBureauPort.RiskLevel;
import com.financial.payments.infrastructure.exceptions.RiskBureauServiceException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class RiskBureauAdapter implements RiskBureauPort {

    private static final Logger LOG = Logger.getLogger(RiskBureauAdapter.class);
    private static final String CIRCUIT_BREAKER_NAME = "riskBureauCircuitBreaker";
    private static final String RETRY_NAME = "riskBureauRetry";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Set<String> RESTRICTED_ACCOUNTS = Set.of(
            "ACC-RESTRICTED-001",
            "ACC-RESTRICTED-002",
            "ACC-FRAUD-001"
    );
    private static final Set<String> HIGH_RISK_ACCOUNTS = Set.of(
            "ACC-HIGH-RISK-001",
            "ACC-HIGH-RISK-002",
            "ACC-SUSPICIOUS-001"
    );

    private final Random random = new Random();
    private final Map<String, RiskLevel> riskCache = new ConcurrentHashMap<>();
    private final Map<String, Boolean> restrictionsCache = new ConcurrentHashMap<>();

    @ConfigProperty(name = "riskbureau.service.url", defaultValue = "http://localhost:8082")
    String serviceUrl;

    @ConfigProperty(name = "riskbureau.service.timeout", defaultValue = "5000")
    long timeoutMs;

    @ConfigProperty(name = "riskbureau.service.mock.enabled", defaultValue = "true")
    boolean mockEnabled;

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAccountRiskLevelFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<RiskLevel> getAccountRiskLevel(String accountNumber) {
        LOG.infof("Consultando nivel de riesgo para cuenta: %s", accountNumber);

        if (mockEnabled) {
            return mockGetAccountRiskLevel(accountNumber);
        }

        return invokeExternalRiskBureauService(accountNumber)
                .onFailure(this::isTransientError).recoverWithUni(this::handleTransientFailure)
                .onFailure().recoverWithItem(this::handlePermanentFailure);
    }

    private Uni<RiskLevel> mockGetAccountRiskLevel(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            if (riskCache.containsKey(accountNumber)) {
                LOG.debugf("Retornando riesgo desde cache para cuenta: %s", accountNumber);
                return riskCache.get(accountNumber);
            }
            
            if (HIGH_RISK_ACCOUNTS.contains(accountNumber)) {
                LOG.warnf("Cuenta de alto riesgo detectada: %s", accountNumber);
                return RiskLevel.HIGH;
            }
            
            if (RESTRICTED_ACCOUNTS.contains(accountNumber)) {
                LOG.warnf("Cuenta con restricciones previas: %s", accountNumber);
                return RiskLevel.HIGH;
            }
            
            int riskRoll = random.nextInt(100);
            RiskLevel level;
            if (riskRoll < 10) {
                level = RiskLevel.HIGH;
            } else if (riskRoll < 30) {
                level = RiskLevel.MEDIUM;
            } else {
                level = RiskLevel.LOW;
            }
            
            riskCache.put(accountNumber, level);
            LOG.infof("Nivel de riesgo determinado para cuenta %s: %s", accountNumber, level);
            return level;
        });
    }

    private Uni<RiskLevel> invokeExternalRiskBureauService(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de buró de riesgos: %s", serviceUrl);
            return RiskLevel.LOW;
        });
    }

    private void simulateLatency() {
        try {
            Thread.sleep(random.nextInt(150) + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isTransientError(Throwable t) {
        return t instanceof java.io.IOException
                || t instanceof java.util.concurrent.TimeoutException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<RiskLevel> handleTransientFailure(Throwable failure) {
        LOG.warnf("Error transitorio en buró de riesgos, reintentando: %s", failure.getMessage());
        return Uni.createFrom().failure(
                new RiskBureauServiceException("Error transitorio en servicio de buró de riesgos", 
                        accountFromError(failure)));
    }

    private RiskLevel handlePermanentFailure(Throwable failure) {
        LOG.errorf("Error permanente en buró de riesgos: %s", failure.getMessage());
        return RiskLevel.MEDIUM;
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "hasAccountRestrictionsFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Boolean> hasAccountRestrictions(String accountNumber) {
        LOG.infof("Verificando restricciones para cuenta: %s", accountNumber);

        if (mockEnabled) {
            return mockHasAccountRestrictions(accountNumber);
        }

        return invokeExternalRestrictionsService(accountNumber);
    }

    private Uni<Boolean> mockHasAccountRestrictions(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            if (restrictionsCache.containsKey(accountNumber)) {
                LOG.debugf("Retornando restricciones desde cache para cuenta: %s", accountNumber);
                return restrictionsCache.get(accountNumber);
            }
            
            boolean hasRestrictions = RESTRICTED_ACCOUNTS.contains(accountNumber);
            
            if (!hasRestrictions && random.nextInt(100) < 5) {
                hasRestrictions = true;
            }
            
            restrictionsCache.put(accountNumber, hasRestrictions);
            LOG.infof("Restricciones para cuenta %s: %s", accountNumber, hasRestrictions);
            return hasRestrictions;
        });
    }

    private Uni<Boolean> invokeExternalRestrictionsService(String accountNumber) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de restricciones: %s", serviceUrl);
            return Boolean.FALSE;
        });
    }

    private String accountFromError(Throwable t) {
        if (t instanceof RiskBureauServiceException) {
            return ((RiskBureauServiceException) t).getAccountNumber();
        }
        return null;
    }

    private Uni<RiskLevel> getAccountRiskLevelFallback(String accountNumber, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para getAccountRiskLevel: %s", failure.getMessage());
        if (failure instanceof CallNotPermittedException) {
            return Uni.createFrom().failure(
                    new RiskBureauServiceException("Buró de riesgos no disponible temporalmente", accountNumber));
        }
        return Uni.createFrom().item(RiskLevel.MEDIUM);
    }

    private Uni<Boolean> hasAccountRestrictionsFallback(String accountNumber, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para hasAccountRestrictions: %s", failure.getMessage());
        if (failure instanceof CallNotPermittedException) {
            return Uni.createFrom().failure(
                    new RiskBureauServiceException("Buró de riesgos no disponible temporalmente", accountNumber));
        }
        return Uni.createFrom().item(Boolean.FALSE);
    }
}