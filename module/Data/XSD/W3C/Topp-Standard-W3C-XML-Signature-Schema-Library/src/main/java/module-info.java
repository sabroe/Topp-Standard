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

import com.yelstream.topp.standard.xml.catalog.provider.CatalogProvider;

/**
 * Topp Standard W3C XML Signature Library addresses usages of W3C XML Signature Schema.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-16
 */
module com.yelstream.topp.standard.data.xsd.w3c.signature.schema {
    uses CatalogProvider;
    uses com.yelstream.topp.standard.xml.schema.provider.SchemaProvider;
    requires jakarta.xml.bind;
    requires com.yelstream.topp.standard.xml.process;
    requires com.yelstream.topp.standard.resource;
    exports com.yelstream.topp.standard.data.xsd.w3c.signature.schema;
}
