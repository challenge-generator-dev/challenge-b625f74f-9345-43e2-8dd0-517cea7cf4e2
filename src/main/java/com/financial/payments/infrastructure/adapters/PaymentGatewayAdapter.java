package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.PaymentGatewayPort;
import com.financial.payments.infrastructure.exceptions.PaymentGatewayServiceException;
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
public class PaymentGatewayAdapter implements PaymentGatewayPort {

    private static final Logger LOGGER = Logger.getLogger(PaymentGatewayAdapter.class.getName());

    @ConfigProperty(name = "paymentgateway.api.url", defaultValue = "http://localhost:8082/api")
    String gatewayApiUrl;

    @ConfigProperty(name = "paymentgateway.api.timeout", defaultValue = "10000")
    long apiTimeout;

    @ConfigProperty(name = "paymentgateway.api.key", defaultValue = "default-api-key")
    String apiKey;

    @ConfigProperty(name = "paymentgateway.max-amount", defaultValue = "100000")
    BigDecimal maxAmount;

    private final Map<String, GatewayTransaction> transactionCache = new ConcurrentHashMap<>();

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "processPaymentFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<GatewayResponse> processPayment(Payment payment) {
        LOGGER.infof("Procesando pago en gateway: %s", payment.transactionReference());

        return Uni.createFrom().item(() -> {
            if (payment.amount().compareTo(maxAmount) > 0) {
                throw new PaymentGatewayServiceException(
                    "Monto excede el limite maximo del gateway: " + maxAmount,
                    "AMOUNT_EXCEEDS_LIMIT",
                    400
                );
            }

            String gatewayTransactionId = UUID.randomUUID().toString();
            Instant timestamp = Instant.now();

            GatewayTransaction transaction = new GatewayTransaction(
                gatewayTransactionId,
                payment.transactionReference(),
                payment.amount(),
                payment.currency(),
                TransactionStatus.PROCESSED,
                timestamp
            );

            transactionCache.put(gatewayTransactionId, transaction);

            LOGGER.infof("Pago procesado exitosamente en gateway. ID: %s", gatewayTransactionId);

            return new GatewayResponse(
                true,
                gatewayTransactionId,
                timestamp,
                "Pago procesado exitosamente",
                "APPROVED",
                "00"
            );
        });
    }

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "refundPaymentFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<GatewayResponse> refundPayment(String gatewayTransactionId, BigDecimal amount, String reason) {
        LOGGER.infof("Iniciando reembolso en gateway. ID: %s, Monto: %s",
            gatewayTransactionId, amount);

        return Uni.createFrom().item(() -> {
            GatewayTransaction original = transactionCache.get(gatewayTransactionId);

            if (original == null) {
                throw new PaymentGatewayServiceException(
                    "Transaccion no encontrada en gateway: " + gatewayTransactionId,
                    "TRANSACTION_NOT_FOUND",
                    404
                );
            }

            if (amount.compareTo(original.amount()) > 0) {
                throw new PaymentGatewayServiceException(
                    "Monto de reembolso excede el monto original",
                    "REFUND_AMOUNT_EXCEEDS",
                    400
                );
            }

            String refundId = UUID.randomUUID().toString();
            Instant timestamp = Instant.now();

            LOGGER.infof("Reembolso procesado. ID: %s", refundId);

            return new GatewayResponse(
                true,
                refundId,
                timestamp,
                "Reembolso aplicado: " + reason,
                "REFUNDED",
                "00"
            );
        });
    }

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "getTransactionStatusFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<GatewayResponse> getTransactionStatus(String gatewayTransactionId) {
        LOGGER.infof("Consultando estado de transaccion en gateway: %s", gatewayTransactionId);

        return Uni.createFrom().item(() -> {
            GatewayTransaction transaction = transactionCache.get(gatewayTransactionId);

            if (transaction == null) {
                throw new PaymentGatewayServiceException(
                    "Transaccion no encontrada: " + gatewayTransactionId,
                    "TRANSACTION_NOT_FOUND",
                    404
                );
            }

            String statusCode = mapStatusToCode(transaction.status());

            return new GatewayResponse(
                true,
                gatewayTransactionId,
                transaction.timestamp(),
                "Estado: " + transaction.status(),
                transaction.status().name(),
                statusCode
            );
        });
    }

    @Override
    @CircuitBreaker(name = "paymentGatewayCircuitBreaker", fallbackMethod = "validateCardFallback")
    @Retry(name = "paymentGatewayRetry")
    public Uni<CardValidationResult> validateCard(String cardNumber, String cvv, String expiryDate) {
        LOGGER.infof("Validando tarjeta para autenticacion");

        return Uni.createFrom().item(() -> {
            if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
                throw new PaymentGatewayServiceException(
                    "Numero de tarjeta invalido",
                    "INVALID_CARD",
                    400
                );
            }

            if (cvv == null || cvv.length() < 3 || cvv.length() > 4) {
                throw new PaymentGatewayServiceException(
                    "CVV invalido",
                    "INVALID_CVV",
                    400
                );
            }

            boolean isValid = luhnCheck(cardNumber);

            return new CardValidationResult(
                isValid,
                isValid ? "Tarjeta valida" : "Tarjeta invalida segun algoritmo de Luhn",
                detectCardType(cardNumber)
            );
        });
    }

    private Uni<GatewayResponse> processPaymentFallback(Payment payment, Throwable t) {
        LOGGER.warnf("Fallback activado para processPayment. Pago: %s, Error: %s",
            payment.transactionReference(), t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "Gateway de pagos no disponible despues de reintentos",
            "SERVICE_UNAVAILABLE",
            503
        ));
    }

    private Uni<GatewayResponse> refundPaymentFallback(String gatewayTransactionId,
                                                        BigDecimal amount, String reason, Throwable t) {
        LOGGER.warnf("Fallback activado para refundPayment. ID: %s, Error: %s",
            gatewayTransactionId, t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "No se pudo procesar el reembolso en el gateway",
            "REFUND_FAILED",
            500
        ));
    }

    private Uni<GatewayResponse> getTransactionStatusFallback(String gatewayTransactionId, Throwable t) {
        LOGGER.warnf("Fallback activado para getTransactionStatus. ID: %s, Error: %s",
            gatewayTransactionId, t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "No se pudo consultar el estado de la transaccion",
            "STATUS_QUERY_FAILED",
            503
        ));
    }

    private Uni<CardValidationResult> validateCardFallback(String cardNumber, String cvv,
                                                            String expiryDate, Throwable t) {
        LOGGER.warnf("Fallback activado para validateCard. Error: %s", t.getMessage());

        return Uni.createFrom().failure(new PaymentGatewayServiceException(
            "No se pudo validar la tarjeta",
            "VALIDATION_FAILED",
            503
        ));
    }

    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10) == 0;
    }

    private String detectCardType(String cardNumber) {
        String prefix = cardNumber.substring(0, 1);

        return switch (prefix) {
            case "4" -> "VISA";
            case "5" -> "MASTERCARD";
            case "3" -> "AMEX";
            case "6" -> "DISCOVER";
            default -> "UNKNOWN";
        };
    }

    private String mapStatusToCode(TransactionStatus status) {
        return switch (status) {
            case PROCESSED -> "00";
            case PENDING -> "01";
            case FAILED -> "02";
            case REFUNDED -> "03";
            case CANCELLED -> "04";
        };
    }

    public record GatewayResponse(
        boolean success,
        String transactionId,
        Instant timestamp,
        String message,
        String status,
        String code
    ) {}

    public record CardValidationResult(
        boolean isValid,
        String message,
        String cardType
    ) {}

    public record GatewayTransaction(
        String gatewayTransactionId,
        String paymentReference,
        BigDecimal amount,
        String currency,
        TransactionStatus status,
        Instant timestamp
    ) {}

    public enum TransactionStatus {
        PENDING,
        PROCESSED,
        FAILED,
        REFUNDED,
        CANCELLED
    }
}