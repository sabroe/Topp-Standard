/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.nil;

/**
 * Provides the nil value for a specific type.
 * @param <T> Type of value.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
@FunctionalInterface
public interface NilProvider<T> {
    /**
     * Gets the provided nil object.
     */
    Nil<T> getNil();

    /**
     * Gets the nil value.
     */
    default T getNilValue() {
        return getNil().getValue();
    }

    /**
     * Tests, if a value represents nil.
     * @return Indicates, if value represents nil.
     */
    default boolean isNil(T value) {
        return getNil().isNil(value);
    }

    /**
     * Create a nil-provider.
     * @param <T> Type of value.
     * @param value Nil value.
     * @return Created nil-provider
     */
    static <T> NilProvider<T> of(T value) {
        return of(Nil.of(value));
    }

    /**
     * Create a nil-provider.
     * @param <T> Type of value.
     * @param nil Nil.
     * @return Created nil-provider
     */
    static <T> NilProvider<T> of(Nil<T> nil) {
        return ()->nil;
    }
}
