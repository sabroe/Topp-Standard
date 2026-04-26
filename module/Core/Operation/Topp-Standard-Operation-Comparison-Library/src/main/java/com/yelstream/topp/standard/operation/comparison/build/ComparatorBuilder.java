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

package com.yelstream.topp.standard.operation.comparison.build;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Builder of instances of {@link Comparator}.
 *
 * @param <T> Type of compared values.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
public final class ComparatorBuilder<T> {  //Note: This could be split into 'Creation' and 'Composition', but it is already there!
    /**
     * Comparator being built.
     */
    private Comparator<T> comparator;

    /**
     * Creates a builder.
     * @param comparator Initial comparator.
     */
    private ComparatorBuilder(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Creates a builder from a comparator.
     * @param comparator Initial comparator.
     * @param <T> Type of compared values.
     * @return Created builder.
     */
    public static <T> ComparatorBuilder<T> of(Comparator<T> comparator) {
        return new ComparatorBuilder<>(comparator);
    }

    /**
     * Creates a builder using natural order.
     * @param <T> Type of compared values.
     * @return Created builder.
     */
    public static <T extends Comparable<? super T>> ComparatorBuilder<T> naturalOrder() {
        return new ComparatorBuilder<>(Comparator.<T>naturalOrder());
    }

    /**
     * Creates a builder using reverse order.
     * @param <T> Type of compared values.
     * @return Created builder.
     */
    public static <T extends Comparable<? super T>> ComparatorBuilder<T> reverseOrder() {
        return new ComparatorBuilder<>(Comparator.<T>reverseOrder());
    }

    /**
     * Creates a builder comparing extracted keys.
     * @param extractor Key extractor.
     * @param <T> Type of compared values.
     * @param <U> Type of extracted keys.
     * @return Created builder.
     */
    public static <T, U extends Comparable<? super U>> ComparatorBuilder<T> comparing(Function<? super T, ? extends U> extractor) {
        return new ComparatorBuilder<>(Comparator.comparing(extractor));
    }

    /**
     * Creates a builder comparing extracted keys.
     * @param extractor Key extractor.
     * @param comparator Key comparator.
     * @param <T> Type of compared values.
     * @param <U> Type of extracted keys.
     * @return Created builder.
     */
    public static <T, U> ComparatorBuilder<T> comparing(Function<? super T, ? extends U> extractor,
                                                        Comparator<? super U> comparator) {
        return new ComparatorBuilder<>(Comparator.comparing(extractor, comparator));
    }

    /**
     * Creates a builder comparing extracted integer keys.
     * @param extractor Key extractor.
     * @param <T> Type of compared values.
     * @return Created builder.
     */
    public static <T> ComparatorBuilder<T> comparingInt(ToIntFunction<? super T> extractor) {
        return new ComparatorBuilder<>(Comparator.comparingInt(extractor));
    }

    /**
     * Creates a builder comparing extracted long keys.
     * @param extractor Key extractor.
     * @param <T> Type of compared values.
     * @return Created builder.
     */
    public static <T> ComparatorBuilder<T> comparingLong(ToLongFunction<? super T> extractor) {
        return new ComparatorBuilder<>(Comparator.comparingLong(extractor));
    }

    /**
     * Creates a builder comparing extracted double keys.
     * @param extractor Key extractor.
     * @param <T> Type of compared values.
     * @return Created builder.
     */
    public static <T> ComparatorBuilder<T> comparingDouble(ToDoubleFunction<? super T> extractor) {
        return new ComparatorBuilder<>(Comparator.comparingDouble(extractor));
    }

    /**
     * Reverses the comparator.
     * @return This builder.
     */
    public ComparatorBuilder<T> reversed() {
        comparator = comparator.reversed();
        return this;
    }

    /**
     * Wraps the comparator with nulls first.
     * @return This builder.
     */
    public ComparatorBuilder<T> nullsFirst() {
        comparator = Comparator.nullsFirst(comparator);
        return this;
    }

    /**
     * Wraps the comparator with nulls last.
     * @return This builder.
     */
    public ComparatorBuilder<T> nullsLast() {
        comparator = Comparator.nullsLast(comparator);
        return this;
    }

    /**
     * Adds comparison of extracted keys.
     * @param extractor Key extractor.
     * @param <U> Type of extracted keys.
     * @return This builder.
     */
    public <U extends Comparable<? super U>>
    ComparatorBuilder<T> thenComparing(Function<? super T, ? extends U> extractor) {
        comparator = comparator.thenComparing(extractor);
        return this;
    }

    /**
     * Adds comparison of extracted keys.
     * @param extractor Key extractor.
     * @param other Key comparator.
     * @param <U> Type of extracted keys.
     * @return This builder.
     */
    public <U>
    ComparatorBuilder<T> thenComparing(Function<? super T, ? extends U> extractor,
                                       Comparator<? super U> other) {
        comparator = comparator.thenComparing(extractor, other);
        return this;
    }

    /**
     * Adds comparison using another comparator.
     * @param other Additional comparator.
     * @return This builder.
     */
    public ComparatorBuilder<T> thenComparing(Comparator<? super T> other) {
        comparator = comparator.thenComparing(other);
        return this;
    }

    /**
     * Adds comparison of extracted integer keys.
     * @param extractor Key extractor.
     * @return This builder.
     */
    public ComparatorBuilder<T> thenComparingInt(ToIntFunction<? super T> extractor) {
        comparator = comparator.thenComparingInt(extractor);
        return this;
    }

    /**
     * Adds comparison of extracted long keys.
     * @param extractor Key extractor.
     * @return This builder.
     */
    public ComparatorBuilder<T> thenComparingLong(ToLongFunction<? super T> extractor) {
        comparator = comparator.thenComparingLong(extractor);
        return this;
    }

    /**
     * Adds comparison of extracted double keys.
     * @param extractor Key extractor.
     * @return This builder.
     */
    public ComparatorBuilder<T> thenComparingDouble(ToDoubleFunction<? super T> extractor) {
        comparator = comparator.thenComparingDouble(extractor);
        return this;
    }

    /**
     * Builds the comparator.
     * @return Built comparator.
     */
    public Comparator<T> build() {
        return comparator;
    }
}
