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

import java.util.Objects;

/**
 * Consumer of {@code byte} primitive values.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-15
 */
@FunctionalInterface
public interface ByteConsumer {
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
    default ByteConsumer andThen(ByteConsumer after) {
        Objects.requireNonNull(after);
        return (boolean t) -> { accept(t); after.accept(t); };
    }
}
