package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * Utility addressing instances of {@link Optional}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-23
 */
@UtilityClass
public class Optionals {
    /**
     * Indicates, if an optional is empty.
     * <p>
     *     Note that while this kind of testing is NOT the intention with a supposed-to-be fluent optional,
     *     not all APIs are created with this in mind, are used appropriately,
     *     leading to most defensive coding being necessary!
     * </p>
     * <p>
     *     This represents extreme carefulness.
     * </p>
     * @param optional Optional.
     *                 This may be {@code null}.
     * @return Indicats, if empty.
     * @param <T> Type of optional value.
     */
    @SuppressWarnings("all")
    public static <T> boolean isEmpty(Optional<T> optional) {
        return optional==null || optional.isEmpty();
    }

    /**
     * Gets the value contained in an optional
     * <p>
     *     Note that while this kind of testing is NOT the intention with a supposed-to-be fluent optional,
     *     not all APIs are created with this in mind, are used appropriately,
     *     leading to most defensive coding being necessary!
     * </p>
     * <p>
     *     This represents extreme carefulness.
     * </p>
     * @param optional Optional.
     *                 This may be {@code null}.
     * @return Value contained in optional.
     *         This may be {@code null}.
     * @param <T> Type of optional value.
     */
    @SuppressWarnings("all")
    public static <T> T get(Optional<T> optional) {
        return isEmpty(optional)?null:optional.get();
    }
}
