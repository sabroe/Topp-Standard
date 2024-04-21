package com.yelstream.topp.standard.microprofile.config;

import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;

@UtilityClass
public class Configs {

    public static ConfigSource xx() {
        return null;
    }

    public static void inspectConfigSources(Config config) {
        Iterable<ConfigSource> configSources = config.getConfigSources();
        for (ConfigSource source : configSources) {
            // Examine the properties or other details of each ConfigSource
            System.out.println("ConfigSource name: " + source.getName());
            // Add more inspection logic as needed
        }
    }

/*
    public static void modifyConfig(Config config) {
        // Update a configuration property
        // This change will override any value provided by the ConfigSource
        configsetValue("my.property", "new value");
    }
*/

}
