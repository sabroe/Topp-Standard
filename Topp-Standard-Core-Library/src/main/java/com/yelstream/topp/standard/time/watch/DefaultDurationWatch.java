/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.time.watch;

import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
     *           This in nanoseconds.
     * @return Duration.
     *         This in nanoseconds.
     */
    @SuppressWarnings("java:S3398")
    private long toNanos(long st) {
        return timeUnit.getDuration().toNanos()*st;  //Yes, please avoid allocation; instead of 'Duration.of(st,timeUnit).toNanos()', use this!
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
            public long toNanos() {
                long st=scaledDelta();
                return DefaultDurationWatch.this.toNanos(st);
            }

            private static final long NANOSECONDS_PER_MILLISECOND=ChronoUnit.MILLIS.getDuration().toNanos();

            @Override
            public long toMillis() {
                return (toNanos()+(NANOSECONDS_PER_MILLISECOND/2))/NANOSECONDS_PER_MILLISECOND;
            }

            @Override
            public Duration toDuration() {
                long st=scaledDelta();
                return DefaultDurationWatch.this.toDuration(st);
            }
        }
    }
}
