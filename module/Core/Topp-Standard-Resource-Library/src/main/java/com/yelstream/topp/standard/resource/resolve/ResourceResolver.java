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

package com.yelstream.topp.standard.resource.resolve;

import com.yelstream.topp.standard.resource.Resource;
import com.yelstream.topp.standard.system.load.name.Location;

/**
 * Provides access to a related group of resources.
 * <p>
 *     This is a read interface, possibly it may support a full set of CRUD operations.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface ResourceResolver {


    //org.springframework.core.io.support.PathMatchingResourcePatternResolver


/*
    Stream<Resource> createResourceStream();

    default Stream<Resource> createResourceStream(Predicate<Resource> filter) {
        if (filter==null) {
            return createResourceStream();
        } else {
            return createResourceStream().filter(filter);
        }
    }

    default List<Resource> getResources() {
        return createResourceStream().toList();
    }

    default List<Resource> getResources(Predicate<Resource> filter) {
        return createResourceStream(filter).toList();
    }
*/


    /**
     *
     */
    default Resource getResource(String name) {
        return null;  //TO-DO: Fix!
    }

    /**
     *
     */
    default Resource getResource(Location location) {
        return null;  //TO-DO: Fix!
    }
}
