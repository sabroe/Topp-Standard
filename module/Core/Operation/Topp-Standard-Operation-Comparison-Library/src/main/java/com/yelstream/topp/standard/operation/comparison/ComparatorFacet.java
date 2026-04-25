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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ComparatorFacet<T> {
    @NonNull
    public final Comparator<T> comparator;

    public T min(T a,
                 T b) {
        Objects.requireNonNull(comparator,"comparator");
        return Comparators.min(comparator, a, b);
    }

    public T max(T a,
                 T b) {
        Objects.requireNonNull(comparator,"comparator");
        return Comparators.max(comparator, a, b);
    }

    public boolean equals(T a,
                          T b) {
        Objects.requireNonNull(comparator,"comparator");
        return Comparators.equals(comparator, a, b);
    }

    public List<T> sort(Collection<T> values) {
        Objects.requireNonNull(comparator,"comparator");
        Objects.requireNonNull(values,"values");
        return Comparators.sort(comparator,values);
    }


}
