package com.yelstream.topp.standard.microprofile.config;

import com.yelstream.topp.standard.microprofile.config.source.describe.ConfigSourceDescriptor;
import com.yelstream.topp.standard.microprofile.config.source.describe.ConfigSourceDescriptors;
import com.yelstream.topp.standard.util.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@UtilityClass
public class Configs {

    public static Config getConfig() {
        return ConfigProvider.getConfig();
    }

    public static Config getConfig(ClassLoader classLoader) {
        return ConfigProvider.getConfig(classLoader);
    }

    public static List<ConfigSource> getConfigSources(Config config) {
        Iterable<ConfigSource> iterable=config.getConfigSources();
        return Lists.of(iterable);
    }

    public static void logDescription(org.slf4j.Logger log,
                                      org.slf4j.event.Level level,
                                      Config config) {
        logDescription(log,level,()->getConfigSources(config));
    }

    public static void logDescription(org.slf4j.Logger log,
                                      org.slf4j.event.Level level,
                                      Supplier<List<ConfigSource>> configSourcesSupplier) {
        log.atLevel(level).setMessage("Configuration sources.").addArgument(()->{
            List<ConfigSource> configSources=configSourcesSupplier.get();
            List<ConfigSourceDescriptor> configSourceDescriptors=ConfigSourceDescriptors.from(configSources);
            return ConfigSourceDescriptors.createDescription(configSourceDescriptors);
            }).log();
    }
}
