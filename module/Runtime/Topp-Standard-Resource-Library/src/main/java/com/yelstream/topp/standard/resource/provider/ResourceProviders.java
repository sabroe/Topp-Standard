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

package com.yelstream.topp.standard.resource.provider;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility addressing instances of {@link ResourceProvider}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class ResourceProviders {
    /**
     *
     */
    public static Stream<ResourceProvider> getResourceProvidersByServiceLoader() {
        ServiceLoader<ResourceProvider> loader = ServiceLoader.load(ResourceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false);
    }

    /*** BEGIN: Encapsulate "late" setting of providers into a separate tech-class ***/
    private static final List<Supplier<List<ResourceProvider>>> resourceProviderSuppliers =
            new CopyOnWriteArrayList<>(Collections.singletonList(
                    () -> getResourceProvidersByServiceLoader().toList()
            ));

    public static void updateResourceProviderSuppliers(Consumer<List<Supplier<List<ResourceProvider>>>> updateOperation) {
        updateOperation.accept(resourceProviderSuppliers);
    }

    private static List<ResourceProvider> initResourceProviders() {
        return resourceProviderSuppliers.stream().flatMap(providerSupplier -> providerSupplier.get().stream()).toList();
    }

    /**
     * Default resource providers.
     * <p>
     * This is immutable.
     * </p>
     */
    @Getter(lazy = true)
    private static final List<ResourceProvider> defaultResourceProviders = Collections.unmodifiableList(initResourceProviders());

    /*** END. ***/

    public ResourceProvider createResourceProvider(ClassLoader classLoader) {
        return null;  //TO-DO: Fix!
    }

    public ResourceProvider createResourceProviderForBoundClass(Class<?> clazz) {
        return createResourceProvider(clazz.getClassLoader());
    }
}
