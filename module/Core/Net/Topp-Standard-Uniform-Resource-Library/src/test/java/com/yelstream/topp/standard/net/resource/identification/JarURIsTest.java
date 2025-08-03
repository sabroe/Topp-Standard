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

package com.yelstream.topp.standard.net.resource.identification;

import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import com.yelstream.topp.standard.net.resource.identification.type.JarURIs;
import com.yelstream.topp.standard.net.resource.identification.util.StandardURIPredicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test of {@link JarURIs}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-16
 */
class JarURIsTest {
    @Test
    void testBuilderCreation() {
        JarURIs.Builder builder = JarURIs.builder();
        Assertions.assertNotNull(builder);
        Assertions.assertThrows(NullPointerException.class, builder::build);
        Assertions.assertThrows(NullPointerException.class, () -> builder.url((URI) null));
        Assertions.assertDoesNotThrow(() -> builder.url(URI.create("file:/my.jar")));
    }

    @Test
    void testFormattingSimpleJarUrl() {
        String expected = "jar:file:/my.jar!/com/example/MyClass.class";
        String result = JarURIs.create("jar", "file:/my.jar", "com/example/MyClass.class");
        Assertions.assertEquals(expected, result);

        JarURIs.Builder builder = JarURIs.builder()
            .scheme("jar")
            .url("file:/my.jar")
            .entry("com/example/MyClass.class");
        Assertions.assertEquals(expected, builder.build());

        builder = JarURIs.builder()
            .scheme(StandardScheme.JAR.getScheme())
            .url(URI.create("file:/my.jar"))
            .entry(URI.create("com/example/MyClass.class"));
        Assertions.assertEquals(expected, builder.build());
        Assertions.assertEquals(URI.create(expected), builder.buildURL());
    }

    @Test
    void testFormattingDoubleNestedJarUrl() {
        String expected = "jar:jar:file:/my.jar!/inner.jar!/com/example/MyClass.class";
        String innerUrl = "jar:file:/my.jar!/inner.jar";
        String result = JarURIs.create("jar", innerUrl, "com/example/MyClass.class");
        Assertions.assertEquals(expected, result);

        JarURIs.Builder builder = JarURIs.builder()
            .scheme("jar")
            .url(innerUrl)
            .entry("com/example/MyClass.class");
        Assertions.assertEquals(expected, builder.build());

        builder = JarURIs.builder()
            .scheme(StandardScheme.JAR.getScheme())
            .url(URI.create("jar:file:/my.jar!/inner.jar"))
            .entry(URI.create("com/example/MyClass.class"));
        Assertions.assertEquals(expected, builder.build());
        Assertions.assertEquals(URI.create(expected), builder.buildURL());
    }

    @Test
    void testFormattingTripleNestedJarUrl() {
        String expected = "jar:jar:jar:file:/my.jar!/inner.jar!/core.jar!/com/example/MyClass.class";
        String innerUrl = "jar:jar:file:/my.jar!/inner.jar!/core.jar";
        String result = JarURIs.create("jar", innerUrl, "com/example/MyClass.class");
        Assertions.assertEquals(expected, result);

        JarURIs.Builder builder = JarURIs.builder()
            .scheme("jar")
            .url(innerUrl)
            .entry("com/example/MyClass.class");
        Assertions.assertEquals(expected, builder.build());

        builder = JarURIs.builder()
            .scheme(StandardScheme.JAR.getScheme())
            .url(URI.create("jar:jar:file:/my.jar!/inner.jar!/core.jar"))
            .entry(URI.create("com/example/MyClass.class"));
        Assertions.assertEquals(expected, builder.build());
        Assertions.assertEquals(URI.create(expected), builder.buildURL());
    }

    @Test
    void testFormattingEmptyEntry() {
        String expected = "jar:file:/my.jar!/";
        String result = JarURIs.create("jar", "file:/my.jar", "");
        Assertions.assertEquals(expected, result);

        JarURIs.Builder builder = JarURIs.builder()
            .scheme("jar")
            .url("file:/my.jar");
        Assertions.assertEquals(expected, builder.build());

        builder = JarURIs.builder()
            .scheme(StandardScheme.JAR.getScheme())
            .url(URI.create("file:/my.jar"));
        Assertions.assertEquals(expected, builder.build());
        Assertions.assertEquals(URI.create(expected), builder.buildURL());
    }

    @Test
    void testParsingSimpleJarUrl() {
        String url = "jar:file:/my.jar!/com/example/MyClass.class";
        JarURIs.Builder builder = JarURIs.Builder.of(URI.create(url));
        Assertions.assertNotNull(builder);
        Assertions.assertEquals(url, builder.build());
        Assertions.assertEquals(URI.create(url), builder.buildURL());

        JarURIs.SplitURI splitURI = JarURIs.SplitURI.of(URI.create(url));
        Assertions.assertEquals("jar", splitURI.getScheme());
        Assertions.assertEquals("file:/my.jar", splitURI.getUrl());
        Assertions.assertEquals("com/example/MyClass.class", splitURI.getEntry());

        // Verify SplitURI -> Builder
        JarURIs.Builder fromSplit = JarURIs.Builder.of(splitURI);
        Assertions.assertEquals(url, fromSplit.build());

        // Verify inner URL
        Assertions.assertDoesNotThrow(() -> URI.create(splitURI.getUrl().substring(0, splitURI.getUrl().length() - 1)));
        // Verify entry
        Assertions.assertDoesNotThrow(() -> StandardURIPredicate.IsPathOnly.requireMatch(URI.create(splitURI.getEntry())));
        // Verify scheme
        Assertions.assertDoesNotThrow(() -> StandardScheme.JAR.getScheme().requireMatch(URI.create(url)));
    }

    @Test
    void testParsingDoubleNestedJarUrl() {
        String url = "jar:jar:file:/my.jar!/inner.jar!/com/example/MyClass.class";
        JarURIs.Builder builder = JarURIs.Builder.of(URI.create(url));
        Assertions.assertNotNull(builder);
        Assertions.assertEquals(url, builder.build());
        Assertions.assertEquals(URI.create(url), builder.buildURL());

        JarURIs.SplitURI splitURI = JarURIs.SplitURI.of(URI.create(url));
        Assertions.assertEquals("jar", splitURI.getScheme());
        Assertions.assertEquals("jar:file:/my.jar!/inner.jar", splitURI.getUrl());
        Assertions.assertEquals("com/example/MyClass.class", splitURI.getEntry());

        // Verify SplitURI -> Builder
        JarURIs.Builder fromSplit = JarURIs.Builder.of(splitURI);
        Assertions.assertEquals(url, fromSplit.build());

        // Verify inner URL
        String innerUrl = splitURI.getUrl().substring(0, splitURI.getUrl().length());
        JarURIs.SplitURI innerSplit = JarURIs.SplitURI.of(URI.create(innerUrl));
        Assertions.assertEquals("jar", innerSplit.getScheme());
        Assertions.assertEquals("file:/my.jar", innerSplit.getUrl());
        Assertions.assertEquals("inner.jar", innerSplit.getEntry());
        // Verify entry
        Assertions.assertDoesNotThrow(() -> StandardURIPredicate.IsPathOnly.requireMatch(URI.create(splitURI.getEntry())));
        // Verify scheme
        Assertions.assertDoesNotThrow(() -> StandardScheme.JAR.getScheme().requireMatch(URI.create(url)));
    }

    @Test
    void testParsingTripleNestedJarUrl() {
        String url = "jar:jar:jar:file:/my.jar!/inner.jar!/core.jar!/com/example/MyClass.class";
        JarURIs.Builder builder = JarURIs.Builder.of(URI.create(url));
        Assertions.assertNotNull(builder);
        Assertions.assertEquals(url, builder.build());
        Assertions.assertEquals(URI.create(url), builder.buildURL());

        JarURIs.SplitURI splitURI = JarURIs.SplitURI.of(URI.create(url));
        Assertions.assertEquals("jar", splitURI.getScheme());
        Assertions.assertEquals("jar:jar:file:/my.jar!/inner.jar!/core.jar", splitURI.getUrl());
        Assertions.assertEquals("com/example/MyClass.class", splitURI.getEntry());

        // Verify SplitURI -> Builder
        JarURIs.Builder fromSplit = JarURIs.Builder.of(splitURI);
        Assertions.assertEquals(url, fromSplit.build());

        // Verify inner URL (first level)
        String innerUrl1 = splitURI.getUrl().substring(0, splitURI.getUrl().length());
        JarURIs.SplitURI innerSplit1 = JarURIs.SplitURI.of(URI.create(innerUrl1));
        Assertions.assertEquals("jar", innerSplit1.getScheme());
        Assertions.assertEquals("jar:file:/my.jar!/inner.jar", innerSplit1.getUrl());
        Assertions.assertEquals("core.jar", innerSplit1.getEntry());

        // Verify inner URL (second level)
        String innerUrl2 = innerSplit1.getUrl().substring(0, innerSplit1.getUrl().length());
        JarURIs.SplitURI innerSplit2 = JarURIs.SplitURI.of(URI.create(innerUrl2));
        Assertions.assertEquals("jar", innerSplit2.getScheme());
        Assertions.assertEquals("file:/my.jar", innerSplit2.getUrl());
        Assertions.assertEquals("inner.jar", innerSplit2.getEntry());

        // Verify inner URL (third level)
        String innerUrl3 = innerSplit2.getUrl().substring(0, innerSplit2.getUrl().length());
        Assertions.assertDoesNotThrow(() -> URI.create(innerUrl3));
        Assertions.assertEquals("file:/my.jar", innerUrl3);
        // Verify entry
        Assertions.assertDoesNotThrow(() -> StandardURIPredicate.IsPathOnly.requireMatch(URI.create(splitURI.getEntry())));
        // Verify scheme
        Assertions.assertDoesNotThrow(() -> StandardScheme.JAR.getScheme().requireMatch(URI.create(url)));
    }

/*
    @Test
    void testParsingInvalidEntryWithSeparator() {
        String url = "jar:file:/my.jar!/entry!/path";
        Assertions.assertThrows(IllegalStateException.class, () -> JarURIs.Builder.of(URI.create(url)));
        Assertions.assertThrows(IllegalStateException.class, () -> JarURIs.SplitURI.of(URI.create(url)));
    }
*/

    @Test
    void testParsingInvalidScheme() {
        String url = "file:http:/my.jar!/com/example/MyClass.class";
        Assertions.assertThrows(IllegalArgumentException.class, () -> JarURIs.createSplitURI(url));
        Assertions.assertThrows(IllegalArgumentException.class, () -> JarURIs.createSplitURI(url));
    }

    @Test
    void testRegEx() {
        {
            String uri = "jar:file:/my.jar!/";
            Assertions.assertTrue(uri.matches(JarURIs.JAR_URL_REGEX));
        }
        {
            String uri = "jar:file:/my.jar!/com/example/MyClass.class";
            Assertions.assertTrue(uri.matches(JarURIs.JAR_URL_REGEX));
        }
        {
            String uri = "jar:jar:jar:file:/my.jar!/inner.jar!/core.jar!/com/example/MyClass.class";
            Assertions.assertTrue(uri.matches(JarURIs.JAR_URL_REGEX));
        }
        {
            String uri = "jar:jar:jar:file:/my.jar!/inner.jar!/core.jar!/com/example/MyClass.class";
            Pattern pattern=Pattern.compile(JarURIs.JAR_URL_REGEX);
            Matcher matcher=pattern.matcher(uri);
            Assertions.assertTrue(matcher.matches());
            Assertions.assertEquals("jar",matcher.group("scheme"));
            Assertions.assertEquals("jar:jar:file:/my.jar!/inner.jar!/core.jar",matcher.group("url"));
            Assertions.assertEquals("com/example/MyClass.class",matcher.group("entry"));
        }
    }

    @Test
    void testBuildSplitURL() {
        String url = "jar:file:/my.jar!/com/example/MyClass.class";
        Assertions.assertTrue(url.matches(JarURIs.JAR_URL_REGEX));
        JarURIs.Builder builder = JarURIs.builder()
            .scheme("jar")
            .url("file:/my.jar")
            .entry("com/example/MyClass.class");
        JarURIs.SplitURI splitURI = builder.buildSplitURL();
        Assertions.assertEquals("jar", splitURI.getScheme());
        Assertions.assertEquals("file:/my.jar", splitURI.getUrl());
        Assertions.assertEquals("com/example/MyClass.class", splitURI.getEntry());
        Assertions.assertEquals(url, builder.build());
    }
}
