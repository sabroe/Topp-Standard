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

package com.yelstream.topp.standard.net.resource.location.handler;

import lombok.experimental.UtilityClass;
import java.net.URLStreamHandler;

/**
 * Utilities addressing instances of {@link URLStreamHandler}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class URLStreamHandlers {
    /**
     * Name of system property used for configuration of packages with protocols.
     * <p>
     *     Given a value like "my.package.name",
     *     place the "XXXURLStreamHandler" in the package "my.package.name.protocols.xxx",
     *     and the JVM will automatically discover it for URLs starting with "xxx:".
     * </p>
     * <p>
     *     Configuration like this is a legacy feature; use {@link java.net.spi.URLStreamHandlerProvider} services.
     * </p>
     */
    public static final String PROTOCOL_HANDLER_PACKAGES_SYSTEM_PROPERTY_NAME="java.protocol.handler.pkgs";
}
