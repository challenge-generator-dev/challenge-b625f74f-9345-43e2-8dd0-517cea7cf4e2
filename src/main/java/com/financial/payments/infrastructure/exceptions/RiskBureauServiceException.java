package com.financial.payments.infrastructure.exceptions;

import java.util.Optional;

public class RiskBureauServiceException extends RuntimeException {

    private final String serviceName;
    private final ErrorCode errorCode;
    private final String accountNumber;
    private final boolean retryable;
    private final long retryAfterSeconds;

    public RiskBureauServiceException(String message) {
        super(message);
        this.serviceName = "RiskBureauService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.retryable = false;
        this.retryAfterSeconds = 0;
    }

    public RiskBureauServiceException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.retryable = determineRetryability(cause);
        this.retryAfterSeconds = 0;
    }

    public RiskBureauServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.retryable = errorCode.isRetryable();
        this.retryAfterSeconds = errorCode.getDefaultRetrySeconds();
    }

    public RiskBureauServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterSeconds = errorCode.getDefaultRetrySeconds();
    }

    public RiskBureauServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterSeconds = errorCode.getDefaultRetrySeconds();
    }

    public RiskBureauServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber, long retryAfterSeconds) {
        super(message, cause);
        this.serviceName = "RiskBureauService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterSeconds = retryAfterSeconds;
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

    public Optional<String> getAccountNumber() {
        return Optional.ofNullable(accountNumber);
    }

    public boolean isRetryable() {
        return retryable;
    }

    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public static RiskBureauServiceException timeout(String accountNumber, long timeoutMs) {
        return new RiskBureauServiceException(
            "Timeout al consultar buró de riesgos para cuenta: " + accountNumber + ". Timeout: " + timeoutMs + "ms",
            ErrorCode.SERVICE_TIMEOUT,
            accountNumber
        );
    }

    public static RiskBureauServiceException connectionFailed(String accountNumber) {
        return new RiskBureauServiceException(
            "Conexión fallida con el buró de riesgos para cuenta: " + accountNumber,
            ErrorCode.SERVICE_UNAVAILABLE,
            accountNumber
        );
    }

    public static RiskBureauServiceException accountNotFound(String accountNumber) {
        return new RiskBureauServiceException(
            "Cuenta no encontrada en el buró de riesgos: " + accountNumber,
            ErrorCode.ACCOUNT_NOT_FOUND,
            accountNumber
        );
    }

    public static RiskBureauServiceException riskEvaluationFailed(String accountNumber, String details) {
        return new RiskBureauServiceException(
            "Error al evaluar riesgo de la cuenta: " + accountNumber + ". Detalles: " + details,
            ErrorCode.RISK_EVALUATION_FAILED,
            accountNumber
        );
    }

    public static RiskBureauServiceException restrictionsFound(String accountNumber, String restrictionDetails) {
        return new RiskBureauServiceException(
            "Restricciones encontradas para la cuenta: " + accountNumber + ". Detalles: " + restrictionDetails,
            ErrorCode.ACCOUNT_RESTRICTIONS,
            accountNumber
        );
    }

    public enum ErrorCode {
        UNKNOWN_ERROR(500, false, 0),
        SERVICE_UNAVAILABLE(503, true, 30),
        SERVICE_TIMEOUT(504, true, 15),
        ACCOUNT_NOT_FOUND(404, false, 0),
        RISK_EVALUATION_FAILED(500, false, 0),
        ACCOUNT_RESTRICTIONS(403, false, 0),
        HIGH_RISK_ACCOUNT(500, false, 0),
        INTERNAL_ERROR(500, false, 0);

        private final int httpStatus;
        private final boolean retryable;
        private final long defaultRetrySeconds;

        ErrorCode(int httpStatus, boolean retryable, long defaultRetrySeconds) {
            this.httpStatus = httpStatus;
            this.retryable = retryable;
            this.defaultRetrySeconds = defaultRetrySeconds;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public boolean isRetryable() {
            return retryable;
        }

        public long getDefaultRetrySeconds() {
            return defaultRetrySeconds;
        }
    }
}