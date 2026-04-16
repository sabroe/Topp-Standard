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

package com.yelstream.topp.standard.time.policy;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Strategy for handling {@code null} values in a functional style.
 * <p>
 *     This decouples the decision of how {@code null} should be treated from the actual business logic.
 *     It allows time-related operations (parsing, formatting, conversion, arithmetic, etc.)
 *     to accept a null-handling policy as a parameter.
 * </p>
 * <p>
 *     Implementations decide whether to:
 * </p>
 * <ul>
 *   <li>Reject {@code null} (fail-fast)</li>
 *   <li>Treat {@code null} as absent and provide a fallback</li>
 *   <li>Pass {@code null} through to the handling function</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-16
 */
public interface NullPolicy {
    /**
     * Applies this null policy to an input value.
     * @param input Input value.
     *              May be {@code null} depending on the policy.
     * @param present Function to apply when the input is considered "present"
     *                Must not be {@code null}.
     * @param absent Supplier of a fallback value when the input is considered "absent".
     *               Must not be {@code null}.
     * @return Result according to policy.
     * @throws NullPointerException Thrown if {@code present} or {@code absent} is {@code null}.
     *                              Some policies may additionally throw when {@code input} is {@code null}.
     * @param <T> Type of the input.
     * @param <R> Type of the result.
     */
    <T,R> R apply(T input,
                  Function<T, R> present,
                  Supplier<R> absent);
}
