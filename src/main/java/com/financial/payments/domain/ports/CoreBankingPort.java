package com.financial.payments.domain.ports;

import com.financial.payments.domain.model.Payment;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el core bancario.
 * Define las operaciones de consulta y modificación en el sistema bancario central.
 */
public interface CoreBankingPort {

    /**
     * Verifica si la cuenta de origen tiene fondos suficientes para el pago.
     * @param accountNumber Número de cuenta del originador
     * @param amount Monto a debitar
     * @return Uni con true si hay fondos suficientes, false en caso contrario
     */
    Uni<Boolean> verifySufficientFunds(String accountNumber, java.math.BigDecimal amount);

    /**
     * Debita el monto de la cuenta del originador.
     * @param payment Información del pago a debitar
     * @return Uni con el identificador de la transacción bancaria
     */
    Uni<String> debitAccount(Payment payment);

    /**
     * Revierte un débito previamente realizado.
     * @param transactionId Identificador de la transacción a revertir
     * @return Uni con true si la reversión fue exitosa
     */
    Uni<Boolean> reverseDebit(String transactionId);

    /**
     * Consulta el saldo actual de una cuenta.
     * @param accountNumber Número de cuenta a consultar
     * @return Uni con el saldo actual
     */
    Uni<java.math.BigDecimal> getAccountBalance(String accountNumber);

    /**
     * Verifica que la cuenta esté activa y habilitada para operaciones.
     * @param accountNumber Número de cuenta a verificar
     * @return Uni con true si la cuenta está activa
     */
    Uni<Boolean> isAccountActive(String accountNumber);

    /**
     * Registra la transacción de crédito en la cuenta del beneficiario.
     * @param payment Información del pago a acreditar
     * @return Uni con el identificador de la transacción de crédito
     */
    Uni<String> creditAccount(Payment payment);
}