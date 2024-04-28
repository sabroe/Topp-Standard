package com.yelstream.topp.standard.time;

import com.yelstream.topp.standard.lang.Comparables;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * Utility addressing instances of {@link Duration}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-24
 */
@UtilityClass
public class Durations {
    /**
     * Duration indicating "forever".
     */
    public static final Duration FOREVER=of(ChronoUnit.FOREVER);

    /**
     * Minimum duration.
     */
    public static final Duration MIN_VALUE=FOREVER.negated();

    /**
     * maximum duration.
     */
    public static final Duration MAX_VALUE=FOREVER;

    /**
     * Creates a duration from a temporal unit.
     * @param temporalUnit Temporal unit.
     * @return Resulting duration.
     */
    public static Duration of(TemporalUnit temporalUnit) {
        return temporalUnit.getDuration();
    }

    /**
     * Creates a duration from a time unit.
     * @param timeUnit Time unit.
     * @return Resulting duration.
     */
    public static Duration of(TimeUnit timeUnit) {
        return timeUnit.toChronoUnit().getDuration();
    }

    /**
     * Gets the minimum of two durations.
     * <p>
     *     Note that durations may be {@code null}.
     * </p>
     * @param a First duration.
     *          This may be {@code null}.
     * @param b Second duration.
     *          This may be {@code null}.
     * @return Minimum duration.
     *         This may be {@code null}.
     */
    public static Duration min(Duration a, Duration b) {
        return Comparables.min(a,b);
    }

    /**
     * Gets the maximum of two durations.
     * <p>
     *     Note that durations may be {@code null}.
     * </p>
     * @param a First duration.
     *          This may be {@code null}.
     * @param b Second duration.
     *          This may be {@code null}.
     * @return Maximum duration.
     *         This may be {@code null}.
     */
    public static Duration max(Duration a, Duration b) {
        return Comparables.max(a,b);
    }

    /**
     * Indicates, if two durations are equal.
     * <p>
     *     Note that durations may be {@code null}.
     * </p>
     * @param a First duration.
     *          This may be {@code null}.
     * @param b Second duration.
     *          This may be {@code null}.
     * @return Indicates, if durations are equal.
     *         This may be {@code null}.
     */
    public static boolean equals(Duration a, Duration b) {
        return Comparables.equals(a,b);
    }

    /**
     * Gets the sum of two durations.
     * <p>
     *     Note that durations may be {@code null}.
     * </p>
     * @param a First duration.
     *          This may be {@code null}.
     * @param b Second duration.
     *          This may be {@code null}.
     * @return Durations summed.
     *         This may be {@code null}.
     */
    public static Duration sum(Duration a, Duration b) {
        if (a==null) {
            return b;
        } else if (b==null) {
            return a;
        } else {
            return a.plus(b);
        }
    }

    /**
     * Indicates, if a duration is practically infinite.
     * @param duration Duration.
     *                 This may be {@code null}.
     * @return Indicates, if duration is infinite.
     */
    public static boolean isInfinite(Duration duration) {
        return duration!=null && duration.abs().equals(FOREVER);
    }

    /**
     * Indicates, if a duration is finite.
     * @param duration Duration.
     *                 This may be {@code null}.
     * @return Indicates, if duration is finite.
     */
    public static boolean isFinite(Duration duration) {
        return duration!=null && !duration.abs().equals(FOREVER);
    }

    public static Supplier<Duration> randomSupplier(RandomGenerator random,
                                                    Duration minDuration,
                                                    Duration maxDuration) {
        return RandomDurationGenerator.of(random,minDuration,maxDuration).supplier();
    }

    public static Supplier<Duration> randomSupplier(Duration minDuration,
                                                    Duration maxDuration) {
        return RandomDurationGenerator.of(minDuration,maxDuration).supplier();
    }
}
