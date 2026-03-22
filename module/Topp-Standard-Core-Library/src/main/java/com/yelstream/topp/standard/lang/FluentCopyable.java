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

package com.yelstream.topp.standard.lang;

import java.util.function.Consumer;

/**
 * Extension of {@link Copyable} supporting fluent-style copying with
 * inline configuration and external handling.
 *
 * <p>
 *     This interface defines a pattern where a copy of an object is created
 *     and passed to a {@link Consumer} for immediate configuration or use.
 *     The consumer is expected to initialize, modify, or assign the copy as needed.
 * </p>
 * <h2>Type Parameters</h2>
 * <ul>
 *   <li>
 *       <b>S</b> – The fluent return type, typically the implementing type
 *       itself, enabling method chaining.
 *   </li>
 *   <li>
 *       <b>T</b> – The type of the copied object.
 *   </li>
 * </ul>
 *
 * <h2>Semantics</h2>
 * <ul>
 *   <li>
 *       A new instance of {@code T} is created as a copy of the original object.
 *   </li>
 *   <li>
 *       The copy is passed to the provided {@code consumer}.
 *   </li>
 *   <li>
 *       The consumer may freely modify, initialize, or assign the copy.
 *   </li>
 *   <li>
 *       The method returns a value of type {@code S} to support fluent usage.
 *   </li>
 * </ul>
 * <p>
 *     The copy is not required to be immutable, and the purpose of the consumer
 *     is not limited to mutation—it may also be used to pass the copy to other
 *     components, store it, or otherwise integrate it into surrounding state.
 * </p>
 * <p>
 *     The exact nature of the copy (shallow vs. deep) is implementation-specific
 *     and should be documented by implementations.<
 * </p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 *     holder.copy(copy -> holder.setValue(copy));
 *
 *     //or with inline configuration:
 *     holder.copy(copy -> {
 *                     copy.setX(42);
 *                     registry.register(copy);
 *                });
 * }</pre>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>
 *       This pattern separates copy creation from its usage, allowing the caller
 *       to decide how the copy is handled.
 *   </li>
 *   <li>
 *       The {@link Consumer} acts as a flexible hook for initialization,
 *       mutation, or assignment.
 *   </li>
 *   <li>
 *       The separation of {@code S} and {@code T} allows the fluent API type
 *       to differ from the copied object type.
 *   </li>
 * </ul>
 *
 * @param <S> The fluent return type.
 * @param <T> The type of the copied object.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-10-21
 */
public interface FluentCopyable<S, T> extends Copyable<T> {
    /**
     * Creates a copy of this object and passes it to the provided consumer.
     * <p>
     *     The consumer may initialize, modify, or otherwise handle the copy,
     *     including assigning it to external state.
     * </p>
     * @param consumer Consumer that receives the copied instance.
     * @return A fluent result of type {@code S}.
     */
    S copy(Consumer<T> consumer);
}
