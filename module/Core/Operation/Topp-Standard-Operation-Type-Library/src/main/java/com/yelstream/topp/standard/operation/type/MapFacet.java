package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class MapFacet<T> {
    public final Subject<T> subject;

    public <R> Subject<R> as(Class<R> type) {
        return Subjects.as(subject,type);
    }

    public <R> Subject<R> to(Function<T, R> mapper) {
        return Subjects.to(subject,mapper);
    }

}
