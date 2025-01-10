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

package com.yelstream.topp.standard.microprofile.config.source;

import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.junit.jupiter.api.Assertions;

/**
 * Utilities addressing testing of {@link ConfigSource} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-20
 */
@SuppressWarnings("java:S2187")
@UtilityClass
public class ConfigSourceTests {

    public static void verifyConfigSourceBasics(ConfigSource configSource) {
        Assertions.assertNotNull(configSource);

        verifyConfigSourceName(configSource);
        verifyConfigSourceOrdinal(configSource);
        verifyConfigSourceProperties(configSource);
    }

    public static void verifyConfigSourceName(ConfigSource configSource) {
        Assertions.assertNotNull(configSource);

        String name=configSource.getName();
        Assertions.assertNotNull(name);
        Assertions.assertFalse(name.isEmpty());

        Assertions.assertEquals(name,configSource.getName());  //First repeated query to #getName()!
        Assertions.assertEquals(name,configSource.getName());  //Second repeated query to #getName()!
        Assertions.assertEquals(name,configSource.getName());  //Third repeated query to #getName()!
    }

    public static void verifyConfigSourceOrdinal(ConfigSource configSource) {
        Assertions.assertNotNull(configSource);

        int ordinal=configSource.getOrdinal();
        Assertions.assertEquals(ordinal,configSource.getOrdinal());  //First repeated query to #getOrdinal()!
        Assertions.assertEquals(ordinal,configSource.getOrdinal());  //Second repeated query to #getOrdinal()!
        Assertions.assertEquals(ordinal,configSource.getOrdinal());  //Third repeated query to #getOrdinal()!
    }

    public static void verifyConfigSourceProperties(ConfigSource configSource) {
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertNotNull(configSource.getPropertyNames());
    }
}
