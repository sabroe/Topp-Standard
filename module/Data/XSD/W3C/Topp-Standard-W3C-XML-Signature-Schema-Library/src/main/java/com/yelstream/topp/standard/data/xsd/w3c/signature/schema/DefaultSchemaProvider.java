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

package com.yelstream.topp.standard.data.xsd.w3c.signature.schema;

import com.yelstream.topp.standard.xml.schema.provider.SchemaProvider;

import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-16
 */
public class DefaultSchemaProvider implements SchemaProvider {
    @Override
    public List<URL> getSchemaURLs() {
        return List.of(
            Objects.requireNonNull(getClass().getClassLoader().getResource("XSD/W3C/XML Signature/xmldsig-core-schema.xsd"))
        );
    }
}
