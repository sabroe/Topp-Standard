package com.yelstream.topp.standard.time;

import com.yelstream.topp.standard.lang.Comparables;
import lombok.experimental.UtilityClass;

import java.time.Instant;

/**
 * Utility addressing instances of {@link Instant}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-24
 */
@UtilityClass
public class Instants {
    /**
     * Determines the minimum of two instants.
     * @param a First instant.
     * @param b Second instant.
     * @return Minimum instant.
     */
    public static Instant min(Instant a, Instant b) {
        return Comparables.min(a,b);
    }

    /**
     * Determines the maximum of two instants.
     * @param a First instant.
     * @param b Second instant.
     * @return Maximum instant.
     */
    public static Instant max(Instant a, Instant b) {
        return Comparables.max(a,b);
    }
}
