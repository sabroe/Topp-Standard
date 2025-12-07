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

import com.yelstream.topp.standard.annotator.annotation.meta.Consideration;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Holder of an item being worked on.
 * <p>
 *     Applicable items are objects whose interfaces cannot be expanded,
 *     and which it may not be sound to wrap into a new, separate interface.
 *     To add functions to this type of item, it can be hold and hence wrapped and operated upon
 *     until it is released and to be used as-is.
 * </p>
 * <p>
 *     The idea to this was triggered by the wish to add rate-limitation to the newer SLF4J fluent API,
 *     where the item in question is {@link org.slf4j.spi.LoggingEventBuilder}.
 *     This ends up in wrapping a fluent API into another fluent API.
 * </p>
 * <p>
 *     Note that this is set up for the Self-Referential Generics pattern.
 *     This is intended to have separate sub-interfaces, possibly with a sub-interface to bind the
 *     self-referential generic followed by actual implementations.
 * </p>
 *
 * @param <A> Type of self object.
 *            This is the key to apply the Self-Referential Generics pattern.
 * @param <I> Type of item hold.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
public interface Anvil<A extends Anvil<A,I>,I> {
    /**
     * Exposes the hold item for direct usage.
     * @return Hold item.
     */
    I use();

    /**
     * Exposes the hold item to be worked on directly.
     * @param consumer Consumer working directly on the hold item.
     * @return Self.
     */
    A apply(Consumer<I> consumer);

    @Consideration
    default <A2 extends Anvil<A2,I2>,I2> A2 transform(Function<I,A2> operation) {
        return null;
    }

    /**
     * Performs any final operation need to complete all operations and frees all resources.
     */
    void end();
}
