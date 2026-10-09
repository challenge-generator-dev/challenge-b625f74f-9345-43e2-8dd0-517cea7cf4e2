package com.financial.payments.domain.ports;

import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el buró de riesgos.
 * Define el contrato para consultar información de riesgo de cuentas.
 */
public interface RiskBureauPort {

    /**
     * Consulta el nivel de riesgo de una cuenta en el buró.
     * @param accountNumber Número de cuenta a consultar
     * @return Uni que emite el nivel de riesgo (LOW, MEDIUM, HIGH)
     */
    Uni<RiskLevel> getAccountRiskLevel(String accountNumber);

    /**
     * Consulta si una cuenta tiene restricciones en el buró.
     * @param accountNumber Número de cuenta a verificar
     * @return Uni que emite true si la cuenta tiene restricciones, false en caso contrario
     */
    Uni<Boolean> hasAccountRestrictions(String accountNumber);

    /**
     * Enum que representa los niveles de riesgo posibles.
     */
    enum RiskLevel {
        LOW,
        MEDIUM,
        HIGH
    }
}