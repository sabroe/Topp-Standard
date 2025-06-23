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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Provider of a catalog over XML Schema files.
 * <p>
 *     Your updated CatalogProvider interface shifts the primary responsibility to getCatalogResources(),
 *     making it the abstract method that must return at least one CatalogResource.
 *     The getCatalog() and getCatalogResolver() methods are now derived from the resources’
 *     getURL().toURI() to ensure compatibility with CatalogManager.
 *     This design supports both in-memory (e.g., memory: scheme) and file-based catalogs
 *     while avoiding JVM-wide registration for private schemes.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-16
 */
@FunctionalInterface
public interface CatalogProvider {
    /**
     * Gets the catalog resources backing this provider.
     * Must return at least one resource, even for programmatically generated catalogs.
     * @return List of catalog resources, never empty.
     */
    List<CatalogResource> getCatalogResources();

    /**
     * Catalog resource, providing access to catalog content.
     * <p>
     *     This supports both in-memory (e.g., a "memory:" scheme) and file-based catalogs
     *     while avoiding imposing any JVM-wide registrations for private schemes.
     *     <br/>
     *     However, a global, JVM-wide registration may be used.
     * </p>
     * <p>
     *     Note: For content access, use {@link #getURL()} .
     * </p>
     */
    @FunctionalInterface
    interface CatalogResource {
        /**
         * Gets the identification of this resource.
         * This may use non-JVM-wide, private schemes (e.g., "memory:").
         * <p>
         *     Note: This may not resolve to a URL with an actively supported scheme.
         * </p>
         * @return The URI of the catalog resource.
         */
        URI getURI();

        /**
         * Gets a reference to actual resource content
         * <p>
         *     Note: This may involve copying content to a temporary file for accessibility.
         * </p>
         * @return A URL resolving to the catalog content.
         *         May be distinct from {@code getURI().toURL()}.
         */
        default URL getURL() {
            URI uri=getURI();
            try {
                return uri.toURL();
            } catch (MalformedURLException ex) {
                throw new IllegalStateException("Failure to create catalog resource URL; cannot convert URI to URL!",ex);
            }
        }
    }

    /**
     * Gets the catalog instance.
     * @return Catalog instance.
     */
    default Catalog getCatalog() {
        List<URI> resolvedContentURIs=
            getCatalogResources().stream().map(CatalogResource::getURL).map(url-> {
                try {
                    return url.toURI();
                } catch (URISyntaxException ex) {
                    throw new IllegalStateException("Failure to create catalog; cannot convert URL to URI!",ex);
                }
            }).toList();
        return CatalogManager.catalog(getCatalogFeatures(),resolvedContentURIs.toArray(new URI[0]));
    }

    /**
     * Gets the catalog features.
     * @return Catalog features.
     */
    default CatalogFeatures getCatalogFeatures() {
        return CatalogFeatures.defaults();
    }

    /**
     * Gets the catalog resolver.
     * @return Catalog resolver.
     */
    default CatalogResolver getCatalogResolver() {
        return CatalogManager.catalogResolver(getCatalog());
    }
}
