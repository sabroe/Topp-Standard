/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.net.resource.identification;

import com.yelstream.topp.standard.net.resource.identification.build.URIArgument;
import com.yelstream.topp.standard.net.resource.identification.path.TaggedPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test of {@link URIs}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-17
 */
@Slf4j
class URIsTest {
    /**
     * Tests the matching, parsing and rebuilding of URIs in the most common formats.
     * <p>
     *     Note that the origin of these examples is the Java SE 21 JavaDoc!
     * </p>
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @ParameterizedTest
    @ValueSource(strings=
        {
            "mailto:java-net@www.example.com",
            "news:comp.lang.java",
            "urn:isbn:096139210x",
            "http://example.com/languages/java/",
            "sample/a/index.html#28",
            "../../demo/b/index.html",
            "file:///~/calendar",
            "file://server/~/calendar",
            "file://server/~/calendar-dir/"
        }
    )
    void rebuildURI(String uri) throws URISyntaxException {
        URI uri1=new URI(uri);
        URIs.Builder builder=URIs.builder();
        builder.uri(uri1);
        URI uri2=builder.build();

        Assertions.assertEquals(uri1,uri2);
        Assertions.assertEquals(uri,uri2.toString());
    }

    /**
     * Tests {@link URIs.Builder#host(String)}.
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @Test
    void replaceHost() throws URISyntaxException {
        {
            URI uri0=new URI("http://example.com/languages/java/#xxx");
            URI uri1=URIs.builder().argument(URIArgument.builder().uri(uri0).host("xxx.com").build()).build();
            URI expectedUri=new URI("http://xxx.com/languages/java/#xxx");
            Assertions.assertEquals(expectedUri,uri1);
            Assertions.assertEquals(expectedUri.toString(),uri1.toString());
        }
    }

    /**
     * Tests {@link URIs.Builder#port(int)}.
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @Test
    void replacePort() throws URISyntaxException {
        {
            URI uri0=new URI("http://example.com/languages/java/#xxx");
            URI uri1=URIs.builder().argument(URIArgument.builder().uri(uri0).port(300).build()).build();
            URI expectedUri=new URI("http://example.com:300/languages/java/#xxx");
            Assertions.assertEquals(expectedUri,uri1);
            Assertions.assertEquals(expectedUri.toString(),uri1.toString());
        }
    }

    /**
     * Tests the matching, parsing and rebuilding of URIs in some not used or not compatible formats.
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @ParameterizedTest
    @ValueSource(strings=
        {
            "docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.8.2",
            "docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.8.3#some-fragment",
        }
    )
    void rebuildURILikeAndNonStandard(String uri) throws URISyntaxException {
        URI uri1=new URI(uri);
        URIs.Builder builder=URIs.builder();
        builder.uri(uri1);
        URI uri2=builder.build();

        Assertions.assertEquals(uri1,uri2);
        Assertions.assertEquals(uri,uri2.toString());
    }

    /**
     * Tests the matching, parsing and rebuilding of URIs with the non-standard scheme "docker".
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @ParameterizedTest
    @ValueSource(strings=
        {
            "docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.0.0",
            "docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.0.0#some-fragment",
            "docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.0.0?x=1&y=2&z=3#some-fragment"
        }
    )
    void schemaDocker(String uri) throws URISyntaxException {
        URI uri1=new URI(uri);
        URIs.Builder builder=URIs.builder();
        builder.uri(uri1);
        URI uri2=builder.build();

        Assertions.assertEquals(uri1,uri2);
        Assertions.assertEquals(uri,uri2.toString());

        Assertions.assertEquals("docker",uri2.getScheme());
        Assertions.assertEquals("nexus.yelstream.com",uri2.getHost());
        Assertions.assertEquals(5000,uri2.getPort());
        Assertions.assertEquals("/yelstream.com/topp/application/docker-intelligence:1.0.0",uri2.getPath());
    }

    @Test
    void taggedPath() throws URISyntaxException {
        {
            String uriText="docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.0.0";
            URI uri=new URI(uriText);
            URIArgument argument=URIArgument.of(uri);

            TaggedPath taggedPath=argument.taggedPath();

            Assertions.assertEquals("/yelstream.com/topp/application/docker-intelligence",taggedPath.getPath());
            Assertions.assertEquals("1.0.0",taggedPath.getTag());
        }
        {
            String uriText="docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence";
            URI uri=new URI(uriText);
            URIArgument argument=URIArgument.of(uri);

            TaggedPath taggedPath=argument.taggedPath();

            Assertions.assertEquals("/yelstream.com/topp/application/docker-intelligence",taggedPath.getPath());
            Assertions.assertNull(taggedPath.getTag());
        }
    }

    /**
     * Tests the matching, parsing and rebuilding of URIs in the most common formats.
     * <p>
     *     Note that the origin of these examples is
     *     <a href="https://en.wikipedia.org/wiki/Uniform_Resource_Identifier">Wikipedia</a>.
     * </p>
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @ParameterizedTest
    @ValueSource(strings=
        {
            "https://john.doe@www.example.com:1234/forum/questions/?tag=networking&order=newest#top",
            "https://john.doe@www.example.com:1234/forum/questions/?tag=networking&order=newest#:~:text=whatever",
            "ldap://[2001:db8::7]/c=GB?objectClass?one",
            "mailto:John.Doe@example.com",
            "news:comp.infosystems.www.servers.unix",
            "tel:+1-816-555-1212",
            "telnet://192.0.2.16:80/",
            "urn:oasis:names:specification:docbook:dtd:xml:4.1.2"
        }
    )
    void rebuildURI2(String uri) throws URISyntaxException {
        URI uri1=new URI(uri);
        URIs.Builder builder=URIs.builder();
        builder.uri(uri1);
        URI uri2=builder.build();

        Assertions.assertEquals(uri1,uri2);
        Assertions.assertEquals(uri,uri2.toString());
    }

    @Test
    void verifyURISchemeJar() {
        String uriText="jar:jar:file:///outer.jar!/inner.jar!/resource.txt";
        URI uri=URI.create(uriText);

        Assertions.assertEquals("jar",uri.getScheme());
        Assertions.assertEquals("jar:file:///outer.jar!/inner.jar!/resource.txt",uri.getSchemeSpecificPart());
        Assertions.assertEquals(null,uri.getPath());
    }
//    public static final String JMX_SERVICE_URL = "service:jmx:rmi:///jndi/rmi://localhost:10224/jmxrmi";

    @Test
    void verifyURISchemeService() {
        String uriText="service:jmx:rmi:///jndi/rmi://localhost:10224/jmxrmi";
        URI uri=URI.create(uriText);

        Assertions.assertEquals("service",uri.getScheme());
        Assertions.assertEquals("jmx:rmi:///jndi/rmi://localhost:10224/jmxrmi",uri.getSchemeSpecificPart());
        Assertions.assertEquals(null,uri.getPath());
    }

    @Test
    void verifyURISchemeJdbc() {
        String uriText="jdbc:sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1;encrypt=true;trustServerCertificate=false";
        URI uri=URI.create(uriText);

        Assertions.assertEquals("jdbc",uri.getScheme());
        Assertions.assertEquals("sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1;encrypt=true;trustServerCertificate=false",uri.getSchemeSpecificPart());
        Assertions.assertEquals(null,uri.getPath());
    }



    /**
     * Tests the matching, parsing and rebuilding of URIs with the non-standard scheme "jdbc:sqlserver".
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @ParameterizedTest
    @ValueSource(strings=
        {
            "jdbc:sqlserver://localhost:1433",
            "jdbc:sqlserver://localhost:1433;databaseName=database1",
            "jdbc:sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1",
            "jdbc:sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1;encrypt=true;trustServerCertificate=false"
        }
    )
@Disabled("Fix this! MSM, 2025-12-06.")
    void schemaJDBC(String uri) throws URISyntaxException {
        URI uri1=new URI(uri);
URIArgument a=URIArgument.of(uri1);
URIArgument b=URIArgument.of(URI.create(a.getSchemeSpecificPart()));
        URIs.Builder builder=URIs.builder();
//String modifiedUri = uri.replace("jdbc:sqlserver://", "sqlserver://");
//builder.uri(new URI(modifiedUri));
        builder.uri(uri1);
//builder.scheme("jdbc:sqlserver");
        URI uri2=builder.build();
System.out.println("Scheme: "+uri2.getScheme());
System.out.println("SchemeSpecificPart: "+uri2.getSchemeSpecificPart());
System.out.println("RawSchemeSpecificPart: "+uri2.getRawSchemeSpecificPart());
        Assertions.assertEquals("jdbc:sqlserver",uri2.getScheme());

        Assertions.assertEquals(uri1,uri2);
        Assertions.assertEquals(uri,uri2.toString());

        Assertions.assertEquals("jdbc",uri2.getScheme());
//TO-DO!        Assertions.assertEquals("sqlserver",uri2.getSchemeSpecificPart());
        Assertions.assertEquals("localhost",uri2.getHost());
        Assertions.assertEquals(1433,uri2.getPort());
        Assertions.assertEquals("",uri2.getPath());
    }

/*
    public static final String JMX_SERVICE_URL = "service:jmx:rmi:///jndi/rmi://localhost:10224/jmxrmi";
    public static final String BROKER_URL = "tcp://localhost:61616";
*/



    @ParameterizedTest
    @ValueSource(strings = {
            "jdbc:sqlserver://localhost:1433",
            "jar:jar:file:///path/to/outer.jar!/inner.jar!/resource.txt",
            "custom:foo:bar://example.com/path"
    })
@Disabled("Fix this! MSM, 2025-12-06.")
    void todo_schemaComposite(String uri) throws URISyntaxException {
        // Split the scheme and inner URI
        String[] schemeParts = uri.split(":", 3); // Split on first two colons
        String fullScheme = schemeParts.length > 1 ? schemeParts[0] + ":" + schemeParts[1] : schemeParts[0];
        String innerUriStr = schemeParts.length > 2 ? schemeParts[2] : schemeParts[1];

        // Handle jar-specific !/ entries if present
        String[] uriParts = innerUriStr.split("!/");
        String baseUri = uriParts[0];
        List<String> entries = uriParts.length > 1 ? Arrays.asList(uriParts).subList(1, uriParts.length) : Collections.emptyList();

        URI innerUri = new URI(baseUri);

        URIs.Builder builder = URIs.builder();
        builder.uri(innerUri);
//XXX???        builder.scheme(fullScheme); // Set composite scheme (e.g., jdbc:sqlserver)
        if (!entries.isEmpty()) {
    //??        builder.entries(entries); // Assume builder supports entries for jar: URLs
        }

        URI uri2 = builder.build();

        Assertions.assertEquals(uri, uri2.toString());
        Assertions.assertEquals(fullScheme, uri2.getScheme());
        Assertions.assertEquals(innerUri.getHost(), uri2.getHost());
        Assertions.assertEquals(innerUri.getPort(), uri2.getPort());
        Assertions.assertEquals(innerUri.getPath(), uri2.getPath());
        if (!entries.isEmpty()) {
    //??        Assertions.assertEquals(entries, uri2.getEntries());
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "jar:file:///path/to/outer.jar!/com/example/MyClass.class",
            "jar:jar:file:///path/to/outer.jar!/middle.jar!/inner.jar!/com/example/MyClass.class",
            "jar:jar:jar:jar:jar:jar:jar:jar:jar:jar:file:///path/to/outer.jar!/jar1.jar!/jar2.jar!/jar3.jar!/jar4.jar!/jar5.jar!/jar6.jar!/jar7.jar!/jar8.jar!/jar9.jar!/com/example/MyClass.class"
    })
@Disabled("Fix this! MSM, 2025-12-06.")
    void todo_schemaNestedJar(String uri) throws URISyntaxException {
        // Parse nested JAR URL
        List<String> entries = new ArrayList<>();
        String currentUri = uri;
        String fileUri = uri;
        int jarCount = 0;

        // Extract nested entries
        while (currentUri.startsWith("jar:")) {
            int separatorIndex = currentUri.indexOf("!/");
            if (separatorIndex == -1) break;
            entries.add(currentUri.substring(separatorIndex + 2));
            currentUri = currentUri.substring(4, separatorIndex); // Remove "jar:" and entry
            fileUri = currentUri;
            jarCount++;
        }

        URI innerUri = new URI(fileUri);

        URIs.Builder builder = URIs.builder();
        builder.uri(innerUri);
//XXX???        builder.scheme("jar".repeat(jarCount > 0 ? jarCount : 1)); // Set composite scheme (e.g., jar:jar:...)
    //??    builder.entries(entries); // Assume builder supports entries

        URI uri2 = builder.build();

        Assertions.assertEquals(uri, uri2.toString());
        Assertions.assertEquals("jar".repeat(jarCount > 0 ? jarCount : 1), uri2.getScheme());
        Assertions.assertEquals(innerUri.getHost(), uri2.getHost());
        Assertions.assertEquals(innerUri.getPort(), uri2.getPort());
        Assertions.assertEquals(innerUri.getPath(), uri2.getPath());
//??        Assertions.assertEquals(entries, uri2.getEntries()); // Assume getEntries() exists
    }

    @Test
@Disabled("Fix this! MSM, 2025-12-06.")
    void todo_schemaDeeplyNestedJar() throws URISyntaxException {
        StringBuilder uri = new StringBuilder("jar:".repeat(10));
        uri.append("file:///path/to/outer.jar");
        for (int i = 1; i <= 9; i++) {
            uri.append("!/jar").append(i).append(".jar");
        }
        uri.append("!/com/example/MyClass.class");

        String uriStr = uri.toString();
        List<String> entries = new ArrayList<>();
        String currentUri = uriStr;
        for (int i = 0; i < 10; i++) {
            int separatorIndex = currentUri.indexOf("!/");
            entries.add(currentUri.substring(separatorIndex + 2));
            currentUri = currentUri.substring(4, separatorIndex);
        }

        URI innerUri = new URI(currentUri);

        URIs.Builder builder = URIs.builder();
        builder.uri(innerUri);
//XXX???        builder.scheme("jar".repeat(10));
//???        builder.entries(entries);

        URI uri2 = builder.build();

        Assertions.assertEquals(uriStr, uri2.toString());
        Assertions.assertEquals("jar".repeat(10), uri2.getScheme());
        Assertions.assertEquals(innerUri.getHost(), uri2.getHost());
        Assertions.assertEquals(innerUri.getPort(), uri2.getPort());
        Assertions.assertEquals(innerUri.getPath(), uri2.getPath());
//???        Assertions.assertEquals(entries, uri2.getEntries());
    }


    /*
    service:jmx:jmxmp://localhost:8004
    service:jmx:iiop://localhost:1300/jndi/iiop://localhost:1300/jmxbean

    service:jmx:<vendor-protocol>://...
    service:jmx:t3://localhost:7001/jndi/weblogic.management.mbeanservers.runtime
    service:jmx:iiop://localhost:1300/jndi/iiop://localhost:1300/jmxbean

     */
}
