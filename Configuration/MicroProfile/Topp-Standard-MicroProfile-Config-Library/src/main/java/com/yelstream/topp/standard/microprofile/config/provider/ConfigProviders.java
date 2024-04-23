package com.yelstream.topp.standard.microprofile.config.provider;

import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.ConfigSourceProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ConfigProviders {

    public static void addConfigSource(ConfigSource configSource) {
//        ConfigProvider.getConfig().
/*
        ConfigProvider configProvider=null;
        Config config = ConfigProvider.getConfig();


        config.getSources().add(configSource);
*/
    }

    public static void xxx() {

/*
        Map<String, String> defaults = new HashMap<>();
        defaults.put("my.property", "default-value");
*/

/*
        ConfigProvider.getConfig()
                .getBuilder()
                .withSources(new CustomConfigSource(defaults))
                .build();
*/

    }

/*
    public static class MyConfigSourceProvider implements ConfigSourceProvider {
        @Override
        public Iterable<ConfigSource> getConfigSources(ClassLoader forClassLoader) {
            Map<String, String> defaults = new HashMap<>();
            defaults.put("my.property", "default-value");
            return Collections.singletonList(new CustomConfigSource(defaults));
        }
    }
*/

/*
    public static void yyy() {
        ConfigProviderResolver.instance()
                .registerConfig(

                        ConfigProvider.getConfig(null*/
/*forClassLoader*//*
)
            .withSources(new MyConfigSourceProvider()

            )


                    .build();
    }
*/



}
