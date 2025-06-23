package com.yelstream.topp.standard.xml.catalog;

import javax.xml.XMLConstants;
import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileWriter;

public class TempCatalogExample {
    public static void main(String[] args) throws Exception {
        // Programmatically create a catalog file
        File tempCatalog = File.createTempFile("catalog", ".xml");
        try (FileWriter writer = new FileWriter(tempCatalog)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<catalog xmlns=\"urn:oasis:names:tc:entity:xmlns:xml:catalog\">\n");
            writer.write("    <uri name=\"http://example.com/schema/example.xsd\" uri=\"file:///path/to/local/example.xsd\"/>\n");
            writer.write("    <uri name=\"http://example.com/schema/another.xsd\" uri=\"file:///path/to/local/another.xsd\"/>\n");
            writer.write("</catalog>");
        }

        // Load the catalog
        Catalog catalog = CatalogManager.catalog(
                CatalogFeatures.defaults(),
                tempCatalog.toURI()
        );

// Create a CatalogResolver from the Catalog
        CatalogResolver catalogResolver = CatalogManager.catalogResolver(catalog);

        // Create SchemaFactory and set the CatalogResolver as ResourceResolver
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "all");
        schemaFactory.setResourceResolver(catalogResolver);

        // Load schema (URIs will be resolved via the catalog)
        Schema schema = schemaFactory.newSchema(new StreamSource(new File("path/to/your/xml-file.xml")));

        // Clean up temporary file
        tempCatalog.delete();
    }
}
