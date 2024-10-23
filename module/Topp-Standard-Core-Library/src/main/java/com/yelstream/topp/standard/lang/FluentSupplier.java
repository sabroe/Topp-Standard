package com.yelstream.topp.standard.lang;

import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-10-23
 */
public interface FluentSupplier<S, T> extends Supplier<T> {
    S getAndContinue();  // Fluent method for chaining
}
