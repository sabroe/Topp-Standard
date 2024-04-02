package com.yelstream.topp.standard.lang.thread;

import lombok.experimental.UtilityClass;

import java.time.Duration;

/**
 * Addresses instances of {@link Thread}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-23
 */
@UtilityClass
@SuppressWarnings({"unused","UnusedReturnValue"})
public class Threads {
    /**
     * Causes the currently executing thread to sleep for the specified duration.
     * @param durationInMillis Sleep duration in ms.
     */
    public static void sleep(long durationInMillis) {
        try {
            Thread.sleep(durationInMillis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Causes the currently executing thread to sleep for the specified duration.
     * @param duration Sleep duration.
     */
    public static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for to die.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread) {
        boolean success=false;
        try {
            thread.join();
            success=true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return success;
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for to die.
     * @param durationInMillis Time to wait in ms.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread,
                               long durationInMillis) {
        boolean success=false;
        try {
            thread.join(durationInMillis);
            success=true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return success;
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for.
     * @param duration Time to wait.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread,
                               Duration duration) {
        return join(thread,duration.toMillis());
    }
}
