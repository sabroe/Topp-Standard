package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.function.Function;

/**
 * Read-only derived view of a subject.
 *
 * @param <T> Value type.
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ViewFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Projects value without modifying subject.
     */
    public <R> R view(Function<T, R> projection) {
        return projection.apply(subject.getValue());
    }

    /**
     * Safe peek operation.
     */
    public T peek() {
        return subject.getValue();
    }
}
