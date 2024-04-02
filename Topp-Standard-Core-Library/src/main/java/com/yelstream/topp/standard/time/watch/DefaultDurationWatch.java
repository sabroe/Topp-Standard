package com.yelstream.topp.standard.time.watch;

import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

/**
 * Default timer.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@AllArgsConstructor(staticName="of")
class DefaultDurationWatch implements DurationWatch {
    /**
     * Source of time.
     */
    private final LongSupplier timeSource;

    /**
     * Unit of time.
     * This should match the granularity provided by the source of time.
     */
    private final TemporalUnit timeUnit;

    /**
     * Dictates, how measured durations should be scaled.
     * Any rounding wanted should be applied by this.
     */
    private final LongUnaryOperator durationScale;

    /**
     * Gets an instant for the current time.
     * @return Instant for the current time.
     */
    private long time() {
        return timeSource.getAsLong();
    }

    @Override
    public Timer start() {
        return new DefaultTimer(time());
    }

    /**
     * Scales a measured duration value.
     * @param t Measured duration value.
     * @return Scaled duration value.
     */
    @SuppressWarnings("java:S3398")
    private long scaledDelta(long t) {
        return durationScale.applyAsLong(t);
    }

    /**
     * Converts a duration value to a duration object.
     * @param st Duration value.
     * @return Duration.
     */
    @SuppressWarnings("java:S3398")
    private Duration toDuration(long st) {
        return Duration.of(st,timeUnit);
    }

    /**
     * Internal timer.
     */
    @AllArgsConstructor
    private class DefaultTimer implements DurationWatch.Timer {
        /**
         * Start of timer.
         * Time instant relative to the source of time provided.
         */
        private final long t0;

        @Override
        public DefaultTime stop() {
            return new DefaultTime(DefaultDurationWatch.this.time());
        }

        /**
         * Computes the difference from the start of the timer and up to the given time instant.
         * @param t1 Time instant relative to the source of time.
         * @return Difference.
         *         Units are relative to the source of time.
         */
        @SuppressWarnings("java:S3398")
        private long delta(long t1) {
            return t1-t0;
        }

        /**
         * Internal time.
         */
        @AllArgsConstructor
        private class DefaultTime implements DurationWatch.Time {
            /**
             * Stop of timer.
             * Time instant relative to the source of time provided.
             */
            private final long t1;

            /**
             * Computes the difference from the start of the timer and up to this time.
             * @return Difference.
             *         Units are relative to the source of time.
             */
            private long delta() {
                return DefaultTimer.this.delta(t1);
            }

            /**
             * Computes the duration measured after scaling.
             * @return Measured duration after scaling.
             *         Units are relative to the source of time.
             */
            private long scaledDelta() {
                long t=delta();
                return DefaultDurationWatch.this.scaledDelta(t);
            }

            @Override
            public Duration toDuration() {
                long st=scaledDelta();
                return DefaultDurationWatch.this.toDuration(st);
            }
        }
    }
}
