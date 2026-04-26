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

import java.util.Objects;
import java.util.function.Function;

/**
 * Transformation facet for a subject.
 * <p>
 *     Provides mapping operations that transform the subject value into another subject.
 *     Supports both direct mapping and monadic-style flat mapping.
 * </p>
 *
 * @param <T> Source value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class MapFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Transforms the subject value using a mapper function.
     *
     * @param mapper Mapping function from value to new value.
     * @param <R> Result type.
     * @return New subject containing mapped value.
     */
    public <R> Subject<R> map(Function<T, R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return Subject.of(mapper.apply(subject.getValue()));
    }

    /**
     * Transforms the subject value into another subject.
     * @param mapper Mapping function returning a subject.
     * @param <R> Result type.
     * @return Flattened subject.
     */
    public <R> Subject<R> flatMap(Function<T, Subject<R>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return mapper.apply(subject.getValue());
    }

    /**
     * Converts the subject value using a mapper function.
     * <p>
     *     Alias for {@link #map(Function)} kept for semantic clarity in fluent chains.
     * </p>
     * @param mapper Mapping function.
     * @param <R> Result type.
     * @return New subject with transformed value.
     */
    public <R> Subject<R> to(Function<T, R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return Subject.of(mapper.apply(subject.getValue()));
    }
}
