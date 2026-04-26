package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * Combines multiple subjects into a single result.
 *
 * @param <T> Value type.
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class CombineFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Combines this subject with another subject.
     */
    public Subject<T> combine(Subject<T> other, BinaryOperator<T> combiner) {
        Objects.requireNonNull(other, "other");
        Objects.requireNonNull(combiner, "combiner");
        return Subject.of(combiner.apply(subject.getValue(), other.getValue()));
    }
}
