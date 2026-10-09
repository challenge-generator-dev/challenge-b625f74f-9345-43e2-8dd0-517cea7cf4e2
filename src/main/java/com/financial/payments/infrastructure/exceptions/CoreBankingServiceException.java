package com.financial.payments.infrastructure.exceptions;

import java.util.Optional;

public class CoreBankingServiceException extends RuntimeException {

    private final String serviceName;
    private final ErrorCode errorCode;
    private final String accountNumber;
    private final String transactionReference;
    private final boolean retryable;
    private final long retryAfterMillis;

    public CoreBankingServiceException(String message) {
        super(message);
        this.serviceName = "CoreBankingService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = false;
        this.retryAfterMillis = 0;
    }

    public CoreBankingServiceException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = ErrorCode.UNKNOWN_ERROR;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = determineRetryability(cause);
        this.retryAfterMillis = 0;
    }

    public CoreBankingServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable();
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = null;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.transactionReference = null;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber, String transactionReference) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.transactionReference = transactionReference;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = errorCode.getDefaultRetryMillis();
    }

    public CoreBankingServiceException(String message, Throwable cause, ErrorCode errorCode, String accountNumber, String transactionReference, long retryAfterMillis) {
        super(message, cause);
        this.serviceName = "CoreBankingService";
        this.errorCode = errorCode;
        this.accountNumber = accountNumber;
        this.transactionReference = transactionReference;
        this.retryable = errorCode.isRetryable() && determineRetryability(cause);
        this.retryAfterMillis = retryAfterMillis;
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

    public Optional<String> getTransactionReference() {
        return Optional.ofNullable(transactionReference);
    }

    public boolean isRetryable() {
        return retryable;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatus();
    }

    public static CoreBankingServiceException timeout(String accountNumber, String transactionReference, long timeoutMs) {
        return new CoreBankingServiceException(
            "Timeout al procesar en core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference + ", Timeout: " + timeoutMs + "ms",
            ErrorCode.SERVICE_TIMEOUT,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException connectionFailed(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Conexión fallida con el core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.SERVICE_UNAVAILABLE,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException insufficientFunds(String accountNumber, String transactionReference, java.math.BigDecimal available, java.math.BigDecimal required) {
        return new CoreBankingServiceException(
            "Fondos insuficientes en cuenta: " + accountNumber + ", Transacción: " + transactionReference + ". Disponible: " + available + ", Requerido: " + required,
            ErrorCode.INSUFFICIENT_FUNDS,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException accountNotFound(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Cuenta no encontrada en el core bancario: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.ACCOUNT_NOT_FOUND,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException accountClosed(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Cuenta cerrada en el core bancario: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.ACCOUNT_CLOSED,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException transactionFailed(String accountNumber, String transactionReference, String reason) {
        return new CoreBankingServiceException(
            "Transacción fallida en el core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference + ", Razón: " + reason,
            ErrorCode.TRANSACTION_FAILED,
            accountNumber,
            transactionReference
        );
    }

    public static CoreBankingServiceException duplicateTransaction(String accountNumber, String transactionReference) {
        return new CoreBankingServiceException(
            "Transacción duplicada detectada en el core bancario. Cuenta: " + accountNumber + ", Transacción: " + transactionReference,
            ErrorCode.DUPLICATE_TRANSACTION,
            accountNumber,
            transactionReference
        );
    }

    public enum ErrorCode {
        UNKNOWN_ERROR(500, false, 0),
        SERVICE_UNAVAILABLE(503, true, 30000),
        SERVICE_TIMEOUT(504, true, 15000),
        ACCOUNT_NOT_FOUND(404, false, 0),
        ACCOUNT_CLOSED(403, false, 0),
        INSUFFICIENT_FUNDS(402, false, 0),
        TRANSACTION_FAILED(500, false, 0),
        DUPLICATE_TRANSACTION(409, false, 0),
        INVALID_AMOUNT(400, false, 0),
        INTERNAL_ERROR(500, false, 0);

        private final int httpStatus;
        private final boolean retryable;
        private final long defaultRetryMillis;

        ErrorCode(int httpStatus, boolean retryable, long defaultRetryMillis) {
            this.httpStatus = httpStatus;
            this.retryable = retryable;
            this.defaultRetryMillis = defaultRetryMillis;
        }

        public int getHttpStatus() {
            return httpStatus;
        }

        public boolean isRetryable() {
            return retryable;
        }

        public long getDefaultRetryMillis() {
            return defaultRetryMillis;
        }
    }
}