package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@EqualsAndHashCode
public class Subject<T> {
    /**
     * Object value held by this subject.
     */
    @Getter
    private final T value;

    public Subject<T> withValue(T newValue) {  //Preserved identity!
        if (Objects.equals(newValue, value)) {  //TO-DO: ObjectOps.same(newValue,value) ?
            return this;
        }
        return toBuilder().value(newValue).build();
    }

    public <R> Subject<R> mapValue(Function<T, R> mapper) {  //Creates a new typed subject!
//        R newValue = (value == null) ? null : mapper.apply(value);
        R newValue = mapper.apply(value);
        return replaceValue(newValue);
    }

    public <R> Subject<R> replaceValue(R newValue) {  //Creates a new typed subject!
        return Subject.<R>builder().value(newValue).build();
    }

    public int identityHash() {
        return ObjectOps.identityHash(value);
    }

    public boolean isInstance(Class<?> type) {
        return ObjectOps.isInstance(value,type);
    }

    public <R> void ifInstance(Class<R> type,
                               Consumer<Subject<R>> action) {
        if (ObjectOps.isInstance(value,type)) {
            action.accept(Subject.of(type.cast(value)));
        }
    }

    public <R> Optional<Subject<R>> tryCast(Class<R> type) {
        return ObjectOps.tryCast(value,type).map(Subject::of);
    }

    public <R> Subject<R> tryCastOrNull(Class<R> type) {
        return tryCast(type).orElse(null);
    }

    public <R> Subject<R> tryCastOr(Class<R> type,
                                    Subject<R> fallback) {
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

    public static <T> Subject<T> empty() {
        return of(null);
    }

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
