package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio reactivo para persistir solicitudes de pago en MongoDB.
 * Utiliza Panache Reactive para operaciones asíncronas con la base de datos.
 */
@ApplicationScoped
public class PaymentRepository implements PanacheMongoRepositoryBase<Payment, ObjectId> {

    @Inject
    ReactiveMongoClient mongoClient;

    /**
     * Guarda una nueva solicitud de pago en la colección.
     * @param payment Entidad de pago a persistir
     * @return Uni con el payment persistido con su ID asignado
     */
    public Uni<Payment> save(Payment payment) {
        return persist(payment)
            .replaceWith(payment);
    }

    /**
     * Busca un pago por su referencia única de transacción.
     * @param transactionReference Referencia única del pago
     * @return Uni con el payment encontrado o vacío
     */
    public Uni<Optional<Payment>> findByTransactionReference(String transactionReference) {
        return find("transactionReference", transactionReference)
            .firstResultOptional();
    }

    /**
     * Busca pagos por el número de cuenta del originador.
     * @param accountNumber Número de cuenta del originador
     * @return Uni con lista de pagos asociados a la cuenta
     */
    public Uni<List<Payment>> findByOriginatorAccount(String accountNumber) {
        return find("originatorAccountNumber", accountNumber)
            .list();
    }

    /**
     * Busca pagos por el número de cuenta del beneficiario.
     * @param accountNumber Número de cuenta del beneficiario
     * @return Uni con lista de pagos recibidos
     */
    public Uni<List<Payment>> findByBeneficiaryAccount(String accountNumber) {
        return find("beneficiaryAccountNumber", accountNumber)
            .list();
    }

    /**
     * Busca pagos por su estado actual.
     * @param status Estado del pago a buscar
     * @return Uni con lista de pagos en el estado especificado
     */
    public Uni<List<Payment>> findByStatus(PaymentStatus status) {
        return find("status", status)
            .list();
    }

    /**
     * Busca pagos creados dentro de un rango de tiempo.
     * @param startTime Timestamp de inicio del rango
     * @param endTime Timestamp de fin del rango
     * @return Uni con lista de pagos en el rango de tiempo
     */
    public Uni<List<Payment>> findByCreatedAtBetween(Instant startTime, Instant endTime) {
        return find("createdAt >= ?1 and createdAt <= ?2", startTime, endTime)
            .list();
    }

    /**
     * Actualiza el estado de un pago existente.
     * @param transactionReference Referencia del pago a actualizar
     * @param newStatus Nuevo estado del pago
     * @return Uni con el número de documentos modificados
     */
    public Uni<Long> updateStatus(String transactionReference, PaymentStatus newStatus) {
        return update("status = ?1 where transactionReference = ?2", newStatus, transactionReference);
    }

    /**
     * Actualiza el estado y añade información de失败 del gateway.
     * @param transactionReference Referencia del pago
     * @param newStatus Nuevo estado
     * @param failureReason Razón del fallo
     * @param gatewayTransactionId ID de la transacción en el gateway
     * @return Uni con el número de documentos modificados
     */
    public Uni<Long> updateStatusWithFailure(
            String transactionReference,
            PaymentStatus newStatus,
            String failureReason,
            String gatewayTransactionId) {
        return update(
            "status = ?1, failureReason = ?2, gatewayTransactionId = ?3, updatedAt = ?4 where transactionReference = ?5",
            newStatus, failureReason, gatewayTransactionId, Instant.now(), transactionReference
        );
    }

    /**
     * Incrementa el contador de reintentos de un pago.
     * @param transactionReference Referencia del pago
     * @param newRetryCount Nuevo conteo de reintentos
     * @param lastFailureReason Razón del último fallo
     * @return Uni con el número de documentos modificados
     */
    public Uni<Long> incrementRetryCount(
            String transactionReference,
            int newRetryCount,
            String lastFailureReason) {
        return update(
            "retryCount = ?1, failureReason = ?2, updatedAt = ?3 where transactionReference = ?4",
            newRetryCount, lastFailureReason, Instant.now(), transactionReference
        );
    }

    /**
     * Cuenta la cantidad de pagos en un estado específico.
     * @param status Estado a contar
     * @return Uni con la cantidad de pagos en ese estado
     */
    public Uni<Long> countByStatus(PaymentStatus status) {
        return count("status", status);
    }

    /**
     * Obtiene pagos pendientes de procesamiento que han excedido el tiempo máximo.
     * @param maxAgeMinutes Edad máxima en minutos para considerar un pago como estancado
     * @return Uni con lista de pagos estancados
     */
    public Uni<List<Payment>> findStalePendingPayments(int maxAgeMinutes) {
        Instant cutoffTime = Instant.now().minusSeconds(maxAgeMinutes * 60L);
        return find("status = ?1 and createdAt < ?2", PaymentStatus.PENDING, cutoffTime)
            .list();
    }

    /**
     * Elimina un pago por su referencia (para casos de prueba o limpieza).
     * @param transactionReference Referencia del pago a eliminar
     * @return Uni con true si se eliminó correctamente
     */
    public Uni<Boolean> deleteByTransactionReference(String transactionReference) {
        return delete("transactionReference", transactionReference)
            .map(deletedCount -> deletedCount > 0);
    }

    /**
     * Verifica si existe un pago con la referencia dada.
     * @param transactionReference Referencia a verificar
     * @return Uni con true si existe
     */
    public Uni<Boolean> existsByTransactionReference(String transactionReference) {
        return count("transactionReference", transactionReference)
            .map(count -> count > 0);
    }

    /**
     * Obtiene el historial de pagos para auditoría.
     * @param accountNumber Número de cuenta (originador o beneficiario)
     * @param limit Cantidad máxima de registros a retornar
     * @return Uni con lista de pagos ordenados por fecha descendente
     */
    public Uni<List<Payment>> getPaymentHistory(String accountNumber, int limit) {
        return find("originatorAccountNumber = ?1 or beneficiaryAccountNumber = ?2", accountNumber, accountNumber)
            .sort("-createdAt")
            .page(0, limit)
            .list();
    }
}