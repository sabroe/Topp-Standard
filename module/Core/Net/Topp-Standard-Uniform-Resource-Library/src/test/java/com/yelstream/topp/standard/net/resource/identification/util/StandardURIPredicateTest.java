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

package com.yelstream.topp.standard.net.resource.identification.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Tests of {@link StandardURIPredicate}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-16
 */
class StandardURIPredicateTest {

    private URI createURI(String uriString) throws URISyntaxException {
        return new URI(uriString);
    }

    @Test
    void testDockerTaggedURI() throws URISyntaxException {
        URI uri = createURI("docker://registry.example.com/repo/image:latest");
        Assertions.assertTrue(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsAbsolute.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasValidHost.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsPathTagged.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathOnly.matches(uri));
    }

    @Test
    void testDockerNonTaggedURI() throws URISyntaxException {
        URI uri = createURI("docker://registry.example.com/repo/image");
        Assertions.assertTrue(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsAbsolute.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasValidHost.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathTagged.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathOnly.matches(uri));
    }

    @Test
    void testJarURI() throws URISyntaxException {
        URI uri = createURI("jar:file:/path/to/file.jar!/resource");
        Assertions.assertFalse(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsAbsolute.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathTagged.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasValidHost.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathOnly.matches(uri));
    }

    @Test
    void testJdbcURI() throws URISyntaxException {
        URI uri = createURI("jdbc:mysql://localhost:3306/db");
        Assertions.assertFalse(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsAbsolute.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasValidHost.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathTagged.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathOnly.matches(uri));
    }

    @Test
    void testHttpURI() throws URISyntaxException {
        URI uri = createURI("https://example.com/path:tag");
        Assertions.assertTrue(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsAbsolute.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasValidHost.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsPathTagged.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathOnly.matches(uri));
    }

    @Test
    void testRelativePathTaggedURI() throws URISyntaxException {
        URI uri = createURI("/path/to/resource:tag");
        Assertions.assertTrue(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsPathOnly.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsPathTagged.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsAbsolute.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsRegular.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasValidHost.matches(uri));
    }

    @Test
    void testMailtoURI() throws URISyntaxException {
        URI uri = createURI("mailto:user@example.com");
        Assertions.assertTrue(StandardURIPredicate.IsOpaque.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsAbsolute.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasStandardScheme.matches(uri));
//        Assertions.assertFalse(StandardURIPredicate.IsRegular.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsRelative.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasValidHost.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathTagged.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsPathOnly.matches(uri));
    }

    @Test
    void testInvalidURI() {
        try {
            URI uri = createURI("invalid URI");
            StandardURIPredicate.IsAbsolute.matches(uri); // Should throw URISyntaxException
            org.junit.jupiter.api.Assertions.fail("Expected URISyntaxException");
        } catch (URISyntaxException e) {
            // Expected
        }
    }



    @Test
    void testJdbcOpaqueURI() throws URISyntaxException {
        URI uri = createURI("jdbc:sqlserver://localhost:1433");
//        Assertions.assertTrue(StandardURIPredicate.NonRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsOpaque.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasStandardScheme.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.IsHierarchical.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasValidHost.matches(uri));
        Assertions.assertEquals("jdbc", uri.getScheme());
        Assertions.assertEquals("sqlserver://localhost:1433", uri.getSchemeSpecificPart());
    }

    @Test
    void testJdbcWithParameters() throws URISyntaxException {
        URI uri = createURI("jdbc:sqlserver://localhost:1433;databaseName=database1");
//        Assertions.assertTrue(StandardURIPredicate.NonRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.IsOpaque.matches(uri));
//        Assertions.assertTrue(StandardURIPredicate.IsNonRegular.matches(uri));
        Assertions.assertTrue(StandardURIPredicate.HasStandardScheme.matches(uri));
        Assertions.assertFalse(StandardURIPredicate.HasAuthority.matches(uri));
        Assertions.assertEquals("jdbc", uri.getScheme());
        Assertions.assertEquals("sqlserver://localhost:1433;databaseName=database1", uri.getSchemeSpecificPart());
    }
}
