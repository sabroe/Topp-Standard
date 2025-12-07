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

package com.yelstream.topp.standard.util.function;

import java.util.Objects;

/**
 * Consumer of three objects.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
@FunctionalInterface
public interface TriConsumer<T,U,V> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void accept(T t, U u, V v);

    /**
     * Return a composed consumer.
     * @param after Operation to be performed after this operation.
     * @return Composed consumer.
     */
    default TriConsumer<T,U,V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        Objects.requireNonNull(after);

        return (t,u,v) -> {
            accept(t,u,v);
            after.accept(t,u,v);
        };
    }
}
