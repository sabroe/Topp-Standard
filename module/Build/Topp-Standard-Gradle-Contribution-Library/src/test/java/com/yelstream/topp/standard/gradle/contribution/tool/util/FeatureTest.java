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

package com.yelstream.topp.standard.gradle.contribution.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Tests {@link Configuration.Feature}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-11-24
 */
class FeatureTest {

    @Test
    void usageWithBuilderAnd() {
        Map<String,Object> properties=Map.of("feature.slf4j.enable","");

        Configuration.Feature feature=
            Configuration.Feature.builder().enable(properties.get("feature.slf4j.enable")).enable(true).build();
        boolean enabled=feature.isEnabled();

        Assertions.assertTrue(enabled);
    }

    @Test
    void usageWithBuilderAndDefaultAtTest() {
        Map<String,Object> properties=Map.of("feature.slf4j.enable","");

        Configuration.Feature feature=
            Configuration.Feature.builder().enable(properties.get("feature.slf4j.enable")).build();
        boolean enabled=feature.isEnabled(true);

        Assertions.assertTrue(enabled);
    }

    @Test
    void usageWithDefaultAtCreation() {
        Map<String,Object> properties=Map.of("feature.slf4j.enable","");

        boolean enabled=
            Configuration.feature(true).enable(properties.get("feature.slf4j.enable")).isEnabled();

        Assertions.assertTrue(enabled);
    }

    @Test
    void usageWithDefaultAtTest() {
        Map<String,Object> properties=Map.of("feature.slf4j.enable","");

        boolean enabled=
            Configuration.feature().enable(properties.get("feature.slf4j.enable")).isEnabled(true);

        Assertions.assertTrue(enabled);
    }
}
