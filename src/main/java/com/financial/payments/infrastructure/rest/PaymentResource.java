package com.financial.payments.infrastructure.rest;




import com.financial.payments.infrastructure.exceptions.ErrorResponse;
import com.financial.payments.infrastructure.exceptions.RiskBureauServiceException;
import com.financial.payments.infrastructure.exceptions.AntiFraudServiceException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import com.financial.payments.application.usecases.ProcessPaymentUseCase;
import com.financial.payments.domain.model.Payment;
import com.financial.payments.infrastructure.exceptions.GlobalExceptionHandler;

import io.smallrye.mutiny.Uni;

@Path("/api/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger LOG = Logger.getLogger(PaymentResource.class);

    @Inject
    ProcessPaymentUseCase processPaymentUseCase;

    @Inject
    GlobalExceptionHandler exceptionHandler;

    @Inject
    @Channel("payment-requests")
    Emitter<Payment> paymentEmitter;

    @POST
    @Path("/submit")
    public Uni<Response> submitPayment(PaymentRequest request) {
        LOG.infof("Recibida solicitud de pago: %s", request);

        if (request == null) {
            return exceptionHandler.handleValidationError("request", "El cuerpo de la solicitud no puede ser nulo");
        }

        if (request.accountNumber() == null || request.accountNumber().isBlank()) {
            return exceptionHandler.handleValidationError("accountNumber", "El numero de cuenta es obligatorio");
        }

        if (request.amount() == null || request.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return exceptionHandler.handleValidationError("amount", "El monto debe ser mayor que cero");
        }

        if (request.currency() == null || request.currency().isBlank()) {
            return exceptionHandler.handleValidationError("currency", "La moneda es obligatoria");
        }

        if (request.description() == null || request.description().isBlank()) {
            return exceptionHandler.handleValidationError("description", "La descripcion es obligatoria");
        }

        Payment payment = new Payment(
                request.accountNumber(),
                request.amount(),
                request.currency(),
                request.description(),
                request.recipientAccount(),
                request.recipientName()
        );

        LOG.infof("Payment creado con referencia: %s", payment.transactionReference());

        return processPaymentUseCase.execute(payment)
                .onItem().transform(p -> {
                    PaymentResponse response = new PaymentResponse(
                            p.transactionReference(),
                            p.status().name(),
                            "Pago procesado exitosamente",
                            p.createdAt()
                    );
                    LOG.infof("Pago procesado exitosamente: %s", p.transactionReference());
                    return Response.status(Response.Status.ACCEPTED)
                            .entity(response)
                            .build();
                })
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf("Error al procesar pago: %s", throwable.getMessage());

                    if (throwable instanceof com.financial.payments.infrastructure.exceptions.AntiFraudServiceException afEx) {
                        return exceptionHandler.handleAntiFraudReactive(afEx, payment);
                    } else if (throwable instanceof com.financial.payments.infrastructure.exceptions.RiskBureauServiceException rbEx) {
                        return exceptionHandler.handleRiskBureauReactive(rbEx, request.accountNumber());
                    } else if (throwable instanceof IllegalArgumentException iaEx) {
                        return exceptionHandler.handleValidationError("payment", iaEx.getMessage());
                    }

                    return Uni.createFrom().item(
                            Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity(new GlobalExceptionHandler.ErrorResponse(
                                            "PROCESSING_ERROR",
                                            "Error al procesar el pago: " + throwable.getMessage(),
                                            System.currentTimeMillis()
                                    ))
                                    .build()
                    );
                });
    }

    @POST
    @Path("/async-submit")
    public Uni<Response> submitPaymentAsync(PaymentRequest request) {
        LOG.infof("Recibida solicitud de pago asincrona: %s", request);

        if (request == null || request.accountNumber() == null || request.amount() == null) {
            return exceptionHandler.handleValidationError("request", "Solicitud invalida");
        }

        Payment payment = new Payment(
                request.accountNumber(),
                request.amount(),
                request.currency(),
                request.description(),
                request.recipientAccount(),
                request.recipientName()
        );

        paymentEmitter.send(payment);

        LOG.infof("Pago encolado para procesamiento asincrono: %s", payment.transactionReference());

        AsyncPaymentResponse response = new AsyncPaymentResponse(
                payment.transactionReference(),
                "Pago recibido y en cola para procesamiento",
                System.currentTimeMillis()
        );

        return Uni.createFrom().item(Response.status(Response.Status.ACCEPTED)
                .entity(response)
                .build());
    }

    public record PaymentRequest(
        String accountNumber,
        java.math.BigDecimal amount,
        String currency,
        String description,
        String recipientAccount,
        String recipientName
    ) {}

    public record PaymentResponse(
        String transactionReference,
        String status,
        String message,
        java.time.Instant timestamp
    ) {}

    public record AsyncPaymentResponse(
        String transactionReference,
        String message,
        long timestamp
    ) {}
}