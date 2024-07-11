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

/**
 * Represents a supplier of results, potentially throwing any kind of {@link Throwable}.
 * <p>
 *     This interface is intended for use in contexts where the supplier may throw any kind of error,
 *     including runtime exceptions and serious system errors. It is suitable for low-level system operations
 *     or experimental features where comprehensive error handling is required.
 * </p>
 * <p>
 *     For scenarios where only recoverable, expected exceptions are to be handled,
 *     consider using {@link SupplierWithException} instead.
 * </p>
 *
 * @param <V> The type of results supplied by this supplier.
 * @param <E> Thrown in case of error.
 * @see SupplierWithException
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-15
 */
@FunctionalInterface
public interface SupplierWithThrowable<V, E extends Throwable> {  //TO-DO: Consider the presence of this in overlap with other libraries of mine! MSM, 2024-07-11.
    /**
     * Gets a result.
     * @return A result.
     * @throws E Thrown in case of error.
     */
    V get() throws E;
}
