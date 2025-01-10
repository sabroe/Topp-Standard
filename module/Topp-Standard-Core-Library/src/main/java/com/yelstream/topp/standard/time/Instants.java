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

package com.yelstream.topp.standard.time;

import com.yelstream.topp.standard.lang.Comparables;
import lombok.experimental.UtilityClass;

import java.time.Instant;

/**
 * Utility addressing instances of {@link Instant}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-24
 */
@UtilityClass
public class Instants {
    /**
     * Gets the minimum of two instants.
     * <p>
     *     Note that instants may be {@code null}.
     * </p>
     * @param a First instant.
     *          This may be {@code null}.
     * @param b Second instant.
     *          This may be {@code null}.
     * @return Minimum instant.
     *         This may be {@code null}.
     */
    public static Instant min(Instant a, Instant b) {
        return Comparables.min(a,b);
    }

    /**
     * Gets the maximum of two instants.
     * <p>
     *     Note that instants may be {@code null}.
     * </p>
     * @param a First instant.
     *          This may be {@code null}.
     * @param b Second instant.
     *          This may be {@code null}.
     * @return Maximum instant.
     *         This may be {@code null}.
     */
    public static Instant max(Instant a, Instant b) {
        return Comparables.max(a,b);
    }

    /**
     * Indicates, if two instants are equal.
     * <p>
     *     Note that instants may be {@code null}.
     * </p>
     * @param a First instant.
     *          This may be {@code null}.
     * @param b Second instant.
     *          This may be {@code null}.
     * @return Indicates, if instants are equal.
     *         This may be {@code null}.
     */
    public static boolean equals(Instant a, Instant b) {
        return Comparables.equals(a,b);
    }

}
