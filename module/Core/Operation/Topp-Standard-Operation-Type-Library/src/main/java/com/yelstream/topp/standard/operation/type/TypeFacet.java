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

/**
 *
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class TypeFacet<T> {
    @NonNull
    public final Subject<T> subject;

    /**
     * Checks whether the underlying value is instance of type.
     */
    public boolean isInstance(Class<?> type) {
        Objects.requireNonNull(type, "type");
        return ObjectOps.isInstance(subject.getValue(), type);
    }

    /**
     * Conditional execution if type matches.
     */
    public <R> void ifInstance(Class<R> type,
                               Consumer<R> action) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(action, "action");
        ObjectOps.ifInstance(subject.getValue(), type, action);
    }

    /**
     * Optional-style exposure (if you want it here).
     */
    public <R> Optional<Subject<R>> tryCast(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return Subjects.tryCast(subject,type);
    }

    public <R> Subject<R> as(Class<R> type) {
        Objects.requireNonNull(type, "type");
        return Subjects.as(subject,type);
    }
}
