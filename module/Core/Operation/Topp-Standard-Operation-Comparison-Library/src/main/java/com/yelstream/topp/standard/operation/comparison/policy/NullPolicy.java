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

import java.util.Comparator;

/**
 * Null-policy handling for comparator-based operations.
 * <p>
 *     A NullPolicy defines how {@code null} values are handled when constructing or adapting a {@link Comparator}.
 *     It acts as a strategy for transforming a base comparator into a null-aware comparator without modifying
 *     the original comparator semantics directly.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
public interface NullPolicy {
    /**
     * Creates a null-aware comparator based on the given base comparator.
     *
     * @param base Base comparator to adapt.
     *             Must not be {@code null}.
     * @param <T> Type being compared.
     * @return A comparator with null-handling policy applied.
     */
    <T> Comparator<T> create(Comparator<T> base);
}
