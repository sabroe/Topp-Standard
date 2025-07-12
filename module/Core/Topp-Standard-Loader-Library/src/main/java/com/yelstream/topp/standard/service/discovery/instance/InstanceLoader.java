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

package com.yelstream.topp.standard.service.discovery.instance;

import com.yelstream.topp.standard.service.discovery.util.holder.Containers;
import com.yelstream.topp.standard.service.discovery.util.holder.ResettableContainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/**
 * Instance loader.
 * <p>
 *     Intended as part of the internal state and implementation of a singleton e.g. a service-locator.
 * </p>
 * <p>
 *     May be used to implement the internals of e.g.
 *     specific service objects to be handled by {@link java.util.ServiceLoader},
 *     chain a list of {@link java.net.URLStreamHandlerFactory}/{@link java.net.spi.URLStreamHandlerProvider}
 *     into a single instance, a chain-of-responsibility construction.
 * </p>
 * @param <I> Type of instance loaded.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-08
 */
@AllArgsConstructor(staticName="of")
public class InstanceLoader<I> {
    /**
     * Registry for suppliers providing lists of instances.
     */
    @AllArgsConstructor(access=AccessLevel.PRIVATE)
    public static class SupplierRegistry<I> {
        /**
         * Suppliers of items.
         */
        @Getter(AccessLevel.PRIVATE)
        private final List<Supplier<List<I>>> suppliers=new CopyOnWriteArrayList<>();

        /**
         * Adds a supplier of instance lists.
         *@param supplier Supplier of instance lists.
         */
        public void add(Supplier<List<I>> supplier) {
            if (supplier!=null) {
                suppliers.add(supplier);
            }
        }

        /**
         * Clears all registered suppliers.
         */
        public void clear() {
            suppliers.clear();
        }
    }

    /**
     * Creates instances.
     * This runs through all suppliers.
     * @return Created instances.
     */
    private List<I> createInstances() {
        return registry.getSuppliers().stream().flatMap(supplier->supplier.get().stream()).filter(Objects::nonNull).toList();
    }

    /**
     * Registered suppliers.
     */
    @Getter
    private final SupplierRegistry<I> registry=new SupplierRegistry<>();

    /**
     * Contained and held instances.
     */
    private final ResettableContainer<List<I>> instancesContainer=Containers.createResettableContainer(()->Collections.unmodifiableList(createInstances()));

    public List<I> getInstances() {
        return instancesContainer.getItem();
    }

    /**
     * Resets the cached instances, enabling re-computation on next access.
     */
    public void reset() {
        instancesContainer.reset();
    }

    /**
     * Retrieves the first available instance, if any.
     * @return First instance, or empty if none exist.
     */
    public Optional<I> getFirstInstance() {
        return getInstances().stream().findFirst();
    }
}
