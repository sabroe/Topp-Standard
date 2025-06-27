package com.yelstream.topp.standard.data.xsd.w3c.signature.schema;

import com.yelstream.topp.standard.xml.catalog.provider.CatalogProvider;
import com.yelstream.topp.standard.xml.schema.provider.SchemaProvider;

import java.net.URL;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        // Load catalog
        ServiceLoader<CatalogProvider> catalogLoader = ServiceLoader.load(CatalogProvider.class);
        for (CatalogProvider provider : catalogLoader) {
            URL catalogURL = null;//provider.getCatalogURL();
            System.out.println("Catalog URL: " + catalogURL);
        }

        // Load schemas
        ServiceLoader<SchemaProvider> schemaLoader = ServiceLoader.load(SchemaProvider.class);
        for (SchemaProvider provider : schemaLoader) {
            for (URL schemaURL : provider.getSchemaURLs()) {
                System.out.println("Schema URL: " + schemaURL);
            }
        }
    }
}