package com.yelstream.topp.standard.xml.catalog.provider.util;

import com.yelstream.topp.standard.resource.io.source.InputSource;
import com.yelstream.topp.standard.resource.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility to scan JAR or directory for XSD files and create catalog resources.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-25
 */
public final class JarXsdResourceScanner3 {

    public static List<Resource> scanForXsdResources(String xsdBasePath, String namespaceBaseUri, Class<?> callerClass, ClassLoader classLoader) throws IOException, URISyntaxException {
        String catalogContent = generateCatalogContent(xsdBasePath, namespaceBaseUri, callerClass, classLoader);
        URI catalogUri = URI.create("memory://jar-xsd-catalog-" + System.identityHashCode(new Object()));
        MemoryUriRegistry.register(catalogUri, catalogContent);
        Resource resource = new MemoryCatalogResource(catalogUri, catalogContent);
        return List.of(resource);
    }

    private static String normalizeNamespaceBaseUri(String uri) {
        return uri.endsWith("/") ? uri : uri + "/";
    }

    private static String generateCatalogContent(String xsdBasePath, String namespaceBaseUri, Class<?> callerClass, ClassLoader classLoader) throws IOException, URISyntaxException {
        List<XsdResource> resources = scanResourcesForXsds(xsdBasePath, namespaceBaseUri, callerClass, classLoader);
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<catalog xmlns=\"urn:oasis:names:tc:entity:xmlns:xml:catalog\">\n");
        for (XsdResource resource : resources) {
            sb.append(String.format("    <uri name=\"%s\" uri=\"%s\"/>\n",
                    escapeXml(resource.resourceName()), escapeXml(resource.resourceUri())));
        }
        sb.append("</catalog>");
        return sb.toString();
    }

    private static List<XsdResource> scanResourcesForXsds(String xsdBasePath, String namespaceBaseUri, Class<?> callerClass, ClassLoader classLoader) throws IOException, URISyntaxException {
//        URL locationUrl = Resources.getLocationOfClass(callerClass);
        URL locationUrl = Resources.getCodeBaseOfClass(callerClass);
        String resourcePath = Resources.normalizePath(xsdBasePath);
        List<XsdResource> entries = new ArrayList<>();
        String protocol = locationUrl.getProtocol();
        String urlStr = locationUrl.toString();

        if ("jar".equals(protocol)) {
            // JAR: jar:file:/.../jar!/
            try (FileSystem jarFs = FileSystems.newFileSystem(URI.create(urlStr), Collections.emptyMap())) {
                Path xsdRoot = jarFs.getPath("/" + resourcePath);
                String baseUrl = urlStr + (resourcePath.isEmpty() ? "" : resourcePath + "/");
                scanXsdFiles(xsdRoot, xsdBasePath, namespaceBaseUri, entries, baseUrl);
            }
        } else if ("file".equals(protocol)) {
            // Directory: file:/.../classes/
            Path xsdRoot = Paths.get(locationUrl.toURI()).resolve(resourcePath);
            String baseUrl = urlStr + (urlStr.endsWith("/") ? "" : resourcePath + "/");
            scanXsdFiles(xsdRoot, xsdBasePath, namespaceBaseUri, entries, baseUrl);
        } else {
            // Other protocols (http:, vfs:, etc.)
            String resourceUrlStr = urlStr + (urlStr.endsWith("/") || urlStr.endsWith("!/") ? "" : "/") + resourcePath;
            URL resourceUrl = URI.create(resourceUrlStr).toURL();
            scanXsdFilesFromUrl(resourceUrl, xsdBasePath, namespaceBaseUri, entries, resourceUrlStr, classLoader);
        }

        return entries;
    }

    private static void scanXsdFiles(Path xsdRoot, String xsdBasePath, String namespaceBaseUri, List<XsdResource> entries, String baseUrl) throws IOException {
        if (!Files.exists(xsdRoot)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(xsdRoot)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".xsd"))
                    .forEach(p -> {
                        String relativePath = xsdRoot.relativize(p).toString().replace('\\', '/');
                        String filename = Paths.get(relativePath).getFileName().toString();
                        String namespaceUri = normalizeNamespaceBaseUri(namespaceBaseUri) + relativePath;
                        String resourceUri = baseUrl.endsWith("/") ? baseUrl + relativePath : baseUrl + "/" + relativePath;
                        entries.add(new XsdResource(namespaceUri, resourceUri));
                        entries.add(new XsdResource(filename, resourceUri));
                        String targetNamespace = getTargetNamespace(p, resourceUri);
                        if (targetNamespace != null) {
                            entries.add(new XsdResource(targetNamespace, resourceUri));
                        }
                    });
        }
    }

    private static void scanXsdFilesFromUrl(URL resourceUrl, String xsdBasePath, String namespaceBaseUri, List<XsdResource> entries, String baseUrl, ClassLoader classLoader) throws IOException {
        String resourcePath = Resources.normalizePath(xsdBasePath);
        try (InputStream is = resourceUrl.openStream()) {
            if (resourceUrl.getPath().toLowerCase().endsWith(".xsd")) {
                String filename = Paths.get(resourceUrl.getPath()).getFileName().toString();
                String namespaceUri = normalizeNamespaceBaseUri(namespaceBaseUri) + filename;
                String resourceUri = resourceUrl.toString();
                entries.add(new XsdResource(namespaceUri, resourceUri));
                entries.add(new XsdResource(filename, resourceUri));
                String targetNamespace = getTargetNamespaceFromInputStream(is, resourceUri);
                if (targetNamespace != null) {
                    entries.add(new XsdResource(targetNamespace, resourceUri));
                }
            } else {
                // Directory-like scanning via ClassLoader
                Enumeration<URL> resources = classLoader.getResources(resourcePath);
                while (resources.hasMoreElements()) {
                    URL xsdUrl = resources.nextElement();
                    String xsdUrlStr = xsdUrl.toString();
                    if (xsdUrlStr.startsWith(baseUrl) && xsdUrlStr.toLowerCase().endsWith(".xsd")) {
                        String relativePath = xsdUrlStr.substring(baseUrl.length());
                        String filename = Paths.get(relativePath).getFileName().toString();
                        String namespaceUri = normalizeNamespaceBaseUri(namespaceBaseUri) + relativePath;
                        String resourceUri = xsdUrlStr;
                        entries.add(new XsdResource(namespaceUri, resourceUri));
                        entries.add(new XsdResource(filename, resourceUri));
                        try (InputStream xsdIs = xsdUrl.openStream()) {
                            String targetNamespace = getTargetNamespaceFromInputStream(xsdIs, resourceUri);
                            if (targetNamespace != null) {
                                entries.add(new XsdResource(targetNamespace, resourceUri));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to scan non-file/jar URL: " + resourceUrl + ": " + e.getMessage());
        }
    }

    private static String getTargetNamespace(Path xsdPath, String resourceUri) {
        try (InputStream is = URI.create(resourceUri).toURL().openStream()) {
            return getTargetNamespaceFromInputStream(is, resourceUri);
        } catch (Exception e) {
            System.err.println("Failed to parse targetNamespace for " + resourceUri + ": " + e.getMessage());
            return null;
        }
    }

    private static String getTargetNamespaceFromInputStream(InputStream is, String resourceUri) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            Element schemaElement = doc.getDocumentElement();
            return schemaElement.getAttribute("targetNamespace");
        } catch (Exception e) {
            System.err.println("Failed to parse targetNamespace for " + resourceUri + ": " + e.getMessage());
            return null;
        }
    }

    private static String escapeXml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private static class MemoryCatalogResource implements Resource {  //TO-DO: Move to Loader-library?
        private final URI uri;
        private final String content;

        MemoryCatalogResource(URI uri, String content) {
            this.uri = uri;
            this.content = content;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public URI getURI() {
            return uri;
        }

        @Override
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
        public InputSource readable() {
            return null;
        }
    }

    private record XsdResource(String resourceName, String resourceUri) {}
}