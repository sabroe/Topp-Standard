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

    public static void assertConfigSourceSemantics(ConfigSource configSource) {
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertNotNull(configSource.getPropertyNames());
    }
}
