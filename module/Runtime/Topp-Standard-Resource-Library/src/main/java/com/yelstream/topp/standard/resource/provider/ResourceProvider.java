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
import com.yelstream.topp.standard.resource.index.ResourceIndex;
import com.yelstream.topp.standard.resource.name.Locations;
import com.yelstream.topp.standard.resource.util.ListInlet;

import java.util.List;
import java.util.stream.Stream;

/**
 * Provides resources.
 * <p>
 *     For indexing, traverse the stream of resources.
 * </p>
 * <p>
 *     For more elaborate, filtered lookup of resources, use resource resolvers.
 * /p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface ResourceProvider {
    /**
     *
     */
    String getName();

    /**
     *
     */
    String getScheme();  //TO-DO: Abandon this; a resource-provider is logical access! Resource/Item has scheme!?

    /**
     *
     */
    ResourceIndex getIndex();

/*** BEGIN: ResourceLookup ? ***/
    /**
     *
     */
    Resource getResource(String name);

    /**
     *
     */
    ListInlet<Resource> resources(String name);

    /**
     *
     */
    default ListInlet<Resource> resources() {
        return resources(Locations.ROOT_CONTAINER_NAME);
    }
/*** END: ResourceLookup ? ***/
}
