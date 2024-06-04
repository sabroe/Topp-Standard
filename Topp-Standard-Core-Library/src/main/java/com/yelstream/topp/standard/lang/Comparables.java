/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

import java.util.Comparator;

/**
 * Utility addressing instances of {@link Comparable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-19
 */
@UtilityClass
public class Comparables {
    /**
     * Determines the minimum of two values.
     * <p>
     *     Note that values may be {@code null}.
     * </p>
     * @param a First value.
     *          This may be {@code null}.
     * @param b Second value.
     *          This may be {@code null}.
     * @param <T> Type of value.
     * @return Minimum value.
     *         This may be {@code null}.
     */
    public static <T extends Comparable<T>> T min(T a, T b) {
        return Comparator.nullsLast(Comparator.<T>naturalOrder()).compare(a,b)<=0?a:b;
    }

    /**
     * Determines the maximum of two values.
     * <p>
     *     Note that values may be {@code null}.
     * </p>
     * @param a First value.
     *          This may be {@code null}.
     * @param b Second value.
     *          This may be {@code null}.
     * @param <T> Type of value.
     * @return Maximum value.
     *         This may be {@code null}.
     */
    public static <T extends Comparable<T>> T max(T a, T b) {
        return Comparator.nullsFirst(Comparator.<T>naturalOrder()).compare(a,b)>=0?a:b;
    }

    /**
     * Indicates, if two values are equal.
     * <p>
     *     Note that values may be {@code null}.
     * </p>
     * @param a First value.
     *          This may be {@code null}.
     * @param b Second value.
     *          This may be {@code null}.
     * @param <T> Type of value.
     * @return Indicates, if values are equal.
     *         This may be {@code null}.
     */
    public static <T extends Comparable<T>> boolean equals(T a, T b) {
        return a!=null && b!=null && a.compareTo(b)==0;
    }
}
