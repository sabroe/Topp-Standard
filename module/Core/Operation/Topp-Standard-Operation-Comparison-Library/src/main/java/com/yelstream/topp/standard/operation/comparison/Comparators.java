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

import com.yelstream.topp.standard.operation.comparison.policy.NullPolicy;
import com.yelstream.topp.standard.operation.comparison.policy.StandardNullPolicy;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility addressing instances of {@link Comparator}.
 * <p>
 *     Provides composable comparison operations, ordering policies,
 *     and convenience methods for working with comparator-based logic.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-19
 */
@UtilityClass
public class Comparators {
    /**
     * Returns a comparator using natural ordering with null values placed last.
     * @param <T> Value type.
     * @return Null-safe comparator using natural order and null-last policy.
     */
    public static <T extends Comparable<? super T>> Comparator<T> nullSafeLast() {
        return Comparator.nullsLast(Comparator.naturalOrder());
    }

    /**
     * Returns a comparator using natural ordering with null values placed first.
     * @param <T> Value type.
     * @return Null-safe comparator using natural order and null-first policy.
     */
    public static <T extends Comparable<? super T>> Comparator<T> nullSafeFirst() {
        return Comparator.nullsFirst(Comparator.naturalOrder());
    }

    /**
     * Wraps a comparator with a null-last policy.
     * @param comparator Base comparator.
     * @param <T> Value type.
     * @return Comparator with null values placed last.
     */
    public static <T> Comparator<T> nullSafeLast(Comparator<T> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        return Comparator.nullsLast(comparator);
    }

    /**
     * Wraps a comparator with a null-first policy.
     * @param comparator Base comparator.
     * @param <T> Value type.
     * @return Comparator with null values placed first.
     */
    public static <T> Comparator<T> nullSafeFirst(Comparator<T> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        return Comparator.nullsFirst(comparator);
    }
    /**
     * Applies a {@link NullPolicy} to a comparator.
     * <p>
     *     Transforms the given comparator into a null-aware comparator using the provided policy.
     *     The original comparator is not modified.
     * </p>
     * @param comparator Base comparator.
     *                   Must not be {@code null}.
     * @param policy Null-handling policy.
     *               Must not be {@code null}.
     * @param <T> Type being compared.
     * @return Comparator with applied null policy.
     */
    public static <T> Comparator<T> applyNullPolicy(Comparator<T> comparator,
                                                    NullPolicy policy) {
        Objects.requireNonNull(comparator, "comparator");
        Objects.requireNonNull(policy, "policy");
        return policy.create(comparator);
    }

    /**
     * Applies a nulls-first policy to a comparator.
     * <p>
     *     Null values are ordered before non-null values.
     * </p>
     * @param comparator Base comparator.
     *                   Must not be {@code null}.
     * @param <T> Type being compared.
     * @return Null-aware comparator with nulls ordered first.
     */
    public static <T> Comparator<T> nullsFirst(Comparator<T> comparator) {
        return applyNullPolicy(comparator, StandardNullPolicy.NullsFirst.getPolicy());
    }

    /**
     * Applies a nulls-last policy to a comparator.
     * <p>
     *     Null values are ordered after non-null values.
     * </p>
     * @param comparator Base comparator.
     *                   Must not be {@code null}.
     * @param <T> Type being compared.
     * @return Null-aware comparator with nulls ordered last.
     */
    public static <T> Comparator<T> nullsLast(Comparator<T> comparator) {
        return applyNullPolicy(comparator, StandardNullPolicy.NullsLast.getPolicy());
    }

    /**
     * Applies a strict null policy to a comparator.
     * <p>
     *     Null values are not allowed and will result in a {@link NullPointerException}.
     * </p>
     * @param comparator Base comparator.
     *                   Must not be {@code null}.
     * @param <T> Type being compared.
     * @return Comparator that rejects null values.
     */
    public static <T> Comparator<T> strict(Comparator<T> comparator) {
        return applyNullPolicy(comparator, StandardNullPolicy.Strict.getPolicy());
    }

    /**
     * Returns the minimum of two values.
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return Minimum value.
     */
    public static <T> T min(Comparator<T> comparator,
                            T a,
                            T b) {
        Objects.requireNonNull(comparator,"comparator");
        return comparator.compare(a, b) <= 0 ? a : b;
    }

    /**
     * Returns the maximum of two values.
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return Maximum value.
     */
    public static <T> T max(Comparator<T> comparator,
                            T a,
                            T b) {
        Objects.requireNonNull(comparator,"comparator");
        return comparator.compare(a, b) >= 0 ? a : b;
    }

    /**
     * Returns the minimum value from a collection.
     * @param comparator Comparator used for ordering.
     * @param values Input values.
     * @param <T> Value type.
     * @return Minimum value or null if empty.
     */
    public static <T> T min(Comparator<T> comparator,
                            Collection<T> values) {
        Objects.requireNonNull(comparator, "comparator");
        Objects.requireNonNull(values, "values");
        return values.stream().min(comparator).orElse(null);
    }

    /**
     * Returns the maximum value from a collection.
     * @param comparator Comparator used for ordering.
     * @param values Input values.
     * @param <T> Value type.
     * @return Maximum value or null if empty.
     */
    public static <T> T max(Comparator<T> comparator,
                            Collection<T> values) {
        Objects.requireNonNull(comparator, "comparator");
        Objects.requireNonNull(values, "values");
        return values.stream().max(comparator).orElse(null);
    }

    /**
     * Checks equality using comparator ordering.
     * <p>
     *     Both values must be non-null.
     * </p>
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if values are equal according to comparator.
     */
    public static <T> boolean equals(Comparator<T> comparator,
                                     T a,
                                     T b) {
        Objects.requireNonNull(comparator,"comparator");
        return comparator.compare(a, b) == 0;
    }

    /**
     * Checks equality using comparator ordering with null-safety.
     * <p>
     *     Null values are treated as greater than non-null values.
     *     Two null values are considered equal.
     * </p>
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if values are equal according to comparator with null-safe ordering.
     */
    public static <T> boolean equalsNullSafe(Comparator<T> comparator,
                                             T a,
                                             T b) {
        return Comparator.nullsLast(comparator).compare(a, b) == 0;
    }

    /**
     * Checks if first value is less than second value.
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if first value is less.
     */
    public static <T> boolean lessThan(Comparator<T> comparator, T a, T b) {
        Objects.requireNonNull(comparator, "comparator");
        return comparator.compare(a, b) < 0;
    }

    /**
     * Checks if first value is less than or equal to second value.
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if first value is less or equal.
     */
    public static <T> boolean lessThanOrEqual(Comparator<T> comparator, T a, T b) {
        Objects.requireNonNull(comparator, "comparator");
        return comparator.compare(a, b) <= 0;
    }

    /**
     * Checks if first value is greater than second value.
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if first value is greater.
     */
    public static <T> boolean greaterThan(Comparator<T> comparator, T a, T b) {
        Objects.requireNonNull(comparator, "comparator");
        return comparator.compare(a, b) > 0;
    }

    /**
     * Checks if first value is greater than or equal to second value.
     * @param comparator Comparator used for ordering.
     * @param a First value.
     * @param b Second value.
     * @param <T> Value type.
     * @return True if first value is greater or equal.
     */
    public static <T> boolean greaterThanOrEqual(Comparator<T> comparator, T a, T b) {
        Objects.requireNonNull(comparator, "comparator");
        return comparator.compare(a, b) >= 0;
    }

    /**
     * Returns a sorted list from a collection.
     * @param comparator Comparator used for ordering.
     * @param values Input values.
     * @param <T> Value type.
     * @return Sorted list.
     */
    public <T> List<T> sort(Comparator<T> comparator,
                            Collection<T> values) {
        Objects.requireNonNull(comparator,"comparator");
        Objects.requireNonNull(values,"values");
        return values.stream().sorted(comparator).toList();
    }

    /**
     * Sorts a list in place.
     * @param comparator Comparator used for ordering.
     * @param values Input list.
     * @param <T> Value type.
     */
    public static <T> void sortInPlace(Comparator<T> comparator,
                                       List<T> values) {
        Objects.requireNonNull(comparator, "comparator");
        Objects.requireNonNull(values, "values");
        values.sort(comparator);
    }

    /**
     * Returns a reversed comparator.
     * @param comparator Input comparator.
     * @param <T> Value type.
     * @return Reversed comparator.
     */
    public static <T> Comparator<T> reversed(Comparator<T> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        return comparator.reversed();
    }
}
