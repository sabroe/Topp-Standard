/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
public class ComparisonFacet<T> {

    @NonNull
    public final Subject<T> subject;

    @NonNull
    public final Comparator<T> comparator;

    /**
     * Creates a new facet using another subject.
     */
    public ComparisonFacet<T> withSubject(Subject<T> subject) {
        return toBuilder().subject(subject).build();
    }

    /**
     * Creates a new facet using another comparator.
     */
    public ComparisonFacet<T> withComparator(Comparator<T> comparator) {
        return toBuilder().comparator(comparator).build();
    }

    /**
     * Creates a comparison facet using natural ordering.
     */
    public static <T extends Comparable<? super T>> ComparisonFacet<T> of(Subject<T> subject) {
        return of(subject, Comparator.naturalOrder());
    }

    /**
     * Compare subject value to another value.
     */
    public int compareTo(T value) {
        return comparator.compare(subject.getValue(), value);
    }

    public boolean isLessThan(T value) {
        return compareTo(value) < 0;
    }

    public boolean isLessThanOrEqual(T value) {
        return compareTo(value) <= 0;
    }

    public boolean isGreaterThan(T value) {
        return compareTo(value) > 0;
    }

    public boolean isGreaterThanOrEqual(T value) {
        return compareTo(value) >= 0;
    }

    public boolean isEqual(T value) {
        return compareTo(value) == 0;
    }

    public void ifLessThan(T value,
                           Consumer<T> consumer) {
        if (isLessThan(value)) {
            consumer.accept(subject.getValue());
        }
    }

    public void ifGreaterThan(T value,
                              Consumer<T> consumer) {
        if (isGreaterThan(value)) {
            consumer.accept(subject.getValue());
        }
    }

    public void ifEqual(T value,
                        Consumer<T> consumer) {
        if (isEqual(value)) {
            consumer.accept(subject.getValue());
        }
    }
}
