package com.yelstream.topp.standard.smallrye.config.source.factory;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.OptionalInt;

/**
 * Static proxy for {@link ConfigSourceFactory} instances.
 * <p>
 *     The primary, intended usage is to be able to set up non-trivial configuration-sources within
 *     a specific sub-class of this proxy having a default constructor and hence applicable
 *     for a {@link java.util.ServiceLoader}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
@AllArgsConstructor
public class ProxyConfigSourceFactory implements ConfigSourceFactory {
    /**
     * Wrapped configuration-source factory.
     * Not open for external access.
     */
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
