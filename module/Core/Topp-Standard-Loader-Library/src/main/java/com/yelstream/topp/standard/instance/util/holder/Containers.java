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

package com.yelstream.topp.standard.instance.util.holder;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * Utility for creating container instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-09
 */
@UtilityClass
public class Containers {
    /**
     * Creates a container with a pre-computed item.
     * @param <X> Type of the item.
     * @param item Item to hold.
     * @return Created container.
     */
    public static <X> Container<X> createContainer(X item) {
        return SimpleContainer.of(item);
    }

    /**
     * Creates a container for a lazily initialized item.
     *
     * @param <X> Type of the item.
     * @param itemSupplier Supplier to compute the item.
     * @return Created container.
     */
    public static <X> Container<X> createContainer(Supplier<X> itemSupplier) {
        return LazyContainer.of(itemSupplier);
    }

    /**
     * Creates a resettable container for a lazily initialized item.
     * @param <X> Type of the item.
     * @param itemSupplier Supplier to compute the item.
     * @return Created container.
     */
    public static <X> ResettableContainer<X> createResettableContainer(Supplier<X> itemSupplier) {
        return ResettableLazyContainer.of(itemSupplier);
    }
}
