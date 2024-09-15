package com.yelstream.topp.standard.util.function;

import java.util.Objects;

/**
 * Consumer of {@code boolean} primitive values.
 * <p>
 *     As of Java SE 21, a {@link java.util.function.BooleanSupplier} exists
 *     while at the same time a {@code BooleanConsumer} has yet to be added.
 * </p>
 * <p>
 *     While {@code Consumer<Boolean>} may be the same as {@code BooleanConsumer} performance-wise,
 *     it is not the same when designing APIs since there are significant differences between
 *     the object {@link Boolean} and the primitive type {@code boolean}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-15
 */
@FunctionalInterface
public interface BooleanConsumer {
    /**
     * Performs this operation on the given argument.
     * @param value Argument.
     */
    void accept(boolean value);

    /**
     * Return a composed consumer.
     * @param after Operation to be performed after this operation.
     * @return Composed consumer.
     */
    default BooleanConsumer andThen(BooleanConsumer after) {
        Objects.requireNonNull(after);
        return (boolean t) -> { accept(t); after.accept(t); };
    }
}
