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

package com.yelstream.topp.standard.log.resist.slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @param <A> Type of self object.
 * @param <C> Type of context.
 * @param <R> Type of resulting item.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
public interface Anvil<A extends Anvil<A,C,R>,C,R> {  //TO-DO: Consider placement and typing; this is not really specific for SLF4J!
    /**
     *
     */
    R use();

    /**
     * Accesses the result of log filtering by handing it to a consumer.
     * @param consumer Receiver of the result.
     */
    A apply(Consumer<R> consumer);

    /**
     * Accesses the result of log filtering together with the context by handing these objects to a consumer.
     * @param consumer Receiver of the context and the result.
     */
    A apply(BiConsumer<C,R> consumer);

    /**
     *
     */
    void end();
}
