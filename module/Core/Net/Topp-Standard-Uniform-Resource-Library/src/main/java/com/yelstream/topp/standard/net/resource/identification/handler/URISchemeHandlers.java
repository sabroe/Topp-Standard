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

package com.yelstream.topp.standard.net.resource.identification.handler;

import com.yelstream.topp.standard.net.resource.identification.type.JarURIs;
import com.yelstream.topp.standard.net.resource.identification.type.JdbcURIs;
import com.yelstream.topp.standard.net.resource.identification.build.URIArguments;
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link URISchemeHandler}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
@UtilityClass
public class URISchemeHandlers {

    public static URISchemeHandler createURISchemeHandler() {
        return SimpleURISchemeHandler.builder()
            .trait(Traits.REGULAR_TRAIT)
            .build();
    }

    public static URISchemeHandler createURISchemeHandlerForJar() {
        return SimpleURISchemeHandler.builder()
            .trait(Traits.JAR_LIKE_TRAIT)
            .entryReader(argument->JarURIs.SplitURI.of(argument.getSource()).getEntry())
            .innerURIReader(argument->JarURIs.SplitURI.of(argument.getSource()).getUrl())
            .build();
    }

    public static URISchemeHandler createURISchemeHandlerForFile() {
        return SimpleURISchemeHandler.builder()
            .trait(Traits.REGULAR_TRAIT)  //Yes, file URIs are regular!
            .argumentCorrection(URIArguments::correctArgumentForFile)
            .build();
    }

    public static URISchemeHandler createURISchemeHandlerForJDBC() {
        return SimpleURISchemeHandler.builder()
            .trait(Traits.JDBC_LIKE_TRAIT)
            .propertiesReader(URIArguments::getProperties)
            .innerURIReader(argument->JdbcURIs.toInnerURI(argument.getSource()))
            .build();
    }

    public static URISchemeHandler createURISchemeHandlerForDocker() {
        return SimpleURISchemeHandler.builder()
            .trait(Traits.DOCKER_LIKE_TRAIT)
            .tagReader(URIArguments::getTag)
            .build();
    }
}
