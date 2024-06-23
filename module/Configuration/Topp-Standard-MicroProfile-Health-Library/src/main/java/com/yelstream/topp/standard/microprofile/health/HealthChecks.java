package com.yelstream.topp.standard.microprofile.health;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Utility addressing instances of {@link HealthCheck}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@Slf4j
@UtilityClass
public class HealthChecks {

    public static AsyncHealthCheck toAsyncHealthCheck(HealthCheck healthCheck) {
        return () -> CompletableFuture.supplyAsync(healthCheck::call);
    }

    public static HealthCheck toHealthCheck(AsyncHealthCheck asyncHealthCheck) {
        return () -> {
            try {
                return asyncHealthCheck.call().get();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Failure to convert health-check type!",ex);
            } catch (ExecutionException ex) {
                throw new IllegalStateException("Failure to convert health-check type!",ex);
            }
        };
    }
}
