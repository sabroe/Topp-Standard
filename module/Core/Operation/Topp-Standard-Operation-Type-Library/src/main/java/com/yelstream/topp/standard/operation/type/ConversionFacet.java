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
 * Conversion facet for transforming subject values.
 *
 * @param <T> Source type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ConversionFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Converts value using converter function.
     */
    public <R> R convert(Function<T, R> converter) {
        Objects.requireNonNull(converter, "converter");
        return converter.apply(subject.getValue());
    }

    /**
     * Converts into a new subject.
     */
    public <R> Subject<R> toSubject(Function<T, R> converter) {
        Objects.requireNonNull(converter, "converter");
        return Subject.of(converter.apply(subject.getValue()));
    }
}
