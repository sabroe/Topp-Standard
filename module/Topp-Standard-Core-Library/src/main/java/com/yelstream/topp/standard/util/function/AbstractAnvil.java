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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

/**
 * Abstract implementation of {@link Anvil}.
 *
 * @param <A> Type of self object.
 *            This is the key to apply the Self-Referential Generics pattern.
 * @param <I> Type of item hold.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public abstract class AbstractAnvil<A extends Anvil<A,I>,I> implements Anvil<A,I> {
    /**
     *
     */
    protected final I item;

    /**
     *
     */
    protected abstract A self();

    @Override
    public I use() {
        return item;
    }

    @Override
    public A apply(Consumer<I> consumer) {
        consumer.accept(item);
        return self();
    }

    /**
     *
     */
    @Override
    public void end() {
        //Empty!
    }
}
