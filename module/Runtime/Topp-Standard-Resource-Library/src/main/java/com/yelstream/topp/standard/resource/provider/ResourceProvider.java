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

import java.util.List;

/**
 * Provides access to resources through {@link ResourceResolver} instances.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface ResourceProvider {
    /**
     * Gets the resource resolvers provided.
     * @return Resource resolvers provided, never empty.
     */
    List<ResourceResolver> getResourceResolvers();  //TO-DO: Create new vs. get the same, single instance??

    /**
     *
     */
    default Resource getResource(String name) {
//        return getResourceResolver().getResource(name);
        return null;  //TO-DO: Fix!
    }

    /**
     *
     */
    default Resource getResource(Location location) {
//        return getResourceResolver().getResource(location);
        return null;  //TO-DO: Fix!
    }

}
