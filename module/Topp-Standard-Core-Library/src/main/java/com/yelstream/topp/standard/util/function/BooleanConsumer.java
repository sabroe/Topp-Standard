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
 * Consumer of {@code boolean} primitive values.
 * <p>
 *     As of Java SE 21, a {@link java.util.function.BooleanSupplier} exists
 *     while at the same time a {@code BooleanConsumer} has yet to be added.
 * </p>
 * <p>
 *     While {@code Consumer<Boolean>} may be the same as {@code BooleanConsumer} performance-wise,
 *     it is not the same when designing APIs since there are significant differences between
 *     the object {@link Boolean} and the primitive type {@code boolean}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-15
 */
@FunctionalInterface
public interface BooleanConsumer {
    /**
     * Performs this operation on the given argument.
     * @param value Argument.
     */
    void accept(boolean value);

    /**
     * Return a composed consumer.
     * @param after Operation to be performed after this operation.
     * @return Composed consumer.
     */
    default BooleanConsumer andThen(BooleanConsumer after) {
        Objects.requireNonNull(after);
        return (boolean t) -> { accept(t); after.accept(t); };
    }
}
