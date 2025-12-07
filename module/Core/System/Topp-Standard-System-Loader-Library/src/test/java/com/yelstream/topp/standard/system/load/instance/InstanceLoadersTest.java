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

package com.yelstream.topp.standard.system.load.instance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Tests for {@link InstanceLoaders}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-10
 */
class InstanceLoadersTest {
   /**
     * Tests creation of an empty loader.
     */
    @Test
    void testEmptyLoader() {
        InstanceLoader<String> loader = InstanceLoaders.emptyLoader();  //Note: #emptyLoader!
        Assertions.assertNotNull(loader, "Loader should not be null.");
        Assertions.assertNotNull(loader.getRegistry(), "Registry should not be null.");
        Assertions.assertTrue(loader.getInstances().isEmpty(), "Instances should be empty.");
    }

    /**
     * Tests creation of a loader for a specific class.
     */
    @Test
    void testForClass() {
        // Mock ServiceLoaders.loadServices behavior
        Supplier<List<String>> mockSupplier = () -> Arrays.asList("Service1", "Service2");
        InstanceLoader<String> loader = InstanceLoaders.forClass(String.class);  //Note: #forClass!

        // Simulate adding a supplier (since ServiceLoaders.loadServices is not provided)
        loader.getRegistry().clear(); // Clear any default suppliers
        loader.getRegistry().add(mockSupplier);

        List<String> instances = loader.getInstances();
        Assertions.assertEquals(Arrays.asList("Service1", "Service2"), instances, "Instances should match mock supplier.");
        Assertions.assertEquals("Service1", loader.getFirstInstance().orElse(null), "First instance should match.");
    }
}