/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Supplier}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-23
 */
@UtilityClass
public class Suppliers {
    /**
     * Creates a new supplier which is fixed in its value.
     * @param value Supplied value.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fix(T value) {
        return ()->value;
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fix(Supplier<T> supplier) {
        return fixInAdvance(supplier);
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called immediately and up-front.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fixInAdvance(Supplier<T> supplier) {
        if (supplier==null) {
            return null;
        } else {
            T value=supplier.get();
            return ()->value;
        }
    }

    /**
     * Creates a new supplier which is fixed in its value.
     * @param supplier Existing supplier.
     *                 This is called only on-demand.
     * @return Created supplier.
     *         On multiple invocations, the supplied value is unchanged.
     * @param <T> Type of supplied value.
     */
    public static <T> Supplier<T> fixOnDemand(Supplier<T> supplier) {
        return supplier==null?null:MemoizedSupplier.Strategy.DoubleChecked.of(supplier);
    }

    public static <T> List<T> substantiate(List<Supplier<T>> suppliers) {
        return suppliers==null?null:suppliers.stream().filter(Objects::nonNull).map(Supplier::get).toList();
    }
}
