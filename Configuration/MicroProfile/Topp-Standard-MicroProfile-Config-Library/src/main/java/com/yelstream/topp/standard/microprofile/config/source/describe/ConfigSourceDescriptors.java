package com.yelstream.topp.standard.microprofile.config.source.describe;

import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@UtilityClass
public class ConfigSourceDescriptors {

    public static ConfigSourceDescriptor from(ConfigSource configSource) {
        ConfigSourceDescriptor.Builder builder=ConfigSourceDescriptor.builder();
        builder.className(configSource.getClass().getName());
        builder.name(configSource.getName());
        builder.ordinal(configSource.getOrdinal());
        builder.propertyNames(configSource.getPropertyNames());
        builder.properties(configSource.getProperties());
        return builder.build();
    }

    public static List<ConfigSourceDescriptor> from(List<ConfigSource> configSources) {
        return configSources.stream().map(ConfigSourceDescriptors::from).toList();
    }

    public static String createDescription(ConfigSourceDescriptor configSourceDescriptor) {
        StringBuilder sb=new StringBuilder();
        String format="%20s: %40s";
        sb.append(String.format(format,"Class",configSourceDescriptor.getClass()));
        sb.append(String.format(format,"Name",configSourceDescriptor.getName()));
        sb.append(String.format(format,"Ordinal",configSourceDescriptor.getOrdinal()));
        sb.append(String.format(format,"Property names",configSourceDescriptor.getPropertyNames()));
        sb.append(String.format(format,"Properties",configSourceDescriptor.getProperties()));
        return sb.toString();
    }

    public static String createDescription(List<ConfigSourceDescriptor> configSources) {
        StringBuilder sb=new StringBuilder();
        configSources.forEach(configSource->{
            String configSourceDescription=createDescription(configSource);
            sb.append(configSourceDescription);
        });
        return sb.toString();
    }

    public static void logDescription(org.slf4j.Logger log,
                                      org.slf4j.event.Level level,
                                      Supplier<List<ConfigSourceDescriptor>> configSourceDescriptorsSupplier) {
        log.atLevel(level).setMessage("Configuration sources.").addArgument(()->{
            List<ConfigSourceDescriptor> configSourceDescriptors=configSourceDescriptorsSupplier.get();
            return ConfigSourceDescriptors.createDescription(configSourceDescriptors);
        }).log();
    }
}
