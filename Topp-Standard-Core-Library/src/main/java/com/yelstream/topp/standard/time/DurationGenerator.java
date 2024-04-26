package com.yelstream.topp.standard.time;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Generator of {@link Duration} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-26
 */
@FunctionalInterface
public interface DurationGenerator {
    /**
     * Generates a duration.
     * @return Duration generated.
     */
    Duration nextDuration();

    /**
     * Creates a supplier bound to this generator.
     * @return Supplier created.
     */
    default Supplier<Duration> supplier() {
        return this::nextDuration;
    }
}
