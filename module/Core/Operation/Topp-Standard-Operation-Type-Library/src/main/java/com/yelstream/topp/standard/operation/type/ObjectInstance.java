package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
//@lombok.Builder(builderClassName = "Builder", toBuilder = true)
public class ObjectInstance<T> {  //TO-DO: Consider naming, 'Instance' vs. 'Value' vs. something else?
    /**
     *
     */
    @Getter
    private final T value;

    public int identityHash() {
        return ObjectOps.identityHash(value);
    }

    public boolean isInstance(Class<?> type) {
        return ObjectOps.isInstance(value,type);
    }

    public <R> void ifInstance(Class<R> type,
                               Consumer<ObjectInstance<R>> action) {
        if (ObjectOps.isInstance(value,type)) {
            action.accept(ObjectInstance.of(type.cast(value)));
        }
    }

    public <R> Optional<ObjectInstance<R>> tryCast(Class<R> type) {
        return ObjectOps.tryCast(value,type).map(ObjectInstance::of);
    }

    public <R> ObjectInstance<R> tryCastOrNull(Class<R> type) {
        return tryCast(type).orElse(null);
    }

    public <R> ObjectInstance<R> tryCastOr(Class<R> type,
                                           ObjectInstance<R> fallback) {
        return tryCast(type).orElse(fallback);
    }

    public <R> R cast(Class<R> type) {
        return ObjectOps.cast(value, type);
    }

/*
    public <R> Optional<R> map(Function<T, R> mapper) {
        return ObjectOps.map(value,mapper);
    }
*/

    public IdentityFacet<T> identity() {
        return IdentityFacet.of(this);
    }

    public TypeFacet<T> type() {
        return TypeFacet.of(this);
    }

    public MapFacet<T> map() {
        return MapFacet.of(this);
    }

    public NullFacet<T> nulls() {
        return NullFacet.of(this);
    }
}
