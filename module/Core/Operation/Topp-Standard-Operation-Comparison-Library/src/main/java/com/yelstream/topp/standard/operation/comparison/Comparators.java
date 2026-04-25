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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility addressing instances of {@link Comparator}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-19
 */
@UtilityClass
public class Comparators {

    /**
     * Creates a facet for a given comparator.
     * @param comparator Comparator.
     * @param <T> Type of value.
     * @return Created facet.
     */
    public static <T> ComparatorFacet<T> facet(java.util.Comparator<T> comparator) {
        return ComparatorFacet.of(comparator);
    }

    public static <T> T min(Comparator<T> comparator,
                            T a,
                            T b) {
        Objects.requireNonNull(comparator,"comparator");
        return comparator.compare(a, b) <= 0 ? a : b;
    }

    public static <T> T max(Comparator<T> comparator,
                            T a,
                            T b) {
        Objects.requireNonNull(comparator,"comparator");
        return comparator.compare(a, b) >= 0 ? a : b;
    }

    public static <T> boolean equals(Comparator<T> comparator,
                                     T a,
                                     T b) {
        Objects.requireNonNull(comparator,"comparator");
        return comparator.compare(a, b) == 0;
    }

    public <T> List<T> sort(Comparator<T> comparator,
                            Collection<T> values) {
        Objects.requireNonNull(comparator,"comparator");
        Objects.requireNonNull(values,"values");
        return values.stream().sorted(comparator).toList();
    }
}
