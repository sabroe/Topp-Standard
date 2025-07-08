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

package com.yelstream.topp.standard.load.instance;

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
 *     Intended as part of the internal state and implementation of a singleton e.g. a server-locator.
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
     * Manages the list of suppliers providing instances.
     */
    @AllArgsConstructor(access=AccessLevel.PRIVATE)
    public class SupplierRegistry {
        @Getter(AccessLevel.PRIVATE)
        private final List<Supplier<List<I>>> suppliers=new CopyOnWriteArrayList<>();

        public InstanceLoader<I> add(Supplier<List<I>> supplier) {
            if (supplier!=null) {
                suppliers.add(supplier);
            }
            return InstanceLoader.this;
        }

        public InstanceLoader<I>  clear() {
            suppliers.clear();
            return InstanceLoader.this;
        }
    }

    /**
     *
     */
    @Getter
    private final SupplierRegistry registry=new SupplierRegistry();

    private List<I> createInstances() {
        return registry.getSuppliers().stream().flatMap(supplier->supplier.get().stream()).filter(Objects::nonNull).toList();
    }

    private final List<I> innerInstances=new CopyOnWriteArrayList<>();

    @SuppressWarnings("java:S1117")
    private List<I> refreshInnerInstances() {
        List<I> instances=createInstances();
        innerInstances.clear();
        innerInstances.addAll(instances);
        return innerInstances;
    }

    @SuppressWarnings("java:S1117")
    private List<I> createImmutableInstances() {
        return Collections.unmodifiableList(refreshInnerInstances());
    }

    /**
     * Default instances.
     * <p>
     * This is immutable.
     * </p>
     */
    @Getter(lazy=true)
    private final List<I> instances=createImmutableInstances();

    /**
     * Reloads instances from the current suppliers, updating the cache.
     */
    public synchronized void reload() {
        refreshInnerInstances();
    }

    /**
     * Gets the first instance, if available.
     * @return First instance or null if none exist.
     */
    public Optional<I> getFirstInstance() {
        return getInstances().stream().findFirst();
    }
}
