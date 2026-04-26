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
import java.util.function.Consumer;

/**
 * Equality facet for a subject.
 * <p>
 *     Provides equality-based operations on the subject value using {@link Objects#equals(Object, Object)}.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class EqualityFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Indicates whether the subject value is equal to another value.
     * @param value Value compared to.
     * @return True if equal.
     */
    public boolean isEquals(T value) {
        return Objects.equals(subject.getValue(), value);
    }

    /**
     * Invokes a consumer if the subject value is equal to another value.
     * @param value Value compared to.
     * @param consumer Consumer invoked.
     */
    public void ifEquals(T value, Consumer<T> consumer) {
        if (isEquals(value)) {
            consumer.accept(subject.getValue());
        }
    }
}
