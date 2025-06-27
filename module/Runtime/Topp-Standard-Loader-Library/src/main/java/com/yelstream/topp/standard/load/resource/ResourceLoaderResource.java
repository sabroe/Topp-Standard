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

package com.yelstream.topp.standard.load.resource;

import com.yelstream.topp.standard.load.io.InputSource;
import com.yelstream.topp.standard.load.io.InputSources;
import com.yelstream.topp.standard.load.resource.adapt.ResourceLoader;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@AllArgsConstructor(staticName="of")
public final class ResourceLoaderResource implements Resource {  //TO-DO: Name? "LookupBasedResource"?
    /**
     * Resource loader.
     */
    private final ResourceLoader resourceLoader;

    /**
     * Resource name.
     */
    private final String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public URI getURI() {
        try {
            URL url=resourceLoader.getResource(name);
            return url==null?null:url.toURI();
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failure to get URI for named classloader resource!",ex);
        }
    }

    @Override
    public URL getURL() {
        return resourceLoader.getResource(name);
    }

    @Override
    public InputSource readable() {
        return InputSources.createInputSource(()->resourceLoader.getResourceAsStream(name),
                                              ()->resourceLoader.getResourceAsChannel(name));
    }
}
