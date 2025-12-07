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

package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * Utility addressing instances of {@link Optional}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-23
 */
@UtilityClass
public class Optionals {
    /**
     * Indicates, if an optional is empty.
     * <p>
     *     Note that while this kind of testing is NOT the intention with a supposed-to-be fluent optional,
     *     not all APIs are created with this in mind, are used appropriately,
     *     leading to most defensive coding being necessary!
     * </p>
     * <p>
     *     This represents extreme carefulness.
     * </p>
     * @param optional Optional.
     *                 This may be {@code null}.
     * @return Indicats, if empty.
     * @param <T> Type of optional value.
     */
    @SuppressWarnings("all")
    public static <T> boolean isEmpty(Optional<T> optional) {
        return optional==null || optional.isEmpty();
    }

    /**
     * Gets the value contained in an optional
     * <p>
     *     Note that while this kind of testing is NOT the intention with a supposed-to-be fluent optional,
     *     not all APIs are created with this in mind, are used appropriately,
     *     leading to most defensive coding being necessary!
     * </p>
     * <p>
     *     This represents extreme carefulness.
     * </p>
     * @param optional Optional.
     *                 This may be {@code null}.
     * @return Value contained in optional.
     *         This may be {@code null}.
     * @param <T> Type of optional value.
     */
    @SuppressWarnings("all")
    public static <T> T get(Optional<T> optional) {
        return isEmpty(optional)?null:optional.get();
    }
}
