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

package com.yelstream.topp.standard.net.resource.location.connection;

import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link java.net.URLConnection}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-13
 */
@UtilityClass
public class URLConnections {

    public static URLConnection createURLConnection(URL url,
                                                    Supplier<InputStream> inputStreamSupplier,
                                                    Supplier<OutputStream> outputStreamSupplier) {
        return new SimpleURLConnection(url,inputStreamSupplier,outputStreamSupplier);
    }


    @lombok.Builder(builderClassName="Builder")
    private static URLConnection createURLConnectionByBuilder(URL url,
                                                              Supplier<InputStream> inputStreamSupplier,
                                                              Supplier<OutputStream> outputStreamSupplier) {
        return new SimpleURLConnection(url,inputStreamSupplier,outputStreamSupplier);
    }
}
