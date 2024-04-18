package com.yelstream.topp.standard.microprofile.config.spi;

import lombok.AllArgsConstructor;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class ProxyConfigSource implements ConfigSource {
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
