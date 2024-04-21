package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;

/**
 * Static proxy for {@link ConfigSource} instances.
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
public class ProxyConfigSource implements ConfigSource {
    /**
     * Wrapped configuration-source.
     * Not open for external access.
     */
    @NonNull
    private final ConfigSource configSource;

    @Override
    public String getName() {
        return configSource.getName();
    }

    @Override
    public int getOrdinal() {
        return configSource.getOrdinal();
    }

    @Override
    public Set<String> getPropertyNames() {
        return configSource.getPropertyNames();
    }

    @Override
    public String getValue(String propertyName) {
        return configSource.getValue(propertyName);
    }

    @Override
    public Map<String,String> getProperties() {
        return configSource.getProperties();
    }
}
