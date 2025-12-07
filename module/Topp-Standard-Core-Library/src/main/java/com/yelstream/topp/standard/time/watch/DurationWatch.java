/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

import java.time.Duration;

/**
 * Timer measuring durations.
 * <p>
 *     The precision depends upon the actual implementation.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
public interface DurationWatch {
    /**
     * Starts a timer.
     * @return Started timer.
     */
    Timer start();

    /**
     * Timer.
     */
    interface Timer {
        /**
         * Stops this timer.
         * @return Time measured from start to stop.
         */
        Time stop();
    }

    /**
     * Time measured.
     */
    interface Time {
        /**
         * Gets the time measured.
         * @return Time measured.
         *         This is in nanoseconds.
         */
        long toNanos();

        /**
         * Gets the time measured.
         * @return Time measured.
         *         This is in milliseconds.
         */
        long toMillis();

        /**
         * Gets the time measured.
         * @return Time measured.
         */
        Duration toDuration();
    }
}
