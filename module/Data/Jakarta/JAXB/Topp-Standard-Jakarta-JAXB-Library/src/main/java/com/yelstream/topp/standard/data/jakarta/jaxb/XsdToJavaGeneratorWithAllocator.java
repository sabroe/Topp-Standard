package com.yelstream.topp.standard.data.jakarta.jaxb;

import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import com.sun.tools.xjc.api.ClassNameAllocator;
import org.xml.sax.InputSource;
import java.io.File;

/**
 * WIP!
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-13
 */
public class XsdToJavaGeneratorWithAllocator {
    public static void main(String[] args) throws Exception {
        File[] xsdFiles = new File[] {
                new File("src/main/resources/xsd/schema1.xsd"),
                new File("src/main/resources/xsd/schema2.xsd")
        };
        File outputDir = new File("target/generated-sources/xjc");

        // Create SchemaCompiler
        SchemaCompiler sc = XJC.createSchemaCompiler();

        // Set custom ClassNameAllocator for namespace-to-package mapping
        sc.setClassNameAllocator(new ClassNameAllocator() {
            @Override
            public String assignClassName(String namespaceUri, String suggestedClassName) {
                if (namespaceUri == null || namespaceUri.isEmpty()) {
                    return "com.example.defaultpkg." + suggestedClassName;
                }
                // Example: http://example.com/nsX -> com.example.nsx
                String[] parts = namespaceUri.split("/");
                return "com.example." + parts[parts.length - 1].toLowerCase() + "." + suggestedClassName;
            }
        });

        // Add schema files
        for (File xsdFile : xsdFiles) {
            sc.parseSchema(new InputSource(xsdFile.toURI().toString()));
        }

        // Generate code
        S2JJAXBModel model = sc.bind();
        if (model == null) {
            throw new RuntimeException("XJC compilation failed");
        }
        model.generateCode(null, null).build(outputDir, System.out);
        System.out.println("Java classes generated successfully");
    }
}
