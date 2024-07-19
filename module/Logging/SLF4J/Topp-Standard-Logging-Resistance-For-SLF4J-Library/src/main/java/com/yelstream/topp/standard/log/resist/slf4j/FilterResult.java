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

import lombok.AllArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Provides access to the result of log filtering operations,
 * with optional access to the context of the filtering process and its result.
 * <p>
 *     The result can be retrieved directly or handed to a consumer and optionally with the context.
 * </p>
 * @param <C> Type of context.
 * @param <R> Type of result.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@AllArgsConstructor(staticName="of")
public class FilterResult<C,R> {
    /**
     * Accesses to the context maintained during log filtering.
     * This may be simple statistics.
     */
    private final Supplier<C> contextSupplier;

    /**
     * Result of log filtering.
     */
    private final R result;

    /**
     * Accesses the result of log filtering.
     * @return Result of log filtering.
     */
    public R use() {
        return result;
    }

    /**
     * Accesses the result of log filtering together with the context by handing these objects to a consumer.
     * @param consumer Receiver of the context and the result.
     */
    public void apply(BiConsumer<C,R> consumer) {
        C context=contextSupplier.get();
        consumer.accept(context,result);
    }

    /**
     * Accesses the result of log filtering by handing it to a consumer.
     * @param consumer Receiver of the result.
     */
    public void apply(Consumer<R> consumer) {
        consumer.accept(result);
    }
}
