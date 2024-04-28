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
     * Gets the minimum of two instants.
     * <p>
     *     Note that instants may be {@link null}.
     * </p>
     * @param a First instant.
     *          This may be {@link null}.
     * @param b Second instant.
     *          This may be {@link null}.
     * @return Minimum instant.
     *         This may be {@link null}.
     */
    public static Instant min(Instant a, Instant b) {
        return Comparables.min(a,b);
    }

    /**
     * Gets the maximum of two instants.
     * <p>
     *     Note that instants may be {@link null}.
     * </p>
     * @param a First instant.
     *          This may be {@link null}.
     * @param b Second instant.
     *          This may be {@link null}.
     * @return Maximum instant.
     *         This may be {@link null}.
     */
    public static Instant max(Instant a, Instant b) {
        return Comparables.max(a,b);
    }

    /**
     * Indicates, if two instants are equal.
     * <p>
     *     Note that instants may be {@link null}.
     * </p>
     * @param a First instant.
     *          This may be {@link null}.
     * @param b Second instant.
     *          This may be {@link null}.
     * @return Indicates, if instants are equal.
     *         This may be {@link null}.
     */
    public static boolean equals(Instant a, Instant b) {
        return Comparables.equals(a,b);
    }

}
