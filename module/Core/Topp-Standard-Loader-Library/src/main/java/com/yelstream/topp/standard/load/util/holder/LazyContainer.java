/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.load.util.holder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * A container for a lazily initialized item.
 * <p>
 * Computes the item on first access using a provided supplier and caches it for subsequent access.
 * Thread-safe for concurrent access.
 * </p>
 *
 * @param <X> Type of the item held.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-09
 */@AllArgsConstructor(staticName="of")
final class LazyContainer<X> implements Container<X> {
    /**
     * Item-supplier.
     */
    private final Supplier<X> itemSupplier;

    /**
     * Item, created on-demand.
     */
    @Getter(lazy=true)
    private final X item=itemSupplier.get();
}
