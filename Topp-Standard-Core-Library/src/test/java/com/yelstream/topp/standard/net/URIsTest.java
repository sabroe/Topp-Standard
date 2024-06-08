/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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
}
