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
 * Represents a supplier of results, potentially throwing a specific {@link Exception}.
 * <p>
 *     This interface is intended for use in contexts where the supplier is expected to handle specific,
 *     recoverable exceptions, such as IO operations or business logic errors. It allows for precise error
 *     handling by restricting the type of throwable to {@link Exception}.
 * </p>
 * <p>
 *     For more general scenarios where any kind of error, including runtime exceptions and system errors,
 *     might be thrown, consider using {@link SupplierWithThrowable}.
 * </p>
 *
 * @param <V> The type of results supplied by this supplier.
 * @param <E> Thrown in case of error.
 * @see SupplierWithThrowable
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-15
 */
@FunctionalInterface
public interface SupplierWithException<V, E extends Exception> {  //TO-DO: Consider the presence of this in overlap with other libraries of mine! MSM, 2024-07-11.
    /**
     * Gets a result.
     * @return A result.
     * @throws E Thrown in case of error.
     */
    V get() throws E;
}
