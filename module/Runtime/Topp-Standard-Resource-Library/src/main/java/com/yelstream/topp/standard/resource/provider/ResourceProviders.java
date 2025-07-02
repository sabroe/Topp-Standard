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

import com.yelstream.topp.standard.resource.Resource;
import com.yelstream.topp.standard.resource.name.Location;
import com.yelstream.topp.standard.resource.resolve.ResourceResolver;
import com.yelstream.topp.standard.resource.resolve.ResourceResolvers;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.ServiceLoader;
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
    public static Stream<ResourceProvider> getResourceProviderStream() {
        ServiceLoader<ResourceProvider> loader=ServiceLoader.load(ResourceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false);
    }

    /**
     *
     */
    public static List<ResourceProvider> getResourceProviders() {
        return getResourceProviderStream().toList();
    }

    /**
     *
     */
    public static List<ResourceResolver> getResourceResolvers(List<ResourceProvider> resourceProviders) {
        return resourceProviders.stream().flatMap(provider -> provider.getResourceResolvers().stream()).toList();
    }

    /**
     *
     */
    public static List<ResourceResolver> getResourceResolvers() {
        return getResourceResolvers(getResourceProviders());
    }

    /**
     *
     */
    public static ResourceResolver createResourceResolver() {
        List<ResourceResolver> resourceResolvers=getResourceResolvers();
        return ResourceResolvers.createResourceResolver(resourceResolvers);
    }

    public static ResourceProvider getResourceProvider() {  //TO-DO: Single instance, on-demand creation, keep for all time!
        return null;  //TO-DO: fix!
    }

    /**
     *
     */
    public static Resource getResource(String name) {
        return getResourceProvider().getResource(name);
    }

    /**
     *
     */
    public static Resource getResource(Location location) {
        return getResourceProvider().getResource(location);
    }
}
