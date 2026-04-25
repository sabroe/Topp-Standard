package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class NullFacet<T> {
    public final Subject<T> subject;

    public Subject<T> or(T value) {
        return Subjects.or(subject,value);
    }
}
