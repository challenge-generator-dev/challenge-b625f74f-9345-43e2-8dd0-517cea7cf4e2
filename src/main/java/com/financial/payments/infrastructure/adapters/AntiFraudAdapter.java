package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AntiFraudAdapter implements AntiFraudPort {

    private static final Logger LOG = Logger.getLogger(AntiFraudAdapter.class);
    private static final String CIRCUIT_BREAKER_NAME = "antiFraudCircuitBreaker";
    private static final String RETRY_NAME = "antiFraudRetry";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final BigDecimal HIGH_RISK_THRESHOLD = new BigDecimal("50000");
    private static final BigDecimal SUSPICIOUS_AMOUNT = new BigDecimal("10000");

    private final Random random = new Random();
    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();

    @ConfigProperty(name = "antifraud.service.url", defaultValue = "http://localhost:8081")
    String serviceUrl;

    @ConfigProperty(name = "antifraud.service.timeout", defaultValue = "5000")
    long timeoutMs;

    @ConfigProperty(name = "antifraud.service.mock.enabled", defaultValue = "true")
    boolean mockEnabled;

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "verifyFraudRiskFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Boolean> verifyFraudRisk(Payment payment) {
        LOG.infof("Verificando riesgo de fraude para transacción: %s", payment.transactionReference());
        incrementRequestCount(payment.accountOrigin());

        if (mockEnabled) {
            return mockVerifyFraudRisk(payment);
        }

        return invokeExternalAntiFraudService(payment)
                .onFailure(this::isTransientError).recoverWithUni(this::handleTransientFailure)
                .onFailure().recoverWithItem(this::handlePermanentFailure);
    }

    private Uni<Boolean> mockVerifyFraudRisk(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            if (payment.amount().compareTo(HIGH_RISK_THRESHOLD) > 0) {
                LOG.warnf("Monto alto detectado: %s para transacción %s", 
                        payment.amount(), payment.transactionReference());
                return Boolean.TRUE;
            }
            
            if (payment.amount().compareTo(SUSPICIOUS_AMOUNT) > 0 && random.nextInt(10) < 3) {
                LOG.warnf("Monto sospechoso y patrón aleatorio: %s", payment.transactionReference());
                return Boolean.TRUE;
            }
            
            int requestCount = requestCounts.getOrDefault(payment.accountOrigin(), 0);
            if (requestCount > 10) {
                LOG.warnf("Exceso de solicitudes desde cuenta: %s", payment.accountOrigin());
                return Boolean.TRUE;
            }
            
            return Boolean.FALSE;
        });
    }

    private Uni<Boolean> invokeExternalAntiFraudService(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de antifraude: %s", serviceUrl);
            return Boolean.FALSE;
        });
    }

    private void simulateLatency() {
        try {
            Thread.sleep(random.nextInt(100) + 50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isTransientError(Throwable t) {
        return t instanceof java.io.IOException
                || t instanceof java.util.concurrent.TimeoutException
                || (t.getCause() != null && t.getCause() instanceof java.io.IOException);
    }

    private Uni<Boolean> handleTransientFailure(Throwable failure) {
        LOG.warnf("Error transitorio en servicio antifraude, reintentando: %s", failure.getMessage());
        return Uni.createFrom().failure(
                new AntiFraudServiceException("Error transitorio en servicio antifraude", 
                        failure.getMessage(), paymentFromError(failure)));
    }

    private Boolean handlePermanentFailure(Throwable failure) {
        LOG.errorf("Error permanente en servicio antifraude: %s", failure.getMessage());
        return Boolean.FALSE;
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getRiskScoreFallback")
    @Retry(name = RETRY_NAME, maxAttempts = MAX_RETRY_ATTEMPTS)
    public Uni<Integer> getRiskScore(Payment payment) {
        LOG.infof("Obteniendo score de riesgo para transacción: %s", payment.transactionReference());
        
        if (mockEnabled) {
            return mockGetRiskScore(payment);
        }

        return invokeExternalRiskScoreService(payment);
    }

    private Uni<Integer> mockGetRiskScore(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            
            int baseScore = random.nextInt(50);
            
            if (payment.amount().compareTo(HIGH_RISK_THRESHOLD) > 0) {
                baseScore += 40;
            } else if (payment.amount().compareTo(SUSPICIOUS_AMOUNT) > 0) {
                baseScore += 20;
            }
            
            int requestCount = requestCounts.getOrDefault(payment.accountOrigin(), 0);
            if (requestCount > 5) {
                baseScore += Math.min(requestCount * 2, 30);
            }
            
            return Math.min(baseScore, 100);
        });
    }

    private Uni<Integer> invokeExternalRiskScoreService(Payment payment) {
        return Uni.createFrom().item(() -> {
            simulateLatency();
            LOG.debugf("Invocando servicio externo de score de riesgo: %s", serviceUrl);
            return 0;
        });
    }

    private void incrementRequestCount(String accountNumber) {
        requestCounts.merge(accountNumber, 1, Integer::sum);
    }

    private Payment paymentFromError(Throwable t) {
        if (t instanceof AntiFraudServiceException) {
            return ((AntiFraudServiceException) t).getPayment();
        }
        return null;
    }

    private Uni<Boolean> verifyFraudRiskFallback(Payment payment, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para verifyFraudRisk: %s", failure.getMessage());
        if (failure instanceof CallNotPermittedException) {
            return Uni.createFrom().failure(
                    new AntiFraudServiceException("Servicio antifraude no disponible temporalmente", 
                            payment.transactionReference()));
        }
        return Uni.createFrom().item(Boolean.FALSE);
    }

    private Uni<Integer> getRiskScoreFallback(Payment payment, Throwable failure) {
        LOG.errorf("Fallback de circuit breaker activado para getRiskScore: %s", failure.getMessage());
        return Uni.createFrom().item(50);
    }
}