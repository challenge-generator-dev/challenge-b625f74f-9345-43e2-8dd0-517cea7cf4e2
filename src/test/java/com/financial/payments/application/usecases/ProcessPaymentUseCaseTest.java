package com.financial.payments.application.usecases;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.model.Payment.PaymentStatus;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.domain.ports.CoreBankingPort;
import com.financial.payments.domain.ports.PaymentGatewayPort;
import com.financial.payments.domain.ports.RiskBureauPort;
import com.financial.payments.domain.ports.RiskBureauPort.RiskLevel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class ProcessPaymentUseCaseTest {

    @Inject
    ProcessPaymentUseCase processPaymentUseCase;

    @InjectMock
    AntiFraudPort antiFraudPort;

    @InjectMock
    RiskBureauPort riskBureauPort;

    @InjectMock
    CoreBankingPort coreBankingPort;

    @InjectMock
    PaymentGatewayPort paymentGatewayPort;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = new Payment(
            "TXN-" + System.currentTimeMillis(),
            "ACC-12345",
            "ACC-67890",
            new BigDecimal("1000.00"),
            "USD",
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            0,
            null
        );
    }

    @Test
    @DisplayName("Debe procesar pago exitosamente cuando todos los servicios responden correctamente")
    void testProcessPayment_Success() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(true));
        when(paymentGatewayPort.executePayment(any(Payment.class))).thenReturn(Uni.createFrom().item("PAY-OK"));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.status());
        verify(antiFraudPort, times(1)).verifyFraudRisk(any(Payment.class));
        verify(paymentGatewayPort, times(1)).executePayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el motor antifraude detecta riesgo")
    void testProcessPayment_RejectedByAntiFraud() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(false));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(85));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.status());
        verify(coreBankingPort, never()).reserveFunds(anyString(), any(BigDecimal.class));
        verify(paymentGatewayPort, never()).executePayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el buró de riesgos indica nivel alto")
    void testProcessPayment_RejectedByRiskBureau_HighRisk() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.HIGH));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.status());
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el buró de riesgos detecta restricciones")
    void testProcessPayment_RejectedByRiskBureau_Restrictions() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(true));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.status());
    }

    @Test
    @DisplayName("Debe fallar cuando la reserva de fondos en core bancario falla")
    void testProcessPayment_FundsReservationFailure() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(false));

        Payment result = processPaymentUseCase.execute(testPayment).await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILED, result.status());
    }

    @Test
    @DisplayName("Debe invocar todos los puertos en el orden correcto")
    void testProcessPayment_InvocationOrder() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(true));
        when(paymentGatewayPort.executePayment(any(Payment.class))).thenReturn(Uni.createFrom().item("PAY-OK"));

        processPaymentUseCase.execute(testPayment).await().indefinitely();

        var inOrder = inOrder(antiFraudPort, riskBureauPort, coreBankingPort, paymentGatewayPort);
        inOrder.verify(antiFraudPort).verifyFraudRisk(any(Payment.class));
        inOrder.verify(riskBureauPort).getAccountRiskLevel(anyString());
        inOrder.verify(riskBureauPort).hasAccountRestrictions(anyString());
        inOrder.verify(coreBankingPort).reserveFunds(anyString(), any(BigDecimal.class));
        inOrder.verify(paymentGatewayPort).executePayment(any(Payment.class));
    }

    @Test
    @DisplayName("Debe capturar y verificar el payment enviado a cada puerto")
    void testProcessPayment_PaymentPayloadCapture() {
        when(antiFraudPort.verifyFraudRisk(any(Payment.class))).thenReturn(Uni.createFrom().item(true));
        when(antiFraudPort.getRiskScore(any(Payment.class))).thenReturn(Uni.createFrom().item(10));
        when(riskBureauPort.getAccountRiskLevel(anyString())).thenReturn(Uni.createFrom().item(RiskLevel.LOW));
        when(riskBureauPort.hasAccountRestrictions(anyString())).thenReturn(Uni.createFrom().item(false));
        when(coreBankingPort.reserveFunds(anyString(), any(BigDecimal.class))).thenReturn(Uni.createFrom().item(true));
        when(paymentGatewayPort.executePayment(any(Payment.class))).thenReturn(Uni.createFrom().item("PAY-OK"));

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        processPaymentUseCase.execute(testPayment).await().indefinitely();

        verify(paymentGatewayPort).executePayment(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();
        assertEquals(testPayment.sourceAccount(), capturedPayment.sourceAccount());
        assertEquals(testPayment.destinationAccount(), capturedPayment.destinationAccount());
        assertEquals(testPayment.amount(), capturedPayment.amount());
    }
}