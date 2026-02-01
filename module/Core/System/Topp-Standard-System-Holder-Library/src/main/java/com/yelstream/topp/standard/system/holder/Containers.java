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

package com.yelstream.topp.standard.system.holder;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
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

    public static <X> Container<X> simple(Supplier<X> itemSupplier) {
        return SimpleContainer.of(itemSupplier.get());
    }

    public static <X> Container<X> lazy(Supplier<X> itemSupplier) {
        return LazyContainer.of(itemSupplier);
    }

    public static <X> ResettableContainer<X> resettable(Supplier<X> itemSupplier) {
        return ResettableLazyContainer.of(itemSupplier);
    }

    public static <X> ResettableContainer<X> createResettableContainer(Supplier<X> itemSupplier,
                                                                       Consumer<Runnable> resetConsumer) {
        ResettableLazyContainer<X> container=ResettableLazyContainer.of(itemSupplier);
        if (resetConsumer!=null) {
            resetConsumer.accept(container::reset);
        }
        return container;
    }

    public static <X> Container<X> createContainer(Supplier<X> itemSupplier,
                                                   Consumer<Runnable> resetConsumer) {
        if (resetConsumer == null) {
            return LazyContainer.of(itemSupplier);
        } else {
            ResettableLazyContainer<X> container=ResettableLazyContainer.of(itemSupplier);
            resetConsumer.accept(container::reset);
            return container;
        }
    }

    @lombok.Builder(builderClassName="builder")
    private static <X> Container<X> createContainerByBuilder(Supplier<X> itemSupplier,
                                                             Consumer<Runnable> resetConsumer) {
        return createContainer(itemSupplier,resetConsumer);
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder<X> {
        private Supplier<X> itemSupplier=null;
        private Consumer<Runnable> resetConsumer=null;

        public Builder<X> itemSupplier(Supplier<X> itemSupplier) {
            this.itemSupplier=itemSupplier;
            return this;
        }

        public Builder<X> item(X item) {
            return itemSupplier(()->item);
        }

        public Builder<X> resetConsumer(Consumer<Runnable> resetConsumer) {
            this.resetConsumer=resetConsumer;
            return this;
        }
    }
}
