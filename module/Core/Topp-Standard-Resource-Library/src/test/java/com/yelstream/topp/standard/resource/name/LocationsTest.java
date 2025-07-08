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

package com.yelstream.topp.standard.resource.name;

import com.yelstream.topp.standard.resource.name.Locations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests {@code com.yelstream.topp.standard.load.resource.name.Locations}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-01
 */
class LocationsTest {
    @Test
    void testContainerNames() {
        Assertions.assertTrue(Locations.isNameForContainer(""));
        Assertions.assertTrue(Locations.isNameForContainer("/"));
        Assertions.assertTrue(Locations.isNameForContainer("path/to/dir/"));
        Assertions.assertFalse(Locations.isNameForContainer("path/to/file.txt"));
        Assertions.assertFalse(Locations.isNameForContainer(null));
    }

    @Test
    void testContentNames() {
        Assertions.assertFalse(Locations.isNameForContent(""));
        Assertions.assertFalse(Locations.isNameForContent("/"));
        Assertions.assertFalse(Locations.isNameForContent("path/to/dir/"));
        Assertions.assertTrue(Locations.isNameForContent("path/to/file.txt"));
        Assertions.assertFalse(Locations.isNameForContent(null));
    }

    @Test
    void testRootNames() {
        Assertions.assertTrue(Locations.isNameForContainerRoot(""));
        Assertions.assertTrue(Locations.isNameForContainerRoot("/"));
        Assertions.assertFalse(Locations.isNameForContainerRoot("path/to/dir/"));
        Assertions.assertFalse(Locations.isNameForContainerRoot("path/to/file.txt"));
        Assertions.assertFalse(Locations.isNameForContainerRoot(null));
    }

    @Test
    void testNormalization() {
        Assertions.assertEquals("", Locations.normalizeNameAsContainer(""));
        Assertions.assertEquals("", Locations.normalizeNameAsContainer("/"));
        Assertions.assertEquals("path/to/dir/", Locations.normalizeNameAsContainer("path/to/dir"));
        Assertions.assertEquals("path/to/dir/", Locations.normalizeNameAsContainer("path/to/dir/"));
        Assertions.assertEquals("path/to/dir/", Locations.normalizeNameAsContainer("//path//to//dir//"));
        Assertions.assertEquals("path/to/file.txt", Locations.normalizeNameAsContent("path/to/file.txt"));
        Assertions.assertEquals("path/to/file.txt", Locations.normalizeNameAsContent("path/to/file.txt/"));
        Assertions.assertEquals("path/to/file.txt", Locations.normalizeNameAsContent("//path//to//file.txt"));
        Assertions.assertEquals("", Locations.normalizeNameAsContainer(null));
        Assertions.assertNull(Locations.normalizeNameAsContent(null));
    }
}
