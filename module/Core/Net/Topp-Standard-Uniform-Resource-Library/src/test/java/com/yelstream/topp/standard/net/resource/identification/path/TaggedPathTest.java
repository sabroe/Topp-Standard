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

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;

/**
 * Tests of {@link TaggedPath}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2024-06-09
 */
class TaggedPathTest {

    @Test
    void testOfPathTagged() {
        TaggedPath taggedPath = TaggedPath.ofPath("/yelstream.com/topp:1.0.0");
        Assertions.assertEquals("/yelstream.com/topp", taggedPath.getPath());
        Assertions.assertEquals("1.0.0", taggedPath.getTag());
        Assertions.assertEquals("/yelstream.com/topp:1.0.0", taggedPath.toPath());
        Assertions.assertEquals(URI.create("/yelstream.com/topp:1.0.0"), taggedPath.toURI());
        Assertions.assertEquals(Arrays.asList("yelstream.com", "topp"), taggedPath.toSegmentedPath().getElements());
    }

    @Test
    void testOfPathUntagged() {
        TaggedPath taggedPath = TaggedPath.ofPath("/yelstream.com/topp");
        Assertions.assertEquals("/yelstream.com/topp", taggedPath.getPath());
        Assertions.assertEquals(null, taggedPath.getTag());
        Assertions.assertEquals("/yelstream.com/topp", taggedPath.toPath());
        Assertions.assertEquals(URI.create("/yelstream.com/topp"), taggedPath.toURI());
        Assertions.assertEquals(Arrays.asList("yelstream.com", "topp"), taggedPath.toSegmentedPath().getElements());
    }

    @Test
    void testOfPathEmpty() {
        TaggedPath taggedPath = TaggedPath.ofPath("");
        Assertions.assertEquals("", taggedPath.getPath());
        Assertions.assertEquals(null, taggedPath.getTag());
        Assertions.assertEquals("", taggedPath.toPath());
        Assertions.assertEquals(URI.create(""), taggedPath.toURI());
        Assertions.assertEquals(List.of(), taggedPath.toSegmentedPath().getElements());
    }

    @Test
    void testOfPathNull() {
        Assertions.assertThrows(NullPointerException.class, () -> TaggedPath.ofPath(null));
    }

    @Test
    void testOfPathOnlyTag() {
        TaggedPath taggedPath = TaggedPath.ofPath(":1.0.0");
        Assertions.assertEquals("", taggedPath.getPath());
        Assertions.assertEquals("1.0.0", taggedPath.getTag());
        Assertions.assertEquals(":1.0.0", taggedPath.toPath());
        Assertions.assertThrows(IllegalArgumentException.class, taggedPath::toURI);
        Assertions.assertEquals(List.of(), taggedPath.toSegmentedPath().getElements());
    }

    @Test
    void testOfURI() {
        TaggedPath taggedPath = TaggedPath.ofURI(URI.create("/yelstream.com/topp:1.0.0"));
        Assertions.assertEquals("/yelstream.com/topp", taggedPath.getPath());
        Assertions.assertEquals("1.0.0", taggedPath.getTag());
        Assertions.assertEquals("/yelstream.com/topp:1.0.0", taggedPath.toPath());
        Assertions.assertEquals(URI.create("/yelstream.com/topp:1.0.0"), taggedPath.toURI());
    }

    @Test
    void testInvalidPathWithColon() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TaggedPath.ofPath("/yelstream.com:invalid:1.0.0"));
    }

    @Test
    void testInvalidTagWithSlash() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TaggedPath.ofPath("/yelstream.com/topp:1.0.0/sub"));
    }

    @Test
    void testInvalidURIWithScheme() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TaggedPath.ofURI(URI.create("http://example.com/path")));
    }

    @Test
    void testBuilderOverride() {
        TaggedPath taggedPath = TaggedPath.builder()
            .path("/yelstream.com/topp")
            .tag("1.0.0")
            .build();
        Assertions.assertEquals("/yelstream.com/topp:1.0.0", taggedPath.toPath());

        TaggedPath updated = taggedPath.toBuilder()
            .segmentedPath(SegmentedPath.ofPath("/new/path"))
            .tag("2.0.0")
            .build();
        Assertions.assertEquals("/new/path", updated.getPath());
        Assertions.assertEquals("2.0.0", updated.getTag());
        Assertions.assertEquals("/new/path:2.0.0", updated.toPath());
    }

    @Test
    void testReplacePathAndTag() {
        TaggedPath taggedPath = TaggedPath.ofPath("/yelstream.com/topp:1.0.0");
        TaggedPath updatedPath = taggedPath.replacePath("/new/path");
        Assertions.assertEquals("/new/path:1.0.0", updatedPath.toPath());

        TaggedPath updatedTag = taggedPath.replaceTag("2.0.0");
        Assertions.assertEquals("/yelstream.com/topp:2.0.0", updatedTag.toPath());

        TaggedPath updatedSegmented = taggedPath.replacePath(SegmentedPath.ofPath("/new/path/"));
        Assertions.assertEquals("/new/path/:1.0.0", updatedSegmented.toPath());
        Assertions.assertTrue(updatedSegmented.toSegmentedPath().isContainer());
    }
}
