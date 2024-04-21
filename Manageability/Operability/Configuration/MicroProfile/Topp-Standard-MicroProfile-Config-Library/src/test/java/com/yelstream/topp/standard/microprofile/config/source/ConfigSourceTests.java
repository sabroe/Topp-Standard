package com.yelstream.topp.standard.microprofile.config.source;

import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.junit.jupiter.api.Assertions;

@UtilityClass
public class ConfigSourceTests {

    public static void assertConfigSourceSemantics(ConfigSource configSource) {
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertNotNull(configSource.getPropertyNames());
    }
}
