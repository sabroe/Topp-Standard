package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class NullFacet<T> {
    public final ObjectInstance<T> instance;

/*
    public ObjectInstance<T> or(T value) {
        return Optional.ofNullable(instance).filter(i->i.getValue()!=null).orElse(Value.of(value));
    }
*/
}
