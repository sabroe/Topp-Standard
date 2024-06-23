package com.yelstream.topp.standard.microprofile.health;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;

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
public class FutureHealthChecks {
    /**
     * Converts an asynchronous health-check to a synchronous health-check.
     * <p>
     *     Note that the created health-check does not carry any annotations indicating the probe type.
     * </p>
     * @param healthCheck Asynchronous health-check.
     * @return Synchronous health-check.
     */
    public static HealthCheck toHealthCheck(FutureHealthCheck healthCheck) {
        return () -> {
            try {
                return healthCheck.submitCall().get();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Failure to convert health-check type!",ex);
            } catch (ExecutionException ex) {
                throw new IllegalStateException("Failure to convert health-check type!",ex);
            }
        };
    }
}
