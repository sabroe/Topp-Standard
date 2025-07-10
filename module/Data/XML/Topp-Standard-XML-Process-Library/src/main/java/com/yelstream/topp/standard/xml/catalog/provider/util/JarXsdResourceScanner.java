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

package com.yelstream.topp.standard.xml.catalog.provider.util;

import com.yelstream.topp.standard.resource.item.Item;
import com.yelstream.topp.standard.resource.Resource;
import com.yelstream.topp.standard.resource.name.Location;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility to scan a JAR for XSD files and create catalog resources.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-22
 */
public final class JarXsdResourceScanner {
    private JarXsdResourceScanner() {}

    /**
     * Scans the JAR containing the specified class for XSD files under the given base path and creates a catalog resource.
     * @param xsdBasePath Base path for XSD files (e.g., "/XSD/HAL/CIM/4.9.0").
     * @param namespaceBaseUri Base URI for namespace mappings (e.g., "http://example.com/xsd/").
     * @param callerClass Class used to determine the JAR to scan.
     * @param classLoader ClassLoader to access the JAR's resources.
     * @return List containing a single CatalogResource for the generated catalog.
     * @throws IOException If scanning or content generation fails.
     */
    public static List<Resource> scanForXsdResources(String xsdBasePath, String namespaceBaseUri, Class<?> callerClass, ClassLoader classLoader) throws IOException {
        String catalogContent = generateCatalogContent(xsdBasePath, namespaceBaseUri, callerClass, classLoader);
        URI catalogUri = URI.create("memory:jar-xsd-catalog-" + System.identityHashCode(new Object()));
        MemoryUriRegistry.register(catalogUri, catalogContent);
        Resource resource = new MemoryCatalogResource(catalogUri, catalogContent);
        return List.of(resource);
    }

    private static String normalizePath(String path) {
        String normalized = path.startsWith("/") ? path : "/" + path;
        return normalized.endsWith("/") ? normalized.substring(0, normalized.length() - 1) : normalized;
    }

    private static String normalizeNamespaceBaseUri(String uri) {
        return uri.endsWith("/") ? uri : uri + "/";
    }

    private static String generateCatalogContent(String xsdBasePath,
                                                 String namespaceBaseUri,
                                                 Class<?> callerClass,
                                                 ClassLoader classLoader) throws IOException {
        List<XsdEntry> xsdEntries = scanJarForXsds3(xsdBasePath, namespaceBaseUri, callerClass, classLoader);
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<catalog xmlns=\"urn:oasis:names:tc:entity:xmlns:xml:catalog\">\n");
        for (XsdEntry entry : xsdEntries) {
            sb.append(String.format("    <uri name=\"%s\" uri=\"%s\"/>\n",
                    escapeXml(entry.namespaceUri()), escapeXml(entry.resourceUri())));
        }
        sb.append("</catalog>");
        return sb.toString();
    }

    private static List<XsdEntry> scanJarForXsds(String xsdBasePath,
                                                 String namespaceBaseUri,
                                                 Class<?> callerClass,
                                                 ClassLoader classLoader) throws IOException {
        CodeSource codeSource = callerClass.getProtectionDomain().getCodeSource();
        if (codeSource == null || codeSource.getLocation() == null) {
            throw new IOException("Cannot determine JAR location for caller class: " + callerClass.getName());
        }
        URL jarUrl = codeSource.getLocation();
System.out.println("JAR URL: " + jarUrl);

        try (FileSystem jarFs = FileSystems.newFileSystem(URI.create("jar:" + jarUrl.toString()), Collections.emptyMap())) {
            Path xsdRoot = jarFs.getPath(normalizePath(xsdBasePath));
            if (!Files.exists(xsdRoot)) {
                return Collections.emptyList();
            }

            List<XsdEntry> entries = new ArrayList<>();
            try (Stream<Path> paths = Files.walk(xsdRoot)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".xsd"))
                        .forEach(p -> {
                            String relativePath = xsdRoot.relativize(p).toString().replace('\\', '/');
                            String namespaceUri = normalizeNamespaceBaseUri(namespaceBaseUri) + relativePath;
                            String resourceUri = "jar:" + jarUrl.toString() + "!/" + normalizePath(xsdBasePath) + "/" + relativePath;
                            entries.add(new XsdEntry(namespaceUri, resourceUri));
                        });
            }
            return entries;
        }
    }

    private static List<XsdEntry> scanJarForXsds2(String xsdBasePath, String namespaceBaseUri, Class<?> callerClass, ClassLoader classLoader) throws IOException {
        // Get the JAR location via CodeSource to ensure caller-specific JAR
        CodeSource codeSource = callerClass.getProtectionDomain().getCodeSource();
        if (codeSource == null || codeSource.getLocation() == null) {
            throw new IOException("Cannot determine JAR location for caller class: " + callerClass.getName());
        }
        URL jarUrl = codeSource.getLocation();
        System.out.println("JAR URL: " + jarUrl);

        // Use ClassLoader to locate the resource path
        String resourcePath = normalizePath(xsdBasePath).substring(1); // Remove leading '/' for ClassLoader
        URL resourceUrl = classLoader.getResource(resourcePath);
        if (resourceUrl == null) {
            return Collections.emptyList();
        }

        // Verify the resource is in the caller's JAR
        String jarUrlStr = jarUrl.toString();
        String resourceUrlStr = resourceUrl.toString();
        if (!resourceUrlStr.startsWith("jar:" + jarUrlStr)) {
            throw new IOException("Resource path " + xsdBasePath + " found outside caller's JAR: " + resourceUrlStr);
        }

        // Scan the JAR
        try (FileSystem jarFs = FileSystems.newFileSystem(URI.create("jar:" + jarUrlStr), Collections.emptyMap())) {
            Path xsdRoot = jarFs.getPath(normalizePath(xsdBasePath));
            if (!Files.exists(xsdRoot)) {
                return Collections.emptyList();
            }

            List<XsdEntry> entries = new ArrayList<>();
            try (Stream<Path> paths = Files.walk(xsdRoot)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".xsd"))
                        .forEach(p -> {
                            String relativePath = xsdRoot.relativize(p).toString().replace('\\', '/');
                            String namespaceUri = normalizeNamespaceBaseUri(namespaceBaseUri) + relativePath;
                            String resourceUri = "jar:" + jarUrlStr + "!/" + normalizePath(xsdBasePath) + "/" + relativePath;
                            entries.add(new XsdEntry(namespaceUri, resourceUri));
                        });
            }
            return entries;
        }
    }

    private static List<XsdEntry> scanJarForXsds3(String xsdBasePath, String namespaceBaseUri, Class<?> callerClass, ClassLoader classLoader) throws IOException {
        // Normalize resource path for ClassLoader (remove leading '/')
        String resourcePath = normalizePath(xsdBasePath).substring(1);
        Enumeration<URL> resources = classLoader.getResources(resourcePath);
        URL jarUrl = null;

        // Find the resource in the caller's JAR
        while (resources.hasMoreElements()) {
            URL resourceUrl = resources.nextElement();
            if ("jar".equals(resourceUrl.getProtocol())) {
                String resourceUrlStr = resourceUrl.toString();
                // Extract JAR path from jar:file:/path/to/jar!/resource
                String jarPath = resourceUrlStr.substring(4, resourceUrlStr.indexOf("!/"));
                // Verify if this JAR contains the callerClass
                URL classResource = classLoader.getResource(callerClass.getName().replace('.', '/') + ".class");
                if (classResource != null && classResource.toString().startsWith("jar:" + jarPath)) {
try {
    jarUrl = new URI(jarPath).toURL();
} catch (URISyntaxException ex) {
    throw new RuntimeException(ex);
}
                    break;
                }
            }
        }

        if (jarUrl == null) {
            return Collections.emptyList();
        }
        System.out.println("JAR URL: " + jarUrl);

        URL jarUrl2 = jarUrl;

        // Scan the JAR
        try (FileSystem jarFs = FileSystems.newFileSystem(URI.create("jar:" + jarUrl2.toString()), Collections.emptyMap())) {
            Path xsdRoot = jarFs.getPath(normalizePath(xsdBasePath));
            if (!Files.exists(xsdRoot)) {
                return Collections.emptyList();
            }

            List<XsdEntry> entries = new ArrayList<>();
            try (Stream<Path> paths = Files.walk(xsdRoot)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".xsd"))
                        .forEach(p -> {
                            String relativePath = xsdRoot.relativize(p).toString().replace('\\', '/');
                            String namespaceUri = normalizeNamespaceBaseUri(namespaceBaseUri) + relativePath;
                            String resourceUri = "jar:" + jarUrl2.toString() + "!/" + normalizePath(xsdBasePath) + "/" + relativePath;
                            entries.add(new XsdEntry(namespaceUri, resourceUri));
                        });
            }
            return entries;
        }
    }

    private static String escapeXml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private static class MemoryCatalogResource implements Resource {
        private final URI uri;
        private final String content;

        MemoryCatalogResource(URI uri, String content) {
            this.uri = uri;
            this.content = content;
        }

//        @Override
        public URL getURL() {
            try {
                return MemoryUriRegistry.createMemoryUrl(uri);
            } catch (IOException e) {
                try {
                    File tempFile = File.createTempFile("catalog", ".xml");
                    try (FileWriter writer = new FileWriter(tempFile)) {
                        writer.write(content);
                    }
                    tempFile.deleteOnExit();
                    return tempFile.toURI().toURL();
                } catch (IOException ex) {
                    throw new IllegalStateException("Failed to create URL for catalog resource: " + uri, ex);
                }
            }
        }

        @Override
        public Location getLocation() {
            return null;
        }

        @Override
        public Item getItem() {
            return null;
        }
    }

    private record XsdEntry(String namespaceUri, String resourceUri) {}
}