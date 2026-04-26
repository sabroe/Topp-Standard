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

import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Entry point for fluent comparator construction.
 * <p>
 *     Provides a domain-specific language for building {@link java.util.Comparator}
 *     instances based on key extractors and primitive projections.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@UtilityClass
public final class Comparing {
    /**
     * Creates a comparator builder based on a comparable key extractor.
     * @param key Key extraction function. Must not be {@code null}.
     * @param <T> Source type.
     * @param <U> Key type.
     * @return Comparator builder ordered by extracted key.
     */
    public static <T, U extends Comparable<? super U>> ComparatorBuilder<T> by(Function<T, U> key) {
        return ComparatorBuilder.comparing(key);
    }

    /**
     * Creates a comparator builder based on an int key extractor.
     * @param key Int extraction function. Must not be {@code null}.
     * @param <T> Source type.
     * @return Comparator builder ordered by extracted int value.
     */
    public static <T> ComparatorBuilder<T> byInt(ToIntFunction<T> key) {
        return ComparatorBuilder.comparingInt(key);
    }

    /**
     * Creates a comparator builder based on a long key extractor.
     * @param key Long extraction function. Must not be {@code null}.
     * @param <T> Source type.
     * @return Comparator builder ordered by extracted long value.
     */
    public static <T> ComparatorBuilder<T> byLong(ToLongFunction<T> key) {
        return ComparatorBuilder.comparingLong(key);
    }

    /**
     * Creates a comparator builder based on a double key extractor.
     * @param key Double extraction function. Must not be {@code null}.
     * @param <T> Source type.
     * @return Comparator builder ordered by extracted double value.
     */
    public static <T> ComparatorBuilder<T> byDouble(ToDoubleFunction<T> key) {
        return ComparatorBuilder.comparingDouble(key);
    }
}
