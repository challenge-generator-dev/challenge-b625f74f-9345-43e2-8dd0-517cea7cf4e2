package com.financial.payments.infrastructure.exceptions;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import com.financial.payments.domain.model.Payment;
import com.financial.payments.domain.ports.AntiFraudPort;
import com.financial.payments.domain.ports.RiskBureauPort;

import io.smallrye.mutiny.Uni;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Inject
    AntiFraudPort antiFraudPort;

    @Inject
    RiskBureauPort riskBureauPort;

    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Excepcion capturada en el manejador global", exception);

        if (exception instanceof AntiFraudServiceException afEx) {
            return handleAntiFraudException(afEx);
        } else if (exception instanceof RiskBureauServiceException rbEx) {
            return handleRiskBureauException(rbEx);
        } else if (exception instanceof CoreBankingServiceException cbEx) {
            return handleCoreBankingException(cbEx);
        } else if (exception instanceof IllegalArgumentException iaEx) {
            return buildErrorResponse(Response.Status.BAD_REQUEST, "BAD_REQUEST", iaEx.getMessage());
        } else if (exception instanceof IllegalStateException isEx) {
            return buildErrorResponse(Response.Status.CONFLICT, "CONFLICT", isEx.getMessage());
        }

        return buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "Ocurrio un error inesperado al procesar el pago");
    }

    private Response handleAntiFraudException(AntiFraudServiceException ex) {
        String errorCode = "ANTIFRAUD_FAILURE";
        String message = "El sistema de prevencion de fraude no esta disponible temporalmente";

        LOG.warnf("AntiFraudServiceException: %s - %s", ex.getMessage(), message);

        return buildErrorResponse(Response.Status.SERVICE_UNAVAILABLE, errorCode, message);
    }

    private Response handleRiskBureauException(RiskBureauServiceException ex) {
        String errorCode = "RISK_BUREAU_FAILURE";
        String message = "El buró de riesgos no respondio a tiempo. Intente mas tarde";

        LOG.warnf("RiskBureauServiceException: %s - %s", ex.getMessage(), message);

        return buildErrorResponse(Response.Status.GATEWAY_TIMEOUT, errorCode, message);
    }

    private Response handleCoreBankingException(CoreBankingServiceException ex) {
        String errorCode = "CORE_BANKING_FAILURE";
        String message = "El core bancario rechazo la transaccion";

        LOG.warnf("CoreBankingServiceException: %s - %s", ex.getMessage(), message);

        if (ex.getMessage() != null && ex.getMessage().contains("INSUFFICIENT_FUNDS")) {
            errorCode = "INSUFFICIENT_FUNDS";
            message = "Fondos insuficientes en la cuenta origen";
            return buildErrorResponse(Response.Status.PAYMENT_REQUIRED, errorCode, message);
        }

        return buildErrorResponse(Response.Status.BAD_GATEWAY, errorCode, message);
    }

    private Response buildErrorResponse(Response.Status status, String errorCode, String message) {
        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());
        return Response.status(status)
                .entity(error)
                .build();
    }

    public record ErrorResponse(
        String errorCode,
        String message,
        long timestamp
    ) {}

    public Uni<Response> handleAntiFraudReactive(AntiFraudServiceException ex, Payment payment) {
        LOG.warnf("Excepcion reactiva de AntiFraud para pago %s: %s",
                payment.transactionReference(), ex.getMessage());

        String errorCode = "ANTIFRAUD_RETRY_EXCEEDED";
        String message = "Se excedio el numero de reintentos con el servicio antifraude";

        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());

        return Uni.createFrom().item(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(error)
                .build());
    }

    public Uni<Response> handleRiskBureauReactive(RiskBureauServiceException ex, String accountNumber) {
        LOG.warnf("Excepcion reactiva de RiskBureau para cuenta %s: %s",
                accountNumber, ex.getMessage());

        String errorCode = "RISK_CHECK_TIMEOUT";
        String message = "Timeout al consultar el buró de riesgos";

        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());

        return Uni.createFrom().item(Response.status(Response.Status.GATEWAY_TIMEOUT)
                .entity(error)
                .build());
    }

    public Uni<Response> handleValidationError(String field, String reason) {
        String errorCode = "VALIDATION_ERROR";
        String message = String.format("Validacion fallida para el campo '%s': %s", field, reason);

        LOG.warnf("Error de validacion: %s", message);

        ErrorResponse error = new ErrorResponse(errorCode, message, System.currentTimeMillis());

        return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build());
    }
}