package com.yelstream.topp.standard.xml.validation;

import com.yelstream.topp.standard.dom.ls.NamedResourceResolver;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;

/**
 * Utilities addressing basic XML validation against an XML Schema.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@Slf4j
@UtilityClass
public class Validation {

    public static void validate(Path xmlFile,
                                String xsdResourcePath) throws SAXException, IOException {
        URI xsdURL = URI.create("resource:/"+xsdResourcePath);
        String rootResource=xsdURL.toString();

        InputStream xsdInputStream = Validation.class.getClassLoader().getResourceAsStream(xsdResourcePath);

        if (xsdInputStream == null) {
            throw new IOException("XSD file not found in classpath: " + xsdResourcePath);
        }

        SchemaFactory schemaFactory = SchemaFactories.createSchemaFactory();
        schemaFactory.setResourceResolver(NamedResourceResolver.of(rootResource));

        StreamSource xsdStream = new StreamSource(xsdInputStream);
        Schema schema = schemaFactory.newSchema(xsdStream);

        Validator validator = schema.newValidator();

        StreamSource xmlStream = new StreamSource(xmlFile.toFile());

        try {
            validator.validate(xmlStream);
            System.out.println("XML is valid against the XSD schema.");
        } catch (SAXException e) {
            System.out.println("XML is not valid: " + e.getMessage());
        }
    }
}
