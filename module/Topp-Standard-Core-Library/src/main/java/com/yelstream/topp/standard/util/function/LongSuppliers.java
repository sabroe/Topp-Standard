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

package com.yelstream.topp.standard.util.function;

import lombok.experimental.UtilityClass;

import java.util.function.LongSupplier;

/**
 * Utility addressing instances of {@link LongSupplier}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-29
 */
@UtilityClass
public class LongSuppliers {
    /**
     * Creates a new supplier which is fixed in its value.
     * @param value Supplied value.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fix(long value) {
        return ()->value;
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fix(LongSupplier supplier) {
        return fixInAdvance(supplier);
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fixInAdvance(LongSupplier supplier) {
        if (supplier==null) {
            return null;
        } else {
            long value=supplier.getAsLong();
            return ()->value;
        }
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called only on-demand.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     */
    public static LongSupplier fixOnDemand(LongSupplier supplier) {
        return supplier==null?null:MemoizedLongSupplier.Strategy.DoubleChecked.of(supplier);
    }
}
