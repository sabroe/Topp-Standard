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

package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Supplier;

/**
 * Utility addressing instances of arrays.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-24
 */
@UtilityClass
public class Arrays {
    /**
     * Creates an array from a list.
     * @param list List.
     * @return Created array.
     *         This may be {@code null}.
     * @param <T> Type of elements.
     */
    @SuppressWarnings("java:S1168")
    public static <T> T[] of(List<T> list) {
        if (list == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] result = (T[])list.toArray();
        return result;
    }

    /**
     * Creates an array from a list.
     * @param list List.
     * @param arraySupplier Supplier of arrays.
     *                      Argument could be of the form {@code ClassX[]::new}.
     * @return Created array.
     *         This may be {@code null}.
     * @param <T> Type of elements.
     */
    @SuppressWarnings("java:S1168")
    public static <T> T[] of(List<T> list,
                             Supplier<T[]> arraySupplier) {
        if (list == null) {
            return null;
        }
        T[] array=arraySupplier.get();
        return list.toArray(java.util.Arrays.copyOf(array,list.size()));
    }
}
