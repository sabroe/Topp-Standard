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

import com.yelstream.topp.standard.xml.catalog.Catalogs;
import lombok.experimental.UtilityClass;

import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility addressing instances of {@link CatalogProvider}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-17
 */
@UtilityClass
public class CatalogProviders {
    /**
     *
     */
    public static Stream<CatalogProvider> getCatalogProviderStream() {
        ServiceLoader<CatalogProvider> loader = ServiceLoader.load(CatalogProvider.class);
        return StreamSupport.stream(loader.spliterator(), false);
    }

    /**
     *
     */
    public static List<CatalogProvider> getCatalogProviders() {
        return getCatalogProviderStream().toList();
    }

    /**
     *
     */
    public static List<Catalog> getCatalogs(List<CatalogProvider> catalogProviders) {
        return catalogProviders.stream().map(CatalogProvider::getCatalog).toList();
    }

    /**
     *
     */
    public static List<Catalog> getCatalogs() {
        return getCatalogs(getCatalogProviders());
    }

    public static List<CatalogProvider.CatalogResource> getCatalogResources() {
        return getCatalogProviderStream().flatMap(provider -> provider.getCatalogResources().stream()).toList();
    }

    public static List<URI> getCatalogURIs() {
        return getCatalogResources().stream().map(resource-> {
            try {
                return resource.getURL().toURI();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);  //TO-DO: Fix!
            }
        }).toList();
    }

    /**
     *
     */
    public static Catalog createCatalog(CatalogFeatures features) {
        List<URI> uris=getCatalogURIs();
        return Catalogs.createCatalog(features,uris);
    }

    /**
     * Combines multiple CatalogProvider instances into a single Catalog using default features.
     * @return Combined Catalog instance.
     */
    public static Catalog createCatalog() {
        return createCatalog(CatalogFeatures.defaults());
    }

    /**
     * Retrieves the content of a catalog resource as a string.
     * @param resource The catalog resource.
     * @return The content of the resource as a string, encoded in UTF-8.
     * @throws IOException If an I/O error occurs while reading the resource.
     */
    public static String getContentAsString(CatalogProvider.CatalogResource resource) {  //TO-DO: Move to new "CatalogResources"!
        try (InputStream inputStream = resource.getURL().openStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException(ex);  //TO-DO: Fix!
        }
    }
}
