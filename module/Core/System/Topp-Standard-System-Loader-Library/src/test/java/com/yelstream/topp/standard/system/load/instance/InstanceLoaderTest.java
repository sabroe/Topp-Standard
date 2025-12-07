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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Tests for {@link InstanceLoader}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-10
 */
class InstanceLoaderTest {
    /**
     * Tests empty loader behavior.
     */
    @Test
    void testEmptyLoader() {
        InstanceLoader<String> loader = InstanceLoaders.emptyLoader();
        List<String> instances = loader.getInstances();
        Assertions.assertNotNull(instances, "Instances list should not be null.");
        Assertions.assertTrue(instances.isEmpty(), "Instances list should be empty.");
        Assertions.assertTrue(loader.getFirstInstance().isEmpty(), "First instance should be empty.");
    }

    /**
     * Tests adding suppliers and retrieving instances.
     */
    @Test
    void testAddSuppliersAndGetInstances() {
        InstanceLoader<String> loader = InstanceLoaders.emptyLoader();
        Supplier<List<String>> supplier1 = () -> List.of("A", "B");
        Supplier<List<String>> supplier2 = () -> Collections.singletonList("C");

        loader.getRegistry().add(supplier1);  //Note: Add supplier!
        loader.getRegistry().add(supplier2);  //Note: Add supplier!

        List<String> instances = loader.getInstances();
        Assertions.assertEquals(List.of("A", "B", "C"), instances, "Instances should match combined supplier results.");

        Optional<String> firstInstance = loader.getFirstInstance();
        Assertions.assertTrue(firstInstance.isPresent(), "First instance should be present.");
        Assertions.assertEquals("A", firstInstance.get(), "First instance should be 'A'.");
    }

    /**
     * Tests reset functionality.
     */
    @Test
    void testReset() {
        InstanceLoader<String> loader = InstanceLoaders.emptyLoader();
        Supplier<List<String>> supplier = () -> List.of("X", "Y");
        loader.getRegistry().add(supplier);

        List<String> instances1 = loader.getInstances();
        Assertions.assertEquals(List.of("X", "Y"), instances1, "Initial instances should match supplier.");

        loader.reset();  //Note: Reset activated!
        loader.getRegistry().clear();
        loader.getRegistry().add(() -> List.of("Z"));

        List<String> instances2 = loader.getInstances();
        Assertions.assertEquals(Collections.singletonList("Z"), instances2, "Instances after reset should match new supplier.");
    }

    /**
     * Tests thread-safe lazy initialization.
     */
    @Test
    void testConcurrentAccess() throws InterruptedException {
        InstanceLoader<String> loader = InstanceLoaders.emptyLoader();
        Supplier<List<String>> supplier = () -> List.of("P", "Q");
        loader.getRegistry().add(supplier);

        int threadCount = 4;
        CountDownLatch latch = new CountDownLatch(threadCount);
        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            List<List<String>> results = Collections.synchronizedList(new java.util.ArrayList<>());

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    results.add(loader.getInstances());  //Note: Get instances!
                    latch.countDown();
                });
            }

            latch.await();
            executor.shutdown();

            Assertions.assertEquals(threadCount, results.size(), "All threads should have retrieved instances.");
            for (List<String> result : results) {
                Assertions.assertEquals(List.of("P", "Q"), result, "All threads should get the same instances.");
            }
        }
    }
}
