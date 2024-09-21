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

import java.util.function.BiConsumer;

/**
 * Abstract implementation of {@link BiAnvil}.
 *
 * @param <A> Type of self object.
 *            This is the key to apply the Self-Referential Generics pattern.
 * @param <C> Type of context.
 *            The context is intended to be informational only.
 * @param <I> Type of item hold.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
public abstract class AbstractBiAnvil<A extends BiAnvil<A,C,I>,C,I> extends AbstractAnvil<A,I> implements BiAnvil<A,C,I> {
    /**
     * Context.
     * Essentially intended as a work log or statistics about what has been done to the held work item.
     * This is not intended for replacement.
     */
    protected final C context;

    protected AbstractBiAnvil(C context,
                              I item) {
        super(item);
        this.context=context;
    }

    @Override
    public A apply(BiConsumer<C,I> consumer) {
        consumer.accept(context,item);
        return self();
    }
}
