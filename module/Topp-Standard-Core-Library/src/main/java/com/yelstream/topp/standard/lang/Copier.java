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

/**
 * Strategy interface for creating copies of objects.
 *
 * <p>This interface represents a reusable copy strategy that can be applied
 * to objects independently of their implementation. It allows copying behavior
 * to be defined externally rather than being embedded within the object itself.</p>
 *
 * <p>Implementations may define different copy semantics, such as shallow copy,
 * deep copy, partial copy, or transformation-based copy.</p>
 *
 * <p>
 *     This abstraction is particularly useful when:
 * </p>
 * <ul>
 *     <li>Multiple copy strategies are required for the same type</li>
 *     <li>Working with types that do not implement {@link Copyable}</li>
 *     <li>Decoupling copy logic from the domain model</li>
 * </ul>
 *
 * <p>The exact nature of the copy (e.g. depth, mutability, structural sharing)
 * is defined by the implementation.</p>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * Copier<MyType> deepCopier = source -> {
 *     MyType copy = new MyType();
 *     copy.setValue(source.getValue());
 *     return copy;
 * };
 *
 * MyType result = deepCopier.copy(original);
 * }</pre>
 *
 * @param <T> The type of object being copied.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-22
 */
public interface Copier<T> {
    /**
     * Creates a copy of the given source object.
     * @param source The object to copy.
     * @return A copy of the source object.
     */
    T copy(T source);
}
