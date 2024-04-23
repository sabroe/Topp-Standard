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
