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

package com.yelstream.topp.standard.net.resource.identification.type;

import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import lombok.experimental.UtilityClass;

import java.net.URI;

/**
 * Utilities for JDBC-specific URIs.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-29
 */
@UtilityClass
public class JdbcURIs {
    /**
     * Gets the inner JDBC URL referred by a URI.
     * @param uri URI.
     * @return Inner URI.
     */
    public static String toInnerURI(URI uri) {
        StandardScheme.JDBC.getScheme().requireMatch(uri);
        String schemeSpecificPart=uri.getSchemeSpecificPart();
        int index=schemeSpecificPart.indexOf(";");
        if (index==-1) {
            return schemeSpecificPart;
        }
        return schemeSpecificPart.substring(0,index);
    }
}
