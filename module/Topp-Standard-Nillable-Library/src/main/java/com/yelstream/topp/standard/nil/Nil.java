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

/**
 * Nil with its associated value.
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
public final class Nil<T> {
    /**
     * Value representing nil.
     */
    @Getter
    private final T value;

    /**
     * Tests, if a value is the nil value.
     * @param value Value to test.
     * @return Indicates, if a value is the nul value.
     */
    public boolean isNil(T value) {
        if (this.value==null) {
            return value==null;
        } else {
            return this.value.equals(value);
        }
    }
}
