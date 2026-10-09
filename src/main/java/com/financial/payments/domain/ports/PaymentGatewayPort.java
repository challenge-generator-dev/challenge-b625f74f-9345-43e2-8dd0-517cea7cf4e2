package com.financial.payments.domain.ports;

import com.financial.payments.domain.model.Payment;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el gateway de pagos externo.
 * Maneja la comunicación con proveedores de pago para procesar transacciones.
 */
public interface PaymentGatewayPort {

    /**
     * Procesa un pago a través del gateway externo.
     * @param payment Información completa del pago
     * @return Uni con el resultado del procesamiento
     */
    Uni<PaymentGatewayResult> processPayment(Payment payment);

    /**
     * Consulta el estado de una transacción previamente procesada.
     * @param transactionReference Referencia de la transacción en el gateway
     * @return Uni con el estado actual de la transacción
     */
    Uni<PaymentGatewayStatus> getTransactionStatus(String transactionReference);

    /**
     * Cancela una transacción pendiente en el gateway.
     * @param transactionReference Referencia de la transacción a cancelar
     * @return Uni con true si la cancelación fue exitosa
     */
    Uni<Boolean> cancelTransaction(String transactionReference);

    /**
     * Reembolsa una transacción previamente procesada.
     * @param transactionReference Referencia de la transacción a reembolsar
     * @param amount Monto a reembolsar
     * @return Uni con true si el reembolso fue exitoso
     */
    Uni<Boolean> refundTransaction(String transactionReference, java.math.BigDecimal amount);

    /**
     * Resultado del procesamiento de un pago en el gateway.
     */
    record PaymentGatewayResult(
        String transactionId,
        PaymentGatewayStatus status,
        String authorizationCode,
        String responseMessage,
        java.time.Instant processedAt
    ) {}

    /**
     * Estados posibles de una transacción en el gateway de pagos.
     */
    enum PaymentGatewayStatus {
        PENDING,
        AUTHORIZED,
        CAPTURED,
        DECLINED,
        CANCELLED,
        REFUNDED,
        FAILED
    }
}