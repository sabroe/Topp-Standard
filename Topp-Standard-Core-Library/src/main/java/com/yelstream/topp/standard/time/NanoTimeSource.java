package com.yelstream.topp.standard.time;

/**
 * Provides access to machine time.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@FunctionalInterface
public interface NanoTimeSource {
    /**
     * Gets the current time of the machine in nanoseconds.
     * @return Machine time in nanoseconds.
     */
    long nanoTime();

    /**
     * Gets the provider of machine time for the system.
     * @return Machine time for the system.
     */
    static NanoTimeSource system() {
        return System::nanoTime;
    }
}
