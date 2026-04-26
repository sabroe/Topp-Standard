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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Standard null-policy handling.
 * <p>
 *     Provides predefined {@link NullPolicy} implementations for common null-handling strategies.
 *     These policies define how {@code null} values are treated when adapting a {@link java.util.Comparator}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@AllArgsConstructor
@SuppressWarnings("java:S115")
public enum StandardNullPolicy {
    /**
     * Strict null policy.
     * <p>
     *     Rejects {@code null} values by enforcing null checks during comparison.
     * </p>
     */
    Strict(NullPolicies.strict()),

    /**
     * Nulls-first policy.
     * <p>
     *     Orders {@code null} values before non-null values.
     * </p>
     */
    NullsFirst(NullPolicies.nullsFirst()),

    /**
     * Nulls-last policy.
     * <p>
     *     Orders {@code null} values after non-null values.
     * </p>
     */
    NullsLast(NullPolicies.nullsLast());

    /**
     * Underlying null-handling policy implementation.
     */
    @Getter
    private final NullPolicy policy;
}