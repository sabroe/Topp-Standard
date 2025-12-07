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

package com.yelstream.topp.standard.resource.provider;

import com.yelstream.topp.standard.system.load.instance.InstanceLoader;
import com.yelstream.topp.standard.system.load.instance.InstanceLoaders;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.List;

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
    private final InstanceLoader<ResourceProvider> loader=InstanceLoaders.forClass(ResourceProvider.class);


    public ResourceProvider createResourceProvider(ClassLoader classLoader) {
        return null;  //TO-DO: Fix!
    }

    public ResourceProvider createResourceProviderForBoundClass(Class<?> clazz) {
        return createResourceProvider(clazz.getClassLoader());
    }

    public List<ResourceProvider> getResourceProviders() {
        return loader.getInstances();
    }

    private ResourceProvider createResourceProvider() {
        return null;  //TO-DO: FiX!
    }

    /**
     * Default instances.
     * <p>
     * This is immutable.
     * </p>
     */
    @Getter(lazy = true)
    private final ResourceProvider resourceProvider=createResourceProvider();
}
