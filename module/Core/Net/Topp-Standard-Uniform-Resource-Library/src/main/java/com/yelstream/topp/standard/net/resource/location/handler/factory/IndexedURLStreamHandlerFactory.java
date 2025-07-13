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

package com.yelstream.topp.standard.net.resource.location.handler.factory;

import lombok.AllArgsConstructor;
import lombok.Singular;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Factories indexed by protocol name.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public final class IndexedURLStreamHandlerFactory implements URLStreamHandlerFactory {
    /**
     * Factories indexed by protocol name.
     */
    @Singular
    private final Map<String,List<URLStreamHandlerFactory>> protocolToFactories;

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return Optional.ofNullable(protocolToFactories.get(protocol)).orElse(List.of()).stream().map(factory->factory.createURLStreamHandler(protocol)).filter(Objects::nonNull).findFirst().orElse(null);
    }
}
