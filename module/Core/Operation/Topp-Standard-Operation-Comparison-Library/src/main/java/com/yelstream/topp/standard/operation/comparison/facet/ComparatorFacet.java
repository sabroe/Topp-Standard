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

package com.yelstream.topp.standard.operation.comparison.facet;

import com.yelstream.topp.standard.operation.comparison.Comparators;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Facet for comparator-based operations.
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
public class ComparatorFacet<T> {
    /**
     * Comparator used for operations.
     */
    @NonNull
    public final Comparator<T> comparator;

    /**
     * Returns the underlying comparator.
     * @return Comparator.
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * Returns the minimum of two values.
     * @param a First value.
     * @param b Second value.
     * @return Minimum value.
     */
    public T min(T a, T b) {
        return Comparators.min(comparator, a, b);
    }

    /**
     * Returns the maximum of two values.
     * @param a First value.
     * @param b Second value.
     * @return Maximum value.
     */
    public T max(T a, T b) {
        return Comparators.max(comparator, a, b);
    }

    /**
     * Returns the minimum of two values.
     * <p>
     *     Null values are ordered last.
     * </p>
     * @param a First value.
     * @param b Second value.
     * @return Minimum value.
     */
    public T minNullsLast(T a, T b) {
        return Comparators.minNullsLast(comparator, a, b);
    }

    /**
     * Returns the maximum of two values.
     * <p>
     *     Null values are ordered first.
     * </p>
     * @param a First value.
     * @param b Second value.
     * @return Maximum value.
     */
    public T maxNullsFirst(T a, T b) {
        return Comparators.maxNullsFirst(comparator, a, b);
    }

    /**
     * Checks equality using comparator ordering.
     * @param a First value.
     * @param b Second value.
     * @return True if equal.
     */
    public boolean equals(T a, T b) {
        return Comparators.equals(comparator, a, b);
    }

    /**
     * Checks equality using comparator ordering with null-safety.
     * @param a First value.
     * @param b Second value.
     * @return True if equal.
     */
    public boolean equalsNullSafe(T a, T b) {
        return Comparators.equalsNullSafe(comparator, a, b);
    }

    /**
     * Checks if first value is less than second value.
     * @param a First value.
     * @param b Second value.
     * @return True if less.
     */
    public boolean lessThan(T a, T b) {
        return Comparators.lessThan(comparator, a, b);
    }

    /**
     * Checks if first value is less than or equal to second value.
     * @param a First value.
     * @param b Second value.
     * @return True if less or equal.
     */
    public boolean lessThanOrEqual(T a, T b) {
        return Comparators.lessThanOrEqual(comparator, a, b);
    }

    /**
     * Checks if first value is greater than second value.
     * @param a First value.
     * @param b Second value.
     * @return True if greater.
     */
    public boolean greaterThan(T a, T b) {
        return Comparators.greaterThan(comparator, a, b);
    }

    /**
     * Checks if first value is greater than or equal to second value.
     * @param a First value.
     * @param b Second value.
     * @return True if greater or equal.
     */
    public boolean greaterThanOrEqual(T a, T b) {
        return Comparators.greaterThanOrEqual(comparator, a, b);
    }

    /**
     * Returns the minimum value from a collection.
     * @param values Input values.
     * @return Minimum value or null.
     */
    public T min(Collection<T> values) {
        return Comparators.min(comparator, values);
    }

    /**
     * Returns the maximum value from a collection.
     * @param values Input values.
     * @return Maximum value or null.
     */
    public T max(Collection<T> values) {
        return Comparators.max(comparator, values);
    }

    /**
     * Returns a sorted list.
     * @param values Input values.
     * @return Sorted list.
     */
    public List<T> sort(Collection<T> values) {
        return Comparators.sort(comparator, values);
    }

    /**
     * Sorts a list in place.
     * @param values Input list.
     */
    public void sortInPlace(List<T> values) {
        Comparators.sortInPlace(comparator, values);
    }

    /**
     * Returns a reversed comparator.
     * @return Reversed comparator.
     */
    public Comparator<T> reversed() {
        return Comparators.reversed(comparator);
    }
}
