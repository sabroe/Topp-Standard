package com.yelstream.topp.standard.smallrye.config.source.factory;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.List;
import java.util.OptionalInt;

@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class ListConfigSourceFactory implements ConfigSourceFactory {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @lombok.Builder.Default
    private final OptionalInt priority=OptionalInt.empty();

    @Singular
    private final List<ConfigSource> configSources;

    @Override
    public OptionalInt getPriority() {
        return priority;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext configSourceContext) {
        return configSources;
    }
}
