package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Semantic emptiness evaluation for a subject.
 *
 * @param <T> Value type.
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class EmptinessFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Default emptiness: null check.
     */
    public boolean isEmpty() {
        return subject.getValue() == null;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Custom emptiness rule.
     */
    public boolean isEmpty(Predicate<T> rule) {
        Objects.requireNonNull(rule, "rule");
        return rule.test(subject.getValue());
    }

    /**
     * Collection-aware emptiness helper.
     */
    public boolean isEmptyCollection() {
        T value = subject.getValue();
        return value instanceof Collection<?> c && c.isEmpty();
    }
}
