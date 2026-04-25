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
import java.util.function.Predicate;

/**
 *
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ValidationFacet<T> {

    @NonNull
    public final Subject<T> subject;

    /**
     * Requires subject value to be non-null.
     */
    public Subject<T> nonNull() {
        Objects.requireNonNull(subject.getValue(), "Subject value is null!");
        return subject;
    }

    /**
     * Requires subject value to be instance of type.
     */
    public Subject<T> isInstance(Class<?> type) {
        Objects.requireNonNull(type, "type");

        if (!ObjectOps.isInstance(subject.getValue(), type)) {
            throw new IllegalArgumentException(
                    "Subject value type '%s' is not instance of '%s'!"
                            .formatted(
                                    ObjectOps.getName(subject.getValue()),
                                    ClassOps.getName(type)
                            )
            );
        }
        return subject;
    }

    /**
     * Requires predicate to match.
     */
    public Subject<T> matches(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate");

        if (!predicate.test(subject.getValue())) {
            throw new IllegalArgumentException(
                    "Subject value '%s' does not satisfy predicate!"
                            .formatted(subject.getValue())
            );
        }
        return subject;
    }
}
