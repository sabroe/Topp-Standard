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

package com.yelstream.topp.standard.system.holder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.Supplier;

/**
 * Tests for {@link Containers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-10
 */
class ContainersTest {
    /**
     * Tests creation of a container with a pre-computed item.
     */
    @Test
    void testCreateContainerWithItem() {
        Container<String> container = Containers.createContainer("Hello");
        Assertions.assertNotNull(container, "Container should not be null.");
        Assertions.assertEquals("Hello", container.getItem(), "Item should match input.");
    }

    /**
     * Tests creation of a container with a lazy supplier.
     */
    @Test
    void testCreateContainerWithSupplier() {
        Supplier<String> supplier = () -> "Lazy";
        Container<String> container = Containers.createContainer(supplier);
        Assertions.assertNotNull(container, "Container should not be null.");
        Assertions.assertEquals("Lazy", container.getItem(), "Item should match supplier result.");
    }

    /**
     * Tests creation of a resettable container with a supplier.
     */
    @Test
    void testCreateResettableContainer() {
        int[] callCount = {0}; // Track supplier invocations
        Supplier<String> supplier = () -> "Value" + (++callCount[0]);
        ResettableContainer<String> container = Containers.createResettableContainer(supplier);

        Assertions.assertEquals("Value1", container.getItem(), "Initial item should be computed.");
        Assertions.assertEquals("Value1", container.getItem(), "Cached item should be reused.");
        Assertions.assertEquals(1, callCount[0], "Supplier should be called once.");

        container.reset();
        Assertions.assertEquals("Value2", container.getItem(), "Item after reset should be recomputed.");
        Assertions.assertEquals(2, callCount[0], "Supplier should be called again after reset.");
    }
}
