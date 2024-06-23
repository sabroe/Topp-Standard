package com.yelstream.topp.standard.microprofile.health;

import com.yelstream.topp.standard.lang.annotation.Annotations;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import java.util.concurrent.CompletableFuture;

/**
 * Utility addressing instances of {@link FutureHealthCheck}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@Slf4j
@UtilityClass
public class FutureHealthChecks {
    /**
     * Creates a new asynchronous health-check.
     * <p>
     *     Note that the created health-check does not carry any annotations indicating the probe type.
     * </p>
     * @param healthCheck Synchronous health-check.
     * @return Asynchronous health-check.
     */
    public static FutureHealthCheck create(HealthCheck healthCheck) {
        return () -> CompletableFuture.supplyAsync(healthCheck::call);
    }

    /**
     * Creates a new asynchronous health-check.
     * <p>
     *     This contains a probe annotation if the argument health-check contains a probe annotation.
     * </p>
     * @param healthCheck Synchronous health-check.
     * @return Asynchronous health-check.
     */
    public static FutureHealthCheck createWithProbeAnnotation(HealthCheck healthCheck) {
        return addProbeAnnotation(create(healthCheck));
    }

    /**
     * Convert a health-check.
     * <p>
     *     This contains a probe annotation if the argument health-check contains a probe annotation.
     * </p>
     * @param healthCheck Health-check.
     * @return Health-check.
     */
    public static FutureHealthCheck addProbeAnnotation(FutureHealthCheck healthCheck) {
        FutureHealthCheck result=healthCheck;
        if (Annotations.hasAnnotation(healthCheck,Liveness.class)) {
            result=withLiveness(result);
        } else {
            if (Annotations.hasAnnotation(healthCheck,Startup.class)) {
                result=withStartup(result);
            } else {
                if (Annotations.hasAnnotation(healthCheck,Readiness.class)) {
                    result=withReadiness(result);
                }
            }
        }
        return result;
    }

    private static LivenessFutureHealthCheck withLiveness(FutureHealthCheck healthCheck) {
        return healthCheck::submitCall;
    }

    private static StartupFutureHealthCheck withStartup(FutureHealthCheck healthCheck) {
        return healthCheck::submitCall;
    }

    private static ReadinessFutureHealthCheck withReadiness(FutureHealthCheck healthCheck) {
        return healthCheck::submitCall;
    }

    @Liveness
    private interface LivenessFutureHealthCheck extends FutureHealthCheck {
    }

    @Startup
    private interface StartupFutureHealthCheck extends FutureHealthCheck {
    }

    @Readiness
    private interface ReadinessFutureHealthCheck extends FutureHealthCheck {
    }
}
