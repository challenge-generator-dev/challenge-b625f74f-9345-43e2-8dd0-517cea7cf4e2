package com.financial.payments.infrastructure.adapters;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class AntiFraudAdapterTest {

    @Inject
    AntiFraudAdapter antiFraudAdapter;

    private Payment createTestPayment(String sourceAccount, BigDecimal amount) {
        return new Payment(
            "TXN-TEST-" + System.currentTimeMillis(),
            sourceAccount,
            "ACC-DEST-001",
            amount,
            "USD",
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            0,
            null
        );
    }

    @Test
    @DisplayName("Debe retornar true para pagos de bajo riesgo")
    void testVerifyFraudRisk_LowRisk_ReturnsTrue() {
        Payment lowRiskPayment = createTestPayment("ACC-NORMAL", new BigDecimal("500.00"));

        Uni<Boolean> result = antiFraudAdapter.verifyFraudRisk(lowRiskPayment);
        Boolean verified = result.await().indefinitely();

        assertTrue(verified);
    }

    @Test
    @DisplayName("Debe retornar false para pagos de alto monto que superan umbral")
    void testVerifyFraudRisk_HighAmount_ReturnsFalse() {
        Payment highAmountPayment = createTestPayment("ACC-NORMAL", new BigDecimal("50000.00"));

        Uni<Boolean> result = antiFraudAdapter.verifyFraudRisk(highAmountPayment);
        Boolean verified = result.await().indefinitely();

        assertFalse(verified);
    }

    @Test
    @DisplayName("Debe retornar score bajo para pagos normales")
    void testGetRiskScore_LowRiskPayment_ReturnsLowScore() {
        Payment normalPayment = createTestPayment("ACC-NORMAL", new BigDecimal("100.00"));

        Uni<Integer> result = antiFraudAdapter.getRiskScore(normalPayment);
        Integer score = result.await().indefinitely();

        assertNotNull(score);
        assertTrue(score < 30);
    }

    @Test
    @DisplayName("Debe retornar score alto para pagos de monto elevado")
    void testGetRiskScore_HighAmount_ReturnsHighScore() {
        Payment highAmountPayment = createTestAccountWithHighAmount();

        Uni<Integer> result = antiFraudAdapter.getRiskScore(highAmountPayment);
        Integer score = result.await().indefinitely();

        assertNotNull(score);
        assertTrue(score >= 70);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el servicio externo falla")
    void testVerifyFraudRisk_ServiceFailure_ThrowsException() {
        Payment payment = createTestPayment("ACC-ERROR", new BigDecimal("1000.00"));

        assertThrows(AntiFraudServiceException.class, () -> {
            antiFraudAdapter.verifyFraudRisk(payment).await().indefinitely();
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción en getRiskScore cuando el servicio externo falla")
    void testGetRiskScore_ServiceFailure_ThrowsException() {
        Payment payment = createTestPayment("ACC-ERROR", new BigDecimal("1000.00"));

        assertThrows(AntiFraudServiceException.class, () -> {
            antiFraudAdapter.getRiskScore(payment).await().indefinitely();
        });
    }

    @Test
    @DisplayName("Debe simular latencia del servicio externo")
    void testVerifyFraudRisk_LatencySimulation() throws InterruptedException {
        Payment normalPayment = createTestPayment("ACC-LATENCY", new BigDecimal("200.00"));

        long startTime = System.currentTimeMillis();
        antiFraudAdapter.verifyFraudRisk(normalPayment).await().indefinitely();
        long duration = System.currentTimeMillis() - startTime;

        assertTrue(duration >= 50, "La latencia simulada debe ser al menos 50ms");
    }

    @Test
    @DisplayName("Debe manejar cuenta bloqueada correctamente")
    void testVerifyFraudRisk_BlockedAccount_ReturnsFalse() {
        Payment blockedAccountPayment = createTestPayment("ACC-BLOCKED", new BigDecimal("100.00"));

        Uni<Boolean> result = antiFraudAdapter.verifyFraudRisk(blockedAccountPayment);
        Boolean verified = result.await().indefinitely();

        assertFalse(verified);
    }

    @Test
    @DisplayName("Debe calcular score basado en múltiples factores de riesgo")
    void testGetRiskScore_MultipleRiskFactors_ReturnsCumulativeScore() {
        Payment multiRiskPayment = createTestPayment("ACC-NEW", new BigDecimal("15000.00"));

        Uni<Integer> result = antiFraudAdapter.getRiskScore(multiRiskPayment);
        Integer score = result.await().indefinitely();

        assertNotNull(score);
        assertTrue(score > 50, "El score debe ser alto por múltiples factores de riesgo");
    }

    @Test
    @DisplayName("Debe implementar el puerto AntiFraudPort correctamente")
    void testImplementsAntiFraudPort() {
        assertTrue(antiFraudAdapter instanceof AntiFraudPort);
    }

    private Payment createTestAccountWithHighAmount() {
        return new Payment(
            "TXN-HIGH-" + System.currentTimeMillis(),
            "ACC-HIGH-AMOUNT",
            "ACC-DEST-002",
            new BigDecimal("75000.00"),
            "USD",
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            0,
            null
        );
    }
}