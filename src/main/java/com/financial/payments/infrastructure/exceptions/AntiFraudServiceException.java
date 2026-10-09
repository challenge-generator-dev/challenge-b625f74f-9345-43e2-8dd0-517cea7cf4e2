package com.financial.payments.infrastructure.exceptions;

import java.util.Optional;

public class AntiFraudServiceException extends RuntimeException {

    private final String serviceName;
    private final ErrorCode errorCode;
    private final String transactionReference;
    private final boolean retryable;

    public AntiFraudServiceException(String message) {
        super(message);
        this.serviceName = "AntiFraudService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.transactionReference = null;
        this.retryable = false;
    }

    public AntiFraudServiceException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "AntiFraudService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.transactionReference = null;
        this.retryable = determineRetryability(cause);
    }

    public AntiFraudServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.serviceName = "AntiFraudService";
        this.errorCode = errorCode;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable();
    }

    public AntiFraudServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.serviceName = "AntiFraudService";
        this.errorCode = errorCode;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
    }

    public AntiFraudServiceException(String message, Throwable cause, ErrorCode errorCode, String transactionReference) {
        super(message, cause);
        this.serviceName = "AntiFraudService";
        this.errorCode = errorCode;
        this.transactionReference = transactionReference;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
    }

    private static boolean determineRetryability(Throwable cause) {
        if (cause == null) {
            return false;
        }
        String exceptionClassName = cause.getClass().getSimpleName().toLowerCase();
        return exceptionClassName.contains("timeout") ||
               exceptionClassName.contains("connection") ||
               exceptionClassName.contains("unavailable");
    }

    public String getServiceName() {
        return serviceName;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Optional<String> getTransactionReference() {
        return Optional.ofNullable(transactionReference);
    }

    public boolean isRetryable() {
        return retryable;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public static AntiFraudServiceException timeout(String transactionReference, long timeoutMs) {
        return new AntiFraudServiceException(
            "Timeout al verificar riesgo de fraude. Transacción: " + transactionReference + ". Timeout: " + timeoutMs + "ms",
            ErrorCode.SERVICE_TIMEOUT,
            transactionReference
        );
    }

    public static AntiFraudServiceException connectionFailed(String transactionReference) {
        return new AntiFraudServiceException(
            "Conexión fallida con el servicio de antifraude. Transacción: " + transactionReference,
            ErrorCode.SERVICE_UNAVAILABLE,
            transactionReference
        );
    }

    public static AntiFraudServiceException invalidResponse(String transactionReference, String details) {
        return new AntiFraudServiceException(
            "Respuesta inválida del servicio de antifraude. Transacción: " + transactionReference + ". Detalles: " + details,
            ErrorCode.INVALID_RESPONSE,
            transactionReference
        );
    }

    public static AntiFraudServiceException fraudDetectionFailed(String transactionReference) {
        return new AntiFraudServiceException(
            "Error en la detección de fraude para la transacción: " + transactionReference,
            ErrorCode.FRAUD_CHECK_FAILED,
            transactionReference
        );
    }

    public enum ErrorCode {
        UNKNOWN_ERROR(500, false),
        SERVICE_UNAVAILABLE(503, true),
        SERVICE_TIMEOUT(504, true),
        INVALID_RESPONSE(502, false),
        FRAUD_CHECK_FAILED(500, false),
        RISK_EVALUATION_ERROR(500, false),
        HIGH_RISK_DETECTED(500, false),
        INTERNAL_ERROR(500, false);

        private final int httpStatus;
        private final boolean retryable;

        ErrorCode(int httpStatus, boolean retryable) {
            this.httpStatus = httpStatus;
            this.retryable = retryable;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public boolean isRetryable() {
            return retryable;
        }
    }
}