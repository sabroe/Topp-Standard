package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class TypeFacet<T> {
    public final Subject<T> instance;

    public boolean isInstance(Class<?> type) {
        Objects.requireNonNull(type,"type");
        return ObjectOps.isInstance(instance.getValue(),type);
    }




    public <R> boolean is(Class<R> type) {
        return ObjectOps.isInstance(instance.getValue(),type);
    }

/*
    public <R> Predicate<T> is(Class<R> type) {
        return ObjectOps.isInstance(instance.getValue(),type);
    }
*/

/*
    public <R> TypeFacet<R> to(Class<R> type) {
        return ObjectOps.tryCast(instance.getValue(),type).map(v->TypeFacet.of(v,type));
    }
*/




}
