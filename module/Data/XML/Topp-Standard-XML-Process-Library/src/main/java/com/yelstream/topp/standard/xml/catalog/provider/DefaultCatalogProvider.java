package com.yelstream.topp.standard.xml.catalog.provider;

import com.yelstream.topp.standard.load.resource.Resource;
import com.yelstream.topp.standard.xml.catalog.provider.util.JarXsdResourceScanner3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Default implementation of CatalogProvider using JAR XSD resource scanning.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-22
 */
public class DefaultCatalogProvider implements CatalogProvider {
    private final List<Resource> catalogResources;

    /**
     * Constructs a provider with the specified XSD base path and namespace base URI.
     * @param xsdBasePath Base path for XSD files (e.g., "/XSD/HAL/CIM/4.9.0").
     * @param namespaceBaseUri Base URI for namespace mappings (e.g., "http://example.com/xsd/").
     * @param callerClass Class used to determine the JAR to scan.
     * @param classLoader ClassLoader to access the JAR's resources.
     * @throws IOException If scanning fails.
     */
    public DefaultCatalogProvider(String xsdBasePath,
                                  String namespaceBaseUri,
                                  Class<?> callerClass,
                                  ClassLoader classLoader) throws IOException, URISyntaxException {
        this.catalogResources =
            JarXsdResourceScanner3.scanForXsdResources(xsdBasePath, namespaceBaseUri, callerClass, classLoader);
    }

    @Override
    public List<Resource> getCatalogResources() {
        return catalogResources;
    }
}
