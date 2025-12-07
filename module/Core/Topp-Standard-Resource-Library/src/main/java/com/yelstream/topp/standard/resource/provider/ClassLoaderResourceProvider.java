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

import com.yelstream.topp.standard.resource.Resource;
import com.yelstream.topp.standard.resource.index.ResourceIndex;
import com.yelstream.topp.standard.collection.stream.let.out.ListOutlet;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Classloader based resource provider.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-02
 */
@AllArgsConstructor(staticName="of")
public final class ClassLoaderResourceProvider implements ResourceProvider {
    /**
     *
     */
    private final ClassLoader classLoader;

    /**
     *
     */
    @Getter
    private final String name;

    /**
     *
     */
    @Getter
    private final String scheme;

    /**
     *
     */
    @Override
    public ResourceIndex getIndex() {
        return null;  //TO-DO: Fix!
    }

    /**
     *
     */
    @Override
    public ListOutlet<Resource> resources() {
        return null;  //TO-DO: Fix!
    }

    /**
     *
     */
    @Override
    public ListOutlet<Resource> resources(String name) {
        return null;  //TO-DO: Fix!
    }

    /**
     *
     */
    @Override
    public Resource getResource(String name) {
        return null;  //TO-DO: Fix!
    }
}
