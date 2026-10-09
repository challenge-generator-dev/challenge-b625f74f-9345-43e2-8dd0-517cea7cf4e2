package com.financial.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de dominio que representa una solicitud de pago.
 * Contiene los atributos esenciales del pago y su estado actual.
 */
public record Payment(
    UUID id,
    String sourceAccount,
    String destinationAccount,
    BigDecimal amount,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String transactionReference,
    int retryCount,
    String failureReason) {

    /**
     * Constructor principal que inicializa un pago con valores predeterminados.
     * @param sourceAccount Cuenta origen del pago
     * @param destinationAccount Cuenta destino del pago
     * @param amount Monto a transferir
     */
    public Payment {
        if (sourceAccount == null || sourceAccount.isBlank()) {
            throw new IllegalArgumentException("La cuenta origen no puede ser nula o vacía");
        }
        if (destinationAccount == null || destinationAccount.isBlank()) {
            throw new IllegalArgumentException("La cuenta destino no puede ser nula o vacía");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (transactionReference == null) {
            transactionReference = generateTransactionReference();
        }
        retryCount = 0;
        failureReason = null;
    }

    /**
     * Genera una referencia única para la transacción.
     * @return Referencia de transacción con formato UUID
     */
    private static String generateTransactionReference() {
        return "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Crea una nueva instancia de Payment con estado actualizado.
     * @param newStatus Nuevo estado del pago
     * @return Nueva instancia de Payment con el estado actualizado
     */
    public Payment withStatus(PaymentStatus newStatus) {
        return new Payment(
            this.id,
            this.sourceAccount,
            this.destinationAccount,
            this.amount,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount,
            this.failureReason
        );
    }

    /**
     * Crea una nueva instancia de Payment con conteo de reintentos incrementado.
     * @param reason Razón del fallo
     * @return Nueva instancia de Payment con retryCount incrementado
     */
    public Payment withIncrementedRetry(String reason) {
        return new Payment(
            this.id,
            this.sourceAccount,
            this.destinationAccount,
            this.amount,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount + 1,
            reason
        );
    }

    /**
     * Enum que representa los posibles estados de un pago.
     */
    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        REJECTED,
        ROLLED_BACK
    }
}