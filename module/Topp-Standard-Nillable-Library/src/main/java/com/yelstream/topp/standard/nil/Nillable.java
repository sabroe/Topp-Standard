/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.nil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Represent a value that can be null, nil, or an actual value.
 * <p>
 *     This is immutable.
 * </p>
 * @param <T> Type of value.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
@AllArgsConstructor(access=AccessLevel.PUBLIC,staticName="of")
@EqualsAndHashCode
@ToString
public final class Nillable<T> {
    /**
     *
     */
    @Getter
    private final T value;

    /**
     *
     */
    @Getter
    private final Nil<T> nil;

    /**
     * Tests, if the value is {@code null}.
     * @return Indicates, if the value is {@code null}.
     */
    public boolean isNull() {
        return value==null;
    }

    /**
     * Tests, if the value is {@code nil}.
     * @return Indicates, if the value is {@code nil}.
     */
    public boolean isNil() {
        return nil.isNil(value);
    }

    /**
     * Tests, if the value is present (non-null, non-nil).
     * @return Indicates, if the value is present.
     */
    public boolean isPresent() {
        return !isNull() && !isNil();
    }

    /**
     * Applies an action based on the state of this nillable value.
     * @param action The action to apply.
     */
    public void apply(NillableAction<T> action) {
        if (isNull()) {
            action.onNull();
        } else if (isNil()) {
            action.onNil();
        } else {
            action.onPresent(value);
        }
    }

    public static <T> Nillable<T> nil(Nil<T> nil) {
        return Nillable.of(nil.getValue(), nil);
    }

    public static <T> Nillable<T> nullValue(Nil<T> nil) {
        return Nillable.of(null, nil);
    }
}
