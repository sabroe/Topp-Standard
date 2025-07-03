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

package com.yelstream.topp.standard.resource.content;

import com.yelstream.topp.standard.resource.io.source.Source;
import com.yelstream.topp.standard.resource.io.source.Sources;
import com.yelstream.topp.standard.resource.io.target.Target;
import com.yelstream.topp.standard.resource.clazz.load.ResourceLoader;
import com.yelstream.topp.standard.resource.name.Location;
import com.yelstream.topp.standard.resource.name.Locations;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Item attached to a resource-loader.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@AllArgsConstructor(staticName="of")
final class ResourceLoaderItem implements Item {
    /**
     * Resource-loader.
     */
    private final ResourceLoader resourceLoader;

    /**
     * Location.
     */
    @Getter
    private final Location location;

    @Override
    public URI getURI() {
        try {
            URL url=resourceLoader.getResource(location.getName());
            return url==null?null:url.toURI();
        } catch (URISyntaxException ex) {
            throw new IllegalStateException("Failure to get URI for named classloader resource!",ex);
        }
    }

    @Override
    public URL getURL() {
        return resourceLoader.getResource(location.getName());
    }

    @Override
    public Capability capability() {
        if (Locations.isNameForContainer(location.getName())) {
            return Capabilities.DEFAULT_EXISTING_STATIC_CONTENT_CAPABILITY;
        } else {
            return Capabilities.DEFAULT_EXISTING_STATIC_CONTAINER_CAPABILITY;
        }
    }

    @Override
    public Source readable() {
        return Sources.createSource(()->resourceLoader.getResourceAsStream(location.getName()),
                                    ()->resourceLoader.getResourceAsChannel(location.getName()));
    }

    @Override
    public Target writable() {
//        return OutputTargets.createTargetSource(classLoader,name);
        return null;
    }
}
