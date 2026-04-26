package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * Conversion facet for transforming subject values.
 *
 * @param <T> Source type.
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ConversionFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Converts value using converter function.
     */
    public <R> R convert(Function<T, R> converter) {
        Objects.requireNonNull(converter, "converter");
        return converter.apply(subject.getValue());
    }

    /**
     * Converts into a new subject.
     */
    public <R> Subject<R> toSubject(Function<T, R> converter) {
        Objects.requireNonNull(converter, "converter");
        return Subject.of(converter.apply(subject.getValue()));
    }
}
