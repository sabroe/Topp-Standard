package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class MapFacet<T> {
    public final ObjectInstance<T> instance;

/*
    public <R> ObjectInstance<R> to(Class<R> type) {
        return Optional.of(instance).filter(i->ObjectOps.isInstance(i.getValue()))
    }
*/

    //as?

    //then?
}
