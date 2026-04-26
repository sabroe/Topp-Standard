package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * State facet for a subject.
 *
 * @param <T> Value type.
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class StateFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Indicates whether subject has a value assigned.
     */
    public boolean isInitialized() {
        return subject.getValue() != null;
    }

    /**
     * Snapshot identity of current state.
     */
    public int stateHash() {
        T value = subject.getValue();
        return value != null ? value.hashCode() : 0;
    }
}