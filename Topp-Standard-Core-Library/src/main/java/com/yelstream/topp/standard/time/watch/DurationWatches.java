package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.experimental.UtilityClass;

import java.time.InstantSource;
import java.time.temporal.ChronoUnit;
import java.util.function.LongUnaryOperator;

/**
 * Utility addressing instances of {@link DurationWatch}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@UtilityClass
public class DurationWatches {
    /**
     * Creates a timer.
     * @param source Source of instants.
     * @param durationScale Applied scaling.
     *                      This could be e.g. 10x and to let time pass on ten times faster than normal.
     * @return Created timer.
     */
    public static DurationWatch createDurationWatch(NanoTimeSource source,
                                                    LongUnaryOperator durationScale) {
        return DefaultDurationWatch.of(source::nanoTime,ChronoUnit.NANOS,durationScale);
    }

    /**
     * Creates a timer.
     * @param source Source of instants.
     * @param durationScale Applied scaling.
     *                      This could be e.g. 10x and to let time pass on ten times faster than normal.
     * @return Created timer.
     */
    public static DurationWatch createDurationWatch(InstantSource source,
                                                    LongUnaryOperator durationScale) {
        return DefaultDurationWatch.of(source::millis,ChronoUnit.MILLIS,durationScale);
    }
}
