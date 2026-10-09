package com.financial.payments.infrastructure.config;

import io.quarkus.arc.profile.IfBuildProfile;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigMapping(prefix = "resilience4j")
@IfBuildProfile(not = "test")
public interface ResilienceConfig {

    CircuitBreakerConfig circuitBreaker();

    RetryConfig retry();

    RateLimiterConfig rateLimiter();

    interface CircuitBreakerConfig {
        @WithDefault("5")
        int failureRateThreshold();

        @WithDefault("60")
        int waitDurationInOpenState();

        @WithDefault("10")
        int slidingWindowSize();

        @WithDefault("50")
        int minimumNumberOfCalls();
    }

    interface RetryConfig {
        @WithDefault("3")
        int maxAttempts();

        @WithDefault("1000")
        long waitDuration();

        @WithDefault("1.5")
        double multiplier();
    }

    interface RateLimiterConfig {
        @WithDefault("100")
        int limitForPeriod();

        @WithDefault("1000")
        int limitRefreshPeriod();
    }
}