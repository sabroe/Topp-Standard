package com.yelstream.topp.standard.microprofile.health;

import com.yelstream.topp.standard.lang.annotation.Annotations;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.health.Startup;

import java.lang.annotation.Annotation;
import java.util.List;
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
    /**
     * Default annotations used to indicate the health-check probe type upon a {@link HealthCheck} class.
     */
    public static final List<Class<? extends Annotation>> DEFAULT_PROBE_ANNOTATIONS=List.of(Liveness.class, Readiness.class, Startup.class);

    /**
     * Creates a new synchronous health-check.
     * <p>
     *     Note that the created health-check does not carry any annotations indicating the probe type.
     * </p>
     * @param healthCheck Asynchronous health-check.
     * @return Synchronous health-check.
     */
    public static HealthCheck create(FutureHealthCheck healthCheck) {
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

    /**
     * Creates a new synchronous health-check.
     * <p>
     *     This contains a probe annotation if the argument health-check contains a probe annotation.
     * </p>
     * @param healthCheck Asynchronous health-check.
     * @return Synchronous health-check.
     */
    public static HealthCheck createWithProbeAnnotation(FutureHealthCheck healthCheck) {
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
    public static HealthCheck addProbeAnnotation(HealthCheck healthCheck) {
        HealthCheck result=healthCheck;
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

    private static LivenessHealthCheck withLiveness(HealthCheck healthCheck) {
        return healthCheck::call;
    }

    private static StartupHealthCheck withStartup(HealthCheck healthCheck) {
        return healthCheck::call;
    }

    private static ReadinessHealthCheck withReadiness(HealthCheck healthCheck) {
        return healthCheck::call;
    }

    @Liveness
    private interface LivenessHealthCheck extends HealthCheck {
    }

    @Startup
    private interface StartupHealthCheck extends HealthCheck {
    }

    @Readiness
    private interface ReadinessHealthCheck extends HealthCheck {
    }
}
