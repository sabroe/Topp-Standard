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
         */
        Duration toDuration();
    }
}
