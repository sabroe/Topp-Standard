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

package com.yelstream.topp.standard.util.function.ex;

import java.util.Objects;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> The type of the input to the function.
 * @param <R> The type of the result of the function.
 * @param <E> Thrown in case of error.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-15
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface FunctionWithException<T, R, E extends Exception> {  //TO-DO: Consider the presence of this in overlap with other libraries of mine! MSM, 2024-09-11.
    /**
     * Applies this function to the given argument.
     * @param t the function argument
     * @return the function result
     * @throws E Thrown in case of error.
     */
    R apply(T t) throws E;

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of input to the {@code before} function, and to the
     *           composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     *     function and then applies this function
     * @throws NullPointerException if before is null
     *
     * @see #andThen(FunctionWithException)
     */
    default <V> FunctionWithException<V, R, E> compose(FunctionWithException<? super V, ? extends T, ? extends E> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V> the type of output of the {@code after} function, and of the
     *           composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     *     applies the {@code after} function
     * @throws NullPointerException if after is null
     *
     * @see #compose(FunctionWithException)
     */
    default <V> FunctionWithException<T, V, E> andThen(FunctionWithException<? super R, ? extends V, ? extends E> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> The type of the input and output objects to the function.
     * @param <E> Type of error.
     * @return A function that always returns its input argument.
     */
    static <T, E extends Exception> FunctionWithException<T, T, E> identity() {
        return t -> t;
    }
}
