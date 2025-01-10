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

package com.yelstream.topp.standard.net;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.net.URISyntaxException;

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
            URI uri1=URIs.builder().uri(uri0).host("xxx.com").build();
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
            URI uri1=URIs.builder().uri(uri0).port(300).build();
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
            URIs.Builder builder=URIs.Builder.fromURI(uri);

            TaggedPath taggedPath=builder.taggedPath();

            Assertions.assertEquals("/yelstream.com/topp/application/docker-intelligence",taggedPath.path());
            Assertions.assertEquals("1.0.0",taggedPath.tag());
        }
        {
            String uriText="docker://nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence";
            URI uri=new URI(uriText);
            URIs.Builder builder=URIs.Builder.fromURI(uri);

            TaggedPath taggedPath=builder.taggedPath();

            Assertions.assertEquals("/yelstream.com/topp/application/docker-intelligence",taggedPath.path());
            Assertions.assertNull(taggedPath.tag());
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
}
