package com.financial.payments.domain.ports;

import com.financial.payments.domain.model.Payment;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el motor antifraude.
 * Define el contrato que debe cumplir cualquier implementación de antifraude.
 */
public interface AntiFraudPort {

    /**
     * Verifica el riesgo de fraude de un pago.
     * @param payment Pago a verificar
     * @return Uni que emite true si el pago es seguro, false si se detecta fraude
     */
    Uni<Boolean> verifyFraudRisk(Payment payment);

    /**
     * Obtiene el puntaje de riesgo asociado a un pago.
     * @param payment Pago a evaluar
     * @return Uni que emite el puntaje de riesgo (0-100)
     */
    Uni<Integer> getRiskScore(Payment payment);
}