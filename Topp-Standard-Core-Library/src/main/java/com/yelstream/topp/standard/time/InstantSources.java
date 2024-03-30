package com.yelstream.topp.standard.time;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;

/**
 * Utility addressing instances of {@link InstantSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-24
 */
@UtilityClass
public class InstantSources {
    /**
     * Creates a source of instants.
     * @return Source of instants.
     */
    public static InstantSource now() {
        return Instant::now;
    }

    /**
     * Creates a source of instants according to a specific clock.
     * @param clock Clock.
     * @return Source of instants.
     */
    public static InstantSource now(Clock clock) {
        return ()->Instant.now(clock);
    }
}
