package com.yelstream.topp.standard.microprofile.health;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    /**
     * Default annotations used to indicate the health-check probe type upon a {@link HealthCheck} class.
     */
    public static final List<Class<? extends Annotation>> DEFAULT_PROBE_ANNOTATIONS=List.of(Liveness.class, Readiness.class, Startup.class);

    /**
     * Converts a synchronous health-check to an asynchronous health-check.
     * <p>
     *     Note that the created health-check does not carry any annotations indicating the probe type.
     * </p>
     * @param healthCheck Synchronous health-check.
     * @return Asynchronous health-check.
     */
    public static FutureHealthCheck toFutureHealthCheck(HealthCheck healthCheck) {
        return () -> CompletableFuture.supplyAsync(healthCheck::call);
    }
}
