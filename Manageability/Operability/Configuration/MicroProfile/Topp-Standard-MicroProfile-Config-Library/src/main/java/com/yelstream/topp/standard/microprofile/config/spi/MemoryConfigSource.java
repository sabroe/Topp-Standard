package com.yelstream.topp.standard.microprofile.config.spi;

import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class MemoryConfigSource implements ConfigSource {
    @lombok.Builder.Default
    private String name=UUID.randomUUID().toString();

    @lombok.Builder.Default
    private int ordinal=DEFAULT_ORDINAL;

    @Singular
    private final Map<String,String> properties;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public Map<String,String> getProperties() {
        return properties;
    }

    public static class Builder {
        private final ConcurrentMap<String,String> properties=new ConcurrentHashMap<>();
    }
    //TO-DO: Test methods properties(Map), clearProperties().
}
