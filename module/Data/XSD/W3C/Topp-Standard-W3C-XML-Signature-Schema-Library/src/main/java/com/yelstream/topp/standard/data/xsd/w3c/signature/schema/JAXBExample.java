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

package com.yelstream.topp.standard.data.xsd.w3c.signature.schema;

import com.yelstream.topp.standard.xml.catalog.provider.CatalogProvider;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URI;
import java.net.URL;
import java.util.ServiceLoader;

public class JAXBExample {
    public static void main(String[] args) throws Exception {
        // Load catalog
        ServiceLoader<CatalogProvider> catalogLoader = ServiceLoader.load(CatalogProvider.class);
        URL catalogURL = catalogLoader.iterator().next().getCatalogURL();

        // Create catalog and resolver
        CatalogFeatures features = CatalogFeatures.defaults();
        Catalog catalog = CatalogManager.catalog(features, catalogURL.toURI());
        CatalogResolver catalogResolver = CatalogManager.catalogResolver(features, catalogURL.toURI());

/*
For more control, here’s how you might customize the catalog features:

        CatalogFeatures features = CatalogFeatures.builder()
                .with(CatalogFeatures.Feature.FILES, catalogURL.toString())
                .with(CatalogFeatures.Feature.PREFER, "public")
                .with(CatalogFeatures.Feature.RESOLVE, "continue")
                .build();
        Catalog catalog = CatalogManager.catalog(features, catalogURL.toURI());
        CatalogResolver resolver = CatalogManager.catalogResolver(features, catalogURL.toURI());

This ensures the catalog resolver continues processing even if no matching entry is found, which can be useful for debugging or handling partial catalog matches.
*/

        // Create LSResourceResolver to bridge CatalogResolver
        LSResourceResolver resourceResolver = new LSResourceResolver() {
            @Override
            public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
                try {
                    // Use CatalogResolver to resolve systemId
                    String resolvedURI = String.valueOf(catalogResolver.resolve(systemId, baseURI));
                    if (resolvedURI != null) {
                        LSInput input = new CustomLSInput();
                        input.setSystemId(resolvedURI);
                        input.setBaseURI(baseURI);
                        input.setPublicId(publicId);
                        // Set the byte stream or character stream if needed
                        input.setByteStream(URI.create(resolvedURI).toURL().openStream());
                        return input;
                    }
                } catch (Exception e) {
                    // Log or handle exception as needed
                    System.err.println("Failed to resolve: " + systemId + ", error: " + e.getMessage());
                }
                return null; // Return null if resolution fails
            }
        };

// Create schema
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        schemaFactory.setResourceResolver(resourceResolver);
        Schema schema = schemaFactory.newSchema(); // Will use catalog to resolve XSDs

        // JAXB context
/*
        JAXBContext context = JAXBContext.newInstance(YourClass.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema(schema);
*/
        // Use marshaller as needed
    }
}
