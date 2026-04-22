package com.yelstream.topp.standard.operation.type;

import java.util.Optional;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ObjectOpsCore {

    private ObjectOpsCore() {}

    /**
     * Type-safe map with casting.
     */
    public static <T, R> Optional<R> map(Object value,
                                         Class<T> type,
                                         Function<T, R> mapper) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(mapper, "mapper");

        return Optional.ofNullable(value)
                .filter(type::isInstance)
                .map(type::cast)
                .map(mapper);
    }

    /**
     * Execute action only if value is instance of type.
     */
    public static <T> void ifInstance(Object value,
                                      Class<T> type,
                                      Consumer<T> action) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(action, "action");

        if (type.isInstance(value)) {
            action.accept(type.cast(value));
        }
    }

    /**
     * Alias-style safe cast (semantic alternative to tryCast).
     */
    public static <T> Optional<T> as(Object value, Class<T> type) {
        Objects.requireNonNull(type, "type");

        return Optional.ofNullable(value)
                .filter(type::isInstance)
                .map(type::cast);
    }

    /**
     * Null-safe mapping without type restriction.
     */
    public static <T, R> Optional<R> map(Object value,
                                         Function<T, R> mapper) {
        @SuppressWarnings("unchecked")
        Function<Object, R> f = (Function<Object, R>) mapper;

        Objects.requireNonNull(mapper, "mapper");

        return Optional.ofNullable(value)
                .map(f);
    }

    /**
     * Identity-based hash (null-safe).
     */
    public static int identityHash(Object value) {
        return System.identityHashCode(value);
    }
}
