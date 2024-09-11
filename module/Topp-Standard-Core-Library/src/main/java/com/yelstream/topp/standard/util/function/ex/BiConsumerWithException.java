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

package com.yelstream.topp.standard.util.function.ex;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result.  This is the two-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code BiConsumer} is expected
 * to operate via side effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <E> Thrown in case of error.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-15
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface BiConsumerWithException<T, U, E extends Exception> {  //TO-DO: Consider the presence of this in overlap with other libraries of mine! MSM, 2024-09-11.
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     * @throws E Thrown in case of error.
     */
    void accept(T t, U u) throws E;

    /**
     * Returns a composed {@code BiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return A composed {@code BiConsumer} that performs in sequence this
     *     operation followed by the {@code after} operation.
     * @throws NullPointerException If {@code after} is null.
     */
    default BiConsumerWithException<T, U, E> andThen(BiConsumerWithException<? super T, ? super U, ? extends E> after) {
        Objects.requireNonNull(after);

        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}
