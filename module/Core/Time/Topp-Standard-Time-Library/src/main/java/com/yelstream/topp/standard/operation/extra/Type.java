package com.yelstream.topp.standard.operation.extra;

import com.yelstream.topp.standard.operation.ObjectOps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

/**
 * Type of object.
 * <p>
 *     This is a mini reflection-safe descriptor, not just a wrapper.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-21
 */
@AllArgsConstructor(staticName = "of")
public final class Type<T> {
    @Getter
    @NonNull
    private final Class<T> raw;

    public boolean isInstance(Object value) {
        return raw.isInstance(value);
    }

    public Optional<T> tryCastOptional(Object value) {
        return ObjectOps.tryCastOptional(value,raw);
    }

    public T tryCast(Object value) {
        return ObjectOps.tryCast(value,raw);
    }

    public T cast(Object value) {
        return ObjectOps.tryCast(value,raw);
    }

    /*
        TO-DO: Consider -
            type.assignableFrom(...)
            type.safeCast(...)
            type.defaultValue(...)
            type.serializer()
     */
}
