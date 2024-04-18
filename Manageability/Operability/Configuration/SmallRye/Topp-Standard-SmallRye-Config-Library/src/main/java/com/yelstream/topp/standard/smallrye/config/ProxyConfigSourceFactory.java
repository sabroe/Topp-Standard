package com.yelstream.topp.standard.smallrye.config;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.OptionalInt;

@AllArgsConstructor
public class ProxyConfigSourceFactory  implements ConfigSourceFactory {
    private final ConfigSourceFactory configSourceFactory;

    @Override
    public OptionalInt getPriority() {
        return configSourceFactory.getPriority();
    }

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext configSourceContext) {
        return configSourceFactory.getConfigSources(configSourceContext);
    }
}
