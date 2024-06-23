package com.yelstream.topp.standard.microprofile.health;

import org.eclipse.microprofile.health.HealthCheckResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Health check procedure.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-23
 */
@FunctionalInterface
public interface FutureHealthCheck {
    /**
     * Initiates the health check procedure.
     * @return Handle to the asynchronously delivered result.
     */
    CompletableFuture<HealthCheckResponse> submitCall();
}
