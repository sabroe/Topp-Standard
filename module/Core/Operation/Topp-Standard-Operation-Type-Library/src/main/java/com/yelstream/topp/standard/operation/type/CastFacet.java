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
import java.util.function.Supplier;

/**
 * Cast facet for a subject.
 * <p>
 *     Provides casting operations on the subject value with stratified failure semantics:
 * </p>
 * <pre>
 * ┌──────────────────┬──────────────────────┬─────────────────────┐
 * │ Method           │ Null input           │ Wrong type          │
 * ├──────────────────┼──────────────────────┼─────────────────────┤
 * │ tryCast          │ empty                │ empty               │
 * ├──────────────────┼──────────────────────┼─────────────────────┤
 * │ tryCastOrNull    │ null                 │ null                │
 * ├──────────────────┼──────────────────────┼─────────────────────┤
 * │ tryCastOr/OrGet  │ fallback             │ fallback            │
 * ├──────────────────┼──────────────────────┼─────────────────────┤
 * │ cast             │ NullPointerException │ ClassCastException  │
 * └──────────────────┴──────────────────────┴─────────────────────┘
 * </pre>
 * <p>
 *     For runtime type-inspection operations, see {@link TypeFacet}.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class CastFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Attempts to cast the subject value to a type.
     * @param type Target type.
     * @param <R> Target type.
     * @return Optional containing the cast subject if successful.
     */
    public <R> Optional<Subject<R>> tryCast(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return subject.tryCast(type);
    }

    /**
     * Attempts to cast the subject value to a type.
     * @param type Target type.
     * @param <R> Target type.
     * @return Cast subject, or {@code null} if the cast is not possible.
     */
    public <R> Subject<R> tryCastOrNull(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return subject.tryCastOrNull(type);
    }

    /**
     * Attempts to cast the subject value to a type.
     * @param type Target type.
     * @param fallback Fallback value if cast is not possible.
     * @param <R> Target type.
     * @return Cast subject, or a subject holding the fallback value.
     */
    public <R> Subject<R> tryCastOr(Class<R> type,
                                    R fallback) {
        Objects.requireNonNull(type, "type");
        return subject.tryCastOr(type, fallback);
    }

    /**
     * Attempts to cast the subject value to a type.
     * @param type Target type.
     * @param fallbackSupplier Supplier of fallback value if cast is not possible.
     * @param <R> Target type.
     * @return Cast subject, or a subject holding the supplied fallback value.
     */
    public <R> Subject<R> tryCastOrGet(Class<R> type,
                                       Supplier<? extends R> fallbackSupplier) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier");
        return subject.tryCastOrGet(type, fallbackSupplier);
    }

    /**
     * Casts the subject value to a type.
     * @param type Target type.
     * @param <R> Target type.
     * @return Cast subject.
     * @throws NullPointerException If the subject value is null.
     * @throws ClassCastException If the subject value is not of the target type.
     */
    public <R> Subject<R> cast(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return subject.cast(type);
    }

    /**
     * Attempts to cast the subject value to a type, returning an empty subject on failure.
     * @param type Target type.
     * @param <R> Target type.
     * @return Cast subject, or an empty subject if the cast is not possible.
     */
    public <R> Subject<R> as(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return Subjects.as(subject, type);
    }
}
