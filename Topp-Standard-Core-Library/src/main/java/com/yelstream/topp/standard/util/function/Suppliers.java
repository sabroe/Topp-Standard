package com.yelstream.topp.standard.util.function;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Supplier}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-23
 */
@UtilityClass
public class Suppliers {
    /**
     * Creates a new supplier which is fixed in its value.
     * @param value Supplied value.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fix(T value) {
        return ()->value;
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fix(Supplier<T> supplier) {
        return fixInAdvance(supplier);
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fixInAdvance(Supplier<T> supplier) {
        T value=supplier.get();
        return ()->value;
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called only on-demand.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fixOnDemand(Supplier<T> supplier) {
        return MemoizedSupplier.Strategy.DoubleChecked.of(supplier);
    }
}
