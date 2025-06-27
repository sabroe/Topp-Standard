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

package com.yelstream.topp.standard.load.resource.provider;

import com.yelstream.topp.standard.load.resource.Resource;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Provides access to resources.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
public interface ResourceProvider {
    //TO-DO: Let this a provider of 'Resource' instances, possibly do traversal!

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
}
