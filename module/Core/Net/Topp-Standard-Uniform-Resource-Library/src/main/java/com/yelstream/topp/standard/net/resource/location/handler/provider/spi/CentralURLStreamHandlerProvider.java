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

package com.yelstream.topp.standard.net.resource.location.handler.provider.spi;

import com.yelstream.topp.standard.net.resource.location.handler.factory.URLStreamHandlerFactories;
import lombok.extern.slf4j.Slf4j;

import java.net.URLStreamHandler;
import java.net.spi.URLStreamHandlerProvider;

/**
 * Provider of URL stream handlers performing protocol lookup in the central set of URL stream handler factories.
 * <p>
 *     This uses {@link URLStreamHandlerFactories#createURLStreamHandler(String)},
 *     which is mostly fixed, but may be dynamic over time.
 * </p>
 * <p>
 * <p>
 *     Note that this is to be registered as a service under
 *     {@code META-INF/services/java.net.spi.URLStreamHandlerProvider},
 *     listed in the JPMS module definition in which this is contained,
 *     and hence will be used actively by instances of {@link java.net.URL}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@Slf4j
public final class CentralURLStreamHandlerProvider extends URLStreamHandlerProvider {
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return URLStreamHandlerFactories.createURLStreamHandler(protocol);
    }
}
