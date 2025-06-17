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

package com.yelstream.topp.standard.xml.catalog.provider;

import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;
import java.net.URI;
import java.net.URL;

/**
 * Provider of a catalog over XML Schema files.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-16
 */
@FunctionalInterface
public interface CatalogProvider {
    /**
     * Gets reference to a catalog over XML Schema files.
     * @return Reference to a catalog over XML Schema files.
     */
    URL getCatalogURL();

    /**
     * Gets the URI of the catalog.
     * @return URI of the catalog.
     */
    default URI getCatalogURI() {
        try {
            return getCatalogURL().toURI();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to convert catalog URL to URI!", ex);
        }
    }

    /**
     * Gets the catalog features for this provider.
     * @return Catalog features.
     */
    default CatalogFeatures getCatalogFeatures() {
        return CatalogFeatures.defaults();
    }

    /**
     * Gets the catalog instance for this provider.
     * @return Catalog instance.
     */
    default Catalog getCatalog() {
        return CatalogManager.catalog(getCatalogFeatures(),getCatalogURI());
    }

    /**
     * Gets the catalog resolver for this provider.
     * @return Catalog resolver.
     */
    default CatalogResolver getCatalogResolver() {
        return CatalogManager.catalogResolver(getCatalogFeatures(), getCatalogURI());
    }
}
