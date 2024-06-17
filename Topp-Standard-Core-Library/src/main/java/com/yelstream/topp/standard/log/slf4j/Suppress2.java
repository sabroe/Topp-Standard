package com.yelstream.topp.standard.log.slf4j;

import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.time.Duration;
import java.util.function.Supplier;

public class Suppress2 {  //TO-DO: Remove!
    private static final Logger log = LoggerFactory.getLogger(Suppress2.class);
    private LoggingEventBuilder builder;
    private RateLimiter rateLimiter;

    private Suppress2(LoggingEventBuilder builder) {
        this.builder = builder;
    }

    public static Suppress2 of(LoggingEventBuilder builder) {
        return new Suppress2(builder);
    }

    public Suppress2 limit(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
        return this;
    }

    public Supplier<LoggingEventBuilder> decorate() {
        Supplier<LoggingEventBuilder> supplier = () -> {
            if (builder != NOPLoggingEventBuilder.singleton()) {
                return builder;
            }
            throw new IllegalStateException("NOPLoggingEventBuilder cannot be logged.");
        };

        if (rateLimiter != null) {
            supplier = Decorators.ofSupplier(supplier)
                    .withRateLimiter(rateLimiter)
                    .decorate();
        }

        return supplier;
    }

    public static void main(String[] args) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(1))
                .limitRefreshPeriod(Duration.ofSeconds(5))
                .limitForPeriod(1)
                .build();
        RateLimiter rateLimiter = RateLimiter.of("logRateLimiter", config);

        // Example usage
        for (int i = 0; i < 10; i++) {
            try {
                Suppress2.of(log.atDebug())
                        .limit(rateLimiter)
                        .decorate()
                        .get()
                        .log("Logging event number: {}", i);
            } catch (Exception e) {
                // Handle the exception (e.g., rate limit exceeded)
                System.out.println(e.getMessage());
            }

            try {
                Thread.sleep(1000); // Sleep for demonstration purposes
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
