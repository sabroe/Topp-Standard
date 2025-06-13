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

package com.yelstream.topp.standard.data.jakarta.jaxb;

import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * WIP!
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-13
 */
public class XsdToJavaGenerator2 {
    public static void main(String[] args) throws Exception {
        File[] xsdFiles = new File[] {
                new File("src/main/resources/xsd/schema1.xsd"),
                new File("src/main/resources/xsd/schema2.xsd")
        };
        File bindingsFile = generateBindingsFile(xsdFiles);
        File outputDir = new File("target/generated-sources/xjc");

        // Create SchemaCompiler
        SchemaCompiler sc = XJC.createSchemaCompiler();

        // Add binding file
        sc.parseSchema(new InputSource(bindingsFile.toURI().toString()));

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

    private static File generateBindingsFile(File[] xsdFiles) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = doc.createElementNS("https://jakarta.ee/xml/ns/jaxb", "bindings");
        root.setAttribute("configVersion", "3.0");
        doc.appendChild(root);

        for (File xsdFile : xsdFiles) {
            String namespace = extractNamespace(xsdFile);
            if (namespace != null) {
                Element bindings = doc.createElement("bindings");
                Element schemaBindings = doc.createElement("schemaBindings");
                schemaBindings.setAttribute("targetNamespace", namespace);
                Element packageElement = doc.createElement("package");
                packageElement.setAttribute("name", namespaceToPackage(namespace));
                schemaBindings.appendChild(packageElement);
                bindings.appendChild(schemaBindings);
                root.appendChild(bindings);
            }
        }

        File bindingsFile = new File("target/generated-bindings.xjb");
        TransformerFactory.newInstance().newTransformer()
                .transform(new DOMSource(doc), new StreamResult(bindingsFile));
        return bindingsFile;
    }

    private static String extractNamespace(File xsdFile) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(xsdFile);
        return doc.getDocumentElement().getAttribute("targetNamespace");
    }

    private static String namespaceToPackage(String namespace) {
        String[] parts = namespace.split("/");
        return "com.example." + parts[parts.length - 1].toLowerCase();
    }
}
