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
 * Identity facet for a subject.
 * <p>
 *     Provides identity-based operations on the subject value using reference
 *     equality and identity hash codes.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class IdentityFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Gets the identity hash code of the subject value.
     * @return Identity hash code.
     */
    public int identityHash() {
        return subject.identityHash();
    }

    public String identityString() {
        return subject.identityString();
    }

    /**
     * Indicates whether another value is the same instance as the subject value.
     * @param value Value compared to.
     * @return True if both values are the same instance.
     */
    public boolean isSame(T value) {
        return value == subject.getValue();
    }

    /**
     * Invokes a consumer if another value is the same instance as the subject value.
     * @param value Value compared to.
     * @param consumer Consumer invoked.
     */
    public void ifSame(T value,
                       Consumer<T> consumer) {
        if (isSame(value)) {
            consumer.accept(value);
        }
    }
}
