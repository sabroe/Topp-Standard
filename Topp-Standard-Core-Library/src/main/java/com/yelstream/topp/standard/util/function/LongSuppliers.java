package com.yelstream.topp.standard.util.function;

import lombok.experimental.UtilityClass;

import java.util.function.LongSupplier;

/**
 * Utility addressing instances of {@link LongSupplier}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-29
 */
@UtilityClass
public class LongSuppliers {
    /**
     * Creates a new supplier which is fixed in its value.
     * @param value Supplied value.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fix(long value) {
        return ()->value;
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fix(LongSupplier supplier) {
        return fixInAdvance(supplier);
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fixInAdvance(LongSupplier supplier) {
        if (supplier==null) {
            return null;
        } else {
            long value=supplier.getAsLong();
            return ()->value;
        }
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called only on-demand.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fixOnDemand(LongSupplier supplier) {
        return supplier==null?null:MemoizedLongSupplier.Strategy.DoubleChecked.of(supplier);
    }
}
