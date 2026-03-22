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

package com.yelstream.topp.standard.lang;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Utilities addressing instances of {@link Copyable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-10-23
 */
@UtilityClass
public class Copyables {

    /**
     * Creates a copy of the given object.
     * @param object The object to copy.
     * @param <T> The type of the object.
     * @return A copy of the given object.
     */
    public static <T extends Copyable<T>> T copy(T object) {
        return object.copy();
    }

    /**
     * Creates a copy of the given object using the provided copy function.
     * <p>
     *     This method allows copying of objects that do not implement
     *     {@link Copyable} by supplying an explicit copy strategy.
     * </p>
     * @param object The object to copy.
     * @param copier Function that produces a copy of the object.
     * @param <T> The type of the object.
     * @return A copy of the given object.
     */
    public static <T> T copy(T object,
                             UnaryOperator<T> copier) {
        return copier.apply(object);
    }

    /**
     * Creates copies of all elements in the given collection.
     * @param items The collection of items to copy.
     * @param <T> The type of elements.
     * @return A list containing copies of all elements in the input collection.
     */
    public static <T extends Copyable<T>> List<T> copyAll(Collection<T> items) {
        return items.stream().map(Copyable::copy).toList();
    }

    /**
     * Creates a copy of the given object,
     * or returns {@code null} if the object is {@code null}.
     * @param object The object to copy, possibly {@code null}.
     * @param <T> The type of the object.
     * @return A copy of the object, or {@code null} if the input was {@code null}.
     */
    public static <T extends Copyable<T>> T copyOrNull(T object) {
        return object == null ? null : object.copy();
    }

    /**
     * Creates a copy of the given object,
     * or returns a fallback value if the object is {@code null}.
     * @param object The object to copy, possibly {@code null}.
     * @param fallback The value to return if the object is {@code null}.
     * @param <T> The type of the object.
     * @return A copy of the object, or the fallback value if the input was {@code null}.
     */
    public static <T extends Copyable<T>> T copyOrDefault(T object,
                                                          T fallback) {
        return object == null ? fallback : object.copy();
    }

    /**
     * Creates a copy using a {@link FluentCopyable} source and applies the given consumer.
     * <p>
     *     This method delegates to {@link FluentCopyable#copy(Consumer)}, allowing
     *     inline configuration, initialization, or handling of the copied instance.
     * </p>
     * @param source The fluent copyable source.
     * @param consumer Consumer that receives the copied instance.
     * @param <S> The fluent return type.
     * @param <T> The type of the copied object.
     * @return A fluent result of type {@code S}.
     */
    public static <S, T> S copy(FluentCopyable<S, T> source,
                                Consumer<T> consumer) {
        return source.copy(consumer);
    }
}
