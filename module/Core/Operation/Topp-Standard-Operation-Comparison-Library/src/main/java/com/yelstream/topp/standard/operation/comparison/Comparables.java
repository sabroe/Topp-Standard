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

package com.yelstream.topp.standard.operation.comparison;

import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.Objects;

/**
 * Utility addressing instances of {@link Comparable}.
 * <p>
 *     Provides strict comparison operations for naturally comparable types
 *     and explicit policy-based variants for null-handling and ordering behavior.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.1
 * @since 2024-03-19
 */
@UtilityClass
public class Comparables {
    /**
     * Returns the minimum of two values using null-last policy.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return Minimum value.
     */
    public static <T extends Comparable<? super T>> T minNullLast(T a, T b) {
        return Comparator.nullsLast(Comparator.<T>naturalOrder()).compare(a, b) <= 0 ? a : b;
    }

    /**
     * Returns the maximum of two values using null-first policy.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return Maximum value.
     */
    public static <T extends Comparable<? super T>> T maxNullFirst(T a, T b) {
        return Comparator.nullsFirst(Comparator.<T>naturalOrder()).compare(a, b) >= 0 ? a : b;
    }

    /**
     * Returns the minimum of two non-null values.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return Minimum value.
     */
    public static <T extends Comparable<? super T>> T min(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * Returns the maximum of two non-null values.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return Maximum value.
     */
    public static <T extends Comparable<? super T>> T max(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * Returns the median of three values.
     * @param a First value.
     * @param b Second value.
     * @param c Third value.
     * @param <T> Value type.
     * @return Median value.
     */
    public static <T extends Comparable<? super T>> T median(T a, T b, T c) {
        return max(min(a, b), min(max(a, b), c));
    }

    /**
     * Checks equality using {@code compareTo}.
     * <p>
     *   Values must not be {@code null}.
     * </p>
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if values are equal.
     */
    public static <T extends Comparable<? super T>> boolean equals(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) == 0;
    }

    /**
     * Checks equality using {@code compareTo}, allowing {@code null} values.
     * <p>
     *     Two {@code null} values are considered equal. A {@code null} and a non-null value are not equal.
     * </p>
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if values are equal or both {@code null}.
     */
    public static <T extends Comparable<? super T>> boolean equalsNullSafe(T a, T b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.compareTo(b) == 0;
    }

    /**
     * Checks if first value is less than second value.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if less.
     */
    public static <T extends Comparable<? super T>> boolean lessThan(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) < 0;
    }

    /**
     * Checks if first value is greater than second value.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if greater.
     */
    public static <T extends Comparable<? super T>> boolean greaterThan(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) > 0;
    }

    /**
     * Checks if first value is less than or equal to second value.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if less or equal.
     */
    public static <T extends Comparable<? super T>> boolean lessThanOrEqual(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) <= 0;
    }

    /**
     * Checks if first value is greater than or equal to second value.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if greater or equal.
     */
    public static <T extends Comparable<? super T>> boolean greaterThanOrEqual(T a, T b) {
        Objects.requireNonNull(a, "a");
        Objects.requireNonNull(b, "b");
        return a.compareTo(b) >= 0;
    }

    /**
     * Returns a natural comparator.
     * @param <T> Value type.
     * @return Natural comparator.
     */
    public static <T extends Comparable<? super T>> Comparator<T> naturalComparator() {
        return Comparator.naturalOrder();
    }

    /**
     * Returns a reversed natural comparator.
     * @param <T> Value type.
     * @return Reversed comparator.
     */
    public static <T extends Comparable<? super T>> Comparator<T> reverseComparator() {
        return Comparator.reverseOrder();
    }
}
