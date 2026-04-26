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
 * Comparison facet for a subject.
 * <p>
 *     Provides comparison-based operations on the subject value using a specified
 *     {@link Comparator} or natural ordering.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
public class ComparisonFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Comparator of subject values.
     */
    @NonNull
    private final Comparator<T> comparator;

    /**
     * Creates a new facet using another subject.
     * @param subject Subject addressed.
     * @return New facet.
     */
    public ComparisonFacet<T> withSubject(Subject<T> subject) {
        return toBuilder().subject(subject).build();
    }

    /**
     * Creates a new facet using another comparator.
     * @param comparator Comparator of subject values.
     * @return New facet.
     */
    public ComparisonFacet<T> withComparator(Comparator<T> comparator) {
        return toBuilder().comparator(comparator).build();
    }

    /**
     * Creates a comparison facet using natural ordering.
     * @param subject Subject addressed.
     * @param <T> Value type.
     * @return New facet.
     */
    public static <T extends Comparable<? super T>> ComparisonFacet<T> of(Subject<T> subject) {
        return of(subject, Comparator.naturalOrder());
    }

    /**
     * Compares the subject value to another value.
     * @param value Value compared to.
     * @return Negative, zero, or positive according to comparison result.
     */
    public int compareTo(T value) {
        return comparator.compare(subject.getValue(), value);
    }

    /**
     * Indicates whether the subject value is less than another value.
     * @param value Value compared to.
     * @return True if less than.
     */
    public boolean isLessThan(T value) {
        return compareTo(value) < 0;
    }

    /**
     * Indicates whether the subject value is less than or equal to another value.
     * @param value Value compared to.
     * @return True if less than or equal.
     */
    public boolean isLessThanOrEqual(T value) {
        return compareTo(value) <= 0;
    }

    /**
     * Indicates whether the subject value is greater than another value.
     * @param value Value compared to.
     * @return True if greater than.
     */
    public boolean isGreaterThan(T value) {
        return compareTo(value) > 0;
    }

    /**
     * Indicates whether the subject value is greater than or equal to another value.
     * @param value Value compared to.
     * @return True if greater than or equal.
     */
    public boolean isGreaterThanOrEqual(T value) {
        return compareTo(value) >= 0;
    }

    /**
     * Indicates whether the subject value is equal to another value.
     * @param value Value compared to.
     * @return True if equal.
     */
    public boolean isEqual(T value) {
        return compareTo(value) == 0;
    }

    /**
     * Invokes a consumer if the subject value is less than another value.
     * @param value Value compared to.
     * @param consumer Consumer invoked.
     */
    public void ifLessThan(T value,
                           Consumer<T> consumer) {
        if (isLessThan(value)) {
            consumer.accept(subject.getValue());
        }
    }

    /**
     * Invokes a consumer if the subject value is greater than another value.
     * @param value Value compared to.
     * @param consumer Consumer invoked.
     */
    public void ifGreaterThan(T value,
                              Consumer<T> consumer) {
        if (isGreaterThan(value)) {
            consumer.accept(subject.getValue());
        }
    }

    /**
     * Invokes a consumer if the subject value is equal to another value.
     * @param value Value compared to.
     * @param consumer Consumer invoked.
     */
    public void ifEqual(T value,
                        Consumer<T> consumer) {
        if (isEqual(value)) {
            consumer.accept(subject.getValue());
        }
    }
}
