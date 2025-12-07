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

package com.yelstream.topp.standard.net.resource.identification.path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test of {@link SegmentedPath}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-17
 */
class SegmentedPathTest {

    @Test
    void testOfPath_absoluteContainerPath() {
        SegmentedPath path = SegmentedPath.ofPath("/a/b/c/");
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertTrue(path.isContainer());
        Assertions.assertFalse(path.isContent());
        Assertions.assertFalse(path.isContainerRoot());
        Assertions.assertEquals(3, path.length());
        Assertions.assertEquals("/a/b/c/", path.toPath());
        Assertions.assertDoesNotThrow(path::validate);
    }

    @Test
    void testOfPath_relativeContentPath() {
        SegmentedPath path = SegmentedPath.ofPath("a/b/c");
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), path.getElements());
        Assertions.assertFalse(path.isAbsolute());
        Assertions.assertFalse(path.isContainer());
        Assertions.assertTrue(path.isContent());
        Assertions.assertFalse(path.isContainerRoot());
        Assertions.assertEquals(3, path.length());
        Assertions.assertEquals("a/b/c", path.toPath());
        Assertions.assertDoesNotThrow(path::validate);
    }

    @Test
    void testOfPath_rootContainer() {
        SegmentedPath path = SegmentedPath.ofPath("/");
        Assertions.assertEquals(Collections.emptyList(), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertTrue(path.isContainer());
        Assertions.assertFalse(path.isContent());
        Assertions.assertTrue(path.isContainerRoot());
        Assertions.assertEquals(0, path.length());
        Assertions.assertEquals("/", path.toPath());
        Assertions.assertDoesNotThrow(path::validate);
    }

    @Test
    void testOfPath_emptyPath() {
        SegmentedPath path = SegmentedPath.ofPath("");
        Assertions.assertEquals(Collections.emptyList(), path.getElements());
        Assertions.assertFalse(path.isAbsolute());
        Assertions.assertFalse(path.isContainer());
        Assertions.assertTrue(path.isContent());
        Assertions.assertFalse(path.isContainerRoot());
        Assertions.assertEquals(0, path.length());
        Assertions.assertEquals("", path.toPath());
        Assertions.assertDoesNotThrow(path::validate);
    }

    @Test
    void testOfPath_nullPath() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.ofPath(null));
    }

    @Test
    void testOfPath_blankPath() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.ofPath("   "));
    }

    @Test
    void testOfPath_invalidElements() {
//TO-DO: Consider this! Is essentially an absolute path with an empty element "" being a container?  Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.ofPath("//"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.ofPath("/a//c"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.ofPath("/a/ /c"));
    }

    @Test
    void testOfPath_validElements() {
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath(""));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("/"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("/a"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("a/"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("/a/"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("a/b"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("/a/b"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("a/b/"));
        Assertions.assertDoesNotThrow(() -> SegmentedPath.ofPath("/a/b/"));
    }

    @Test
    void testOfURI() {
        SegmentedPath path = SegmentedPath.ofURI(URI.create("/a/b/c"));
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertFalse(path.isContainer());
        Assertions.assertEquals("/a/b/c", path.toPath());
        Assertions.assertDoesNotThrow(path::validate);
    }

    @Test
    void testOfURI_invalidURI() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.ofURI(URI.create("http://example.com")));
    }

    @Test
    void testElementAt() {
        SegmentedPath path = SegmentedPath.ofPath("/a/b/c");
        Assertions.assertEquals("a", path.elementAt(0));
        Assertions.assertEquals("b", path.elementAt(1));
        Assertions.assertEquals("c", path.elementAt(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> path.elementAt(3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> path.elementAt(-1));
    }

    @Test
    void testBuilder_defaultValidation() {
        SegmentedPath path = SegmentedPath.builder()
                .absolute()
                .appendContainerElement("a")
                .appendContainerElement("b")
                .build();
        Assertions.assertEquals(Arrays.asList("a", "b"), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertTrue(path.isContainer());
        Assertions.assertEquals("/a/b/", path.toPath());
        Assertions.assertDoesNotThrow(path::validate);
    }

    @Test
    void testBuilder_customValidation() {
        SegmentedPath path = SegmentedPath.builder()
                .elementValidation(element -> {
                    if (element == null) {
                        throw new IllegalArgumentException("Custom validation: element cannot be null");
                    }
                    if (!element.matches("[a-z]+")) {
                        throw new IllegalArgumentException("Custom validation: element must be lowercase letters: " + element);
                    }
                })
                .absolute()
                .appendContainerElement("abc")
                .appendContainerElement("xyz")
                .build();
        Assertions.assertEquals(Arrays.asList("abc", "xyz"), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertTrue(path.isContainer());
        Assertions.assertEquals("/abc/xyz/", path.toPath());
    }

    @Test
    void testBuilder_customValidation_failure() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.builder()
                .elementValidation(element -> {
                    if (!element.matches("[a-z]+")) {
                        throw new IllegalArgumentException("Element must be lowercase: " + element);
                    }
                })
                .appendElement("ABC")
                .build());
    }

    @Test
    void testBuilder_appendElements() {
        SegmentedPath path = SegmentedPath.builder()
                .relative()
                .appendElements(Arrays.asList("x", "y"))
                .appendContentElement("z")
                .build();
        Assertions.assertEquals(Arrays.asList("x", "y", "z"), path.getElements());
        Assertions.assertFalse(path.isAbsolute());
        Assertions.assertFalse(path.isContainer());
        Assertions.assertEquals("x/y/z", path.toPath());
    }

    @Test
    void testBuilder_invalidElements() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.builder().appendElement(null).build());
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.builder().appendElement("").build());
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.builder().appendElement("   ").build());
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.builder().appendElement("a/b").build());
        Assertions.assertThrows(IllegalArgumentException.class, () -> SegmentedPath.builder().appendElements(Arrays.asList("a", null)).build());
    }

    @Test
    void testToURI() {
        SegmentedPath path = SegmentedPath.ofPath("/a/b/c/");
        URI uri = path.toURI();
        Assertions.assertEquals("/a/b/c/", uri.toString());
    }

    @Test
    void testBuilderFromPath() {
        SegmentedPath path = SegmentedPath.Builder.of("/x/y/z")
                .appendContentElement("w")
                .build();
        Assertions.assertEquals(Arrays.asList("x", "y", "z", "w"), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertFalse(path.isContainer());
        Assertions.assertEquals("/x/y/z/w", path.toPath());
    }

    @Test
    void testBuilder_validate() {
        SegmentedPath path = SegmentedPath.builder()
                .absolute()
                .appendElement("a")
                .validate()
                .build();
        Assertions.assertEquals(List.of("a"), path.getElements());
        Assertions.assertTrue(path.isAbsolute());
        Assertions.assertFalse(path.isContainer());
        Assertions.assertEquals("/a", path.toPath());
    }
}
