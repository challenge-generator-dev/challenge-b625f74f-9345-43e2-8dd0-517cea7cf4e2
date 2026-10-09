package com.financial.payments;


import com.financial.payments.domain.model.Payment;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@QuarkusMain
public class Application implements QuarkusApplication {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    @Override
    public int run(String... args) {
        LOGGER.info("Payment Processing Service is starting...");
        Quarkus.waitForExit();
        return 0;
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Payment Processing Service started successfully.");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Payment Processing Service is stopping...");
    }
}