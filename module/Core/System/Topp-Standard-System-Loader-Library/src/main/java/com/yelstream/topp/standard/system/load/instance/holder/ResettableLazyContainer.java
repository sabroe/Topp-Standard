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

package com.yelstream.topp.standard.system.load.instance.holder;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Resettable container for a lazily initialized item.
 * <p>
 *     Computes the item on first access using a provided supplier, caches it,
 *     and allows resetting to trigger re-computation.
 * </p>
 * <p>
 *     This is thread-safe.
 * </p>
 *
 * @param <X> Type of the item held.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-09
 */@AllArgsConstructor(staticName="of")
final class ResettableLazyContainer<X> implements ResettableContainer<X> {
    /**
     * Item-supplier.
     */
    private final Supplier<X> itemSupplier;

    /**
     * Item, created on-demand.
     */
    private final AtomicReference<X> itemHolder=new AtomicReference<>();

    @Override
    public X getItem() {
        X item=itemHolder.get();
        if (item==null) {
            synchronized (itemHolder) {
                item=itemHolder.updateAndGet(v->v!=null?v:itemSupplier.get());
            }
        }
        return item;
    }

    @Override
    public void reset() {
        synchronized (itemHolder) {
            itemHolder.set(null);
        }
    }
}
