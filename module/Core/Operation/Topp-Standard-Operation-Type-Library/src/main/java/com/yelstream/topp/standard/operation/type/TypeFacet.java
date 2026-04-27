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

package com.yelstream.topp.standard.operation.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Type facet for a subject.
 * <p>
 *     Provides type-based operations on the subject value.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class TypeFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Indicates whether the subject value is an instance of a type.
     * @param type Type tested against.
     * @return True if the subject value is an instance of the type.
     */
    public boolean isInstance(Class<?> type) {
        Objects.requireNonNull(type, "type");
        return ObjectOps.isInstance(subject.getValue(), type);
    }

    /**
     * Executes an action if the subject value is an instance of a type.
     * @param type Type tested against.
     * @param action Action invoked.
     * @param <R> Target type.
     */
    public <R> void ifInstance(Class<R> type,
                               Consumer<R> action) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(action, "action");
        ObjectOps.ifInstance(subject.getValue(), type, action);
    }

    /**
     * Attempts to cast the subject to another type.
     * @param type Target type.
     * @param <R> Target type.
     * @return Optional containing the cast subject if successful.
     */
    public <R> Optional<Subject<R>> tryCast(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return subject.tryCast(type);
    }

    public <R> Subject<R> tryCastOrNull(Class<R> type) {
        return subject.tryCastOrNull(type);
    }

    public <R> Subject<R> tryCastOr(Class<R> type,
                                    R fallback) {
        return subject.tryCastOr(type,fallback);
    }

    public <R> Subject<R> tryCastOrGet(Class<R> type,
                                       Supplier<? extends R> fallbackSupplier) {
        return subject.tryCastOrGet(type,fallbackSupplier);
    }

    public <R> Subject<R> cast(Class<R> type) {
        return subject.cast(type);
    }







    /**
     * Casts the subject to another type.
     * @param type Target type.
     * @param <R> Target type.
     * @return Cast subject.
     * @throws ClassCastException Thrown if the subject value cannot be cast.
     */
    public <R> Subject<R> as(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return Subjects.as(subject, type);
    }
}
