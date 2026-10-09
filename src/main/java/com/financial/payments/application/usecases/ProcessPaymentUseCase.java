package com.financial.payments.application.usecases;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.domain.ports.RiskBureauPort;
import com.financial.payments.domain.ports.RiskBureauPort.RiskLevel;
import com.financial.payments.infrastructure.adapters.PaymentRepository;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import com.financial.payments.infrastructure.exceptions.RiskBureauServiceException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProcessPaymentUseCase {

    private static final Logger LOG = Logger.getLogger(ProcessPaymentUseCase.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final String CIRCUIT_BREAKER_NAME = "paymentCircuitBreaker";

    private final AntiFraudPort antiFraudPort;
    private final RiskBureauPort riskBureauPort;
    private final PaymentRepository paymentRepository;

    @Inject
    public ProcessPaymentUseCase(
            AntiFraudPort antiFraudPort,
            RiskBureauPort riskBureauPort,
            PaymentRepository paymentRepository) {
        this.antiFraudPort = antiFraudPort;
        this.riskBureauPort = riskBureauPort;
        this.paymentRepository = paymentRepository;
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "processPaymentFallback")
    @Retry(name = "paymentRetry", maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Payment> processPayment(Payment payment) {
        LOG.infof("Iniciando procesamiento de pago: %s", payment.transactionReference());
        
        return validatePayment(payment)
                .onFailure().recoverWithUni(this::handleValidationFailure)
                .chain(this::checkAntiFraud)
                .onFailure(this::isRecoverableFraudError).recoverWithUni(this::handleAntiFraudFailure)
                .chain(this::checkRiskBureau)
                .onFailure(this::isRecoverableRiskError).recoverWithUni(this::handleRiskBureauFailure)
                .chain(this::savePayment)
                .onItem().invoke(p -> LOG.infof("Pago procesado exitosamente: %s", p.transactionReference()));
    }

    private Uni<Payment> validatePayment(Payment payment) {
        if (payment == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Payment no puede ser nulo"));
        }
        if (payment.accountOrigin() == null || payment.accountOrigin().isBlank()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Cuenta origen es requerida"));
        }
        if (payment.accountDestination() == null || payment.accountDestination().isBlank()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Cuenta destino es requerida"));
        }
        if (payment.amount() == null || payment.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return Uni.createFrom().failure(new IllegalArgumentException("Monto debe ser mayor a cero"));
        }
        return Uni.createFrom().item(payment);
    }

    private Uni<Payment> handleValidationFailure(Throwable failure) {
        LOG.errorf("Validación de pago fallida: %s", failure.getMessage());
        return Uni.createFrom().failure(failure);
    }

    private Uni<Payment> checkAntiFraud(Payment payment) {
        LOG.debugf("Verificando riesgo de fraude para pago: %s", payment.transactionReference());
        return antiFraudPort.verifyFraudRisk(payment)
                .onItem().transformToUni(isFraudulent -> {
                    if (Boolean.TRUE.equals(isFraudulent)) {
                        LOG.warnf("Pago marcado como fraudulento: %s", payment.transactionReference());
                        return Uni.createFrom().failure(
                                new AntiFraudServiceException("Pago rechazado por motor antifraude", payment.transactionReference()));
                    }
                    return Uni.createFrom().item(payment);
                });
    }

    private boolean isRecoverableFraudError(Throwable t) {
        return t instanceof AntiFraudServiceException 
                || t instanceof CallNotPermittedException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<Payment> handleAntiFraudFailure(Throwable failure) {
        LOG.errorf("Error en verificación antifraude: %s", failure.getMessage());
        if (failure instanceof AntiFraudServiceException) {
            Payment failedPayment = ((AntiFraudServiceException) failure).getPayment();
            return paymentRepository.save(failedPayment.withStatus(PaymentStatus.REJECTED));
        }
        return Uni.createFrom().failure(failure);
    }

    private Uni<Payment> checkRiskBureau(Payment payment) {
        LOG.debugf("Consultando buró de riesgos para cuenta: %s", payment.accountOrigin());
        return riskBureauPort.getAccountRiskLevel(payment.accountOrigin())
                .onItem().transformToUni(riskLevel -> {
                    if (riskLevel == RiskLevel.HIGH) {
                        LOG.warnf("Cuenta con riesgo alto: %s", payment.accountOrigin());
                        return Uni.createFrom().failure(
                                new RiskBureauServiceException("Cuenta con nivel de riesgo alto", payment.accountOrigin()));
                    }
                    return checkAccountRestrictions(payment);
                });
    }

    private Uni<Payment> checkAccountRestrictions(Payment payment) {
        return riskBureauPort.hasAccountRestrictions(payment.accountOrigin())
                .onItem().transformToUni(hasRestrictions -> {
                    if (Boolean.TRUE.equals(hasRestrictions)) {
                        LOG.warnf("Cuenta con restricciones: %s", payment.accountOrigin());
                        return Uni.createFrom().failure(
                                new RiskBureauServiceException("Cuenta con restricciones activas", payment.accountOrigin()));
                    }
                    return Uni.createFrom().item(payment);
                });
    }

    private boolean isRecoverableRiskError(Throwable t) {
        return t instanceof RiskBureauServiceException
                || t instanceof CallNotPermittedException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<Payment> handleRiskBureauFailure(Throwable failure) {
        LOG.errorf("Error en verificación de buró de riesgos: %s", failure.getMessage());
        if (failure instanceof RiskBureauServiceException) {
            Payment failedPayment = ((RiskBureauServiceException) failure).getAccountNumber();
            return paymentRepository.save(failedPayment.withStatus(PaymentStatus.REJECTED));
        }
        return Uni.createFrom().failure(failure);
    }

    private Uni<Payment> savePayment(Payment payment) {
        LOG.debugf("Guardando pago en repositorio: %s", payment.transactionReference());
        return paymentRepository.save(payment.withStatus(PaymentStatus.PROCESSED));
    }

    private Uni<Payment> processPaymentFallback(Payment payment, Throwable failure) {
        LOG.errorf("Fallback activado para pago %s: %s", payment.transactionReference(), failure.getMessage());
        Payment failedPayment = payment.withIncrementedRetry(failure.getMessage());
        return paymentRepository.save(failedPayment.withStatus(PaymentStatus.PENDING_RETRY))
                .onItem().invoke(p -> LOG.warnf("Pago guardado con estado PENDING_RETRY: %s", p.transactionReference()));
    }
}