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

package com.yelstream.topp.standard.operation.comparison.policy;

import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.Objects;

/**
 * Utilities addressing instances of {@link NullPolicy}.
 * <p>
 *     Provides factory methods for standard null-handling comparator policies.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@UtilityClass
public class NullPolicies {
    /**
     * Creates a strict null policy.
     * @return Null policy rejecting null values.
     */
    public static NullPolicy strict() {
        return new StrictNullPolicy();
    }

    /**
     * Creates a nulls-first policy.
     * @return Null policy ordering null values first.
     */
    public static NullPolicy nullsFirst() {
        return new NullsFirstNullPolicy();
    }

    /**
     * Creates a nulls-last policy.
     * @return Null policy ordering null values last.
     */
    public static NullPolicy nullsLast() {
        return new NullsLastNullPolicy();
    }

    /**
     * Strict null policy implementation.
     */
    private static final class StrictNullPolicy implements NullPolicy {
        @Override
        public <T> Comparator<T> create(Comparator<T> comparator) {
            Objects.requireNonNull(comparator, "comparator");
            return new Comparator<T>() {
                @Override
                public int compare(T a, T b) {
                    Objects.requireNonNull(a);
                    Objects.requireNonNull(b);
                    return comparator.compare(a, b);
                }
            };
        }
    }

    /**
     * Nulls-first policy implementation.
     */
    private static final class NullsFirstNullPolicy implements NullPolicy {
        @Override
        public <T> Comparator<T> create(Comparator<T> comparator) {
            Objects.requireNonNull(comparator, "comparator");
            return Comparator.nullsFirst(comparator);
        }
    }

    /**
     * Nulls-last policy implementation.
     */
    private static final class NullsLastNullPolicy implements NullPolicy {
        @Override
        public <T> Comparator<T> create(Comparator<T> comparator) {
            Objects.requireNonNull(comparator, "comparator");
            return Comparator.nullsLast(comparator);
        }
    }
}
