package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * In-memory configuration-source keeping properties as a {@link Map}.
 * <p>
 *     Actual properties may be handed over at the time of instantiation and
 *     otherwise created, read, updated or removed during runtime.
 * </p>
 * <p>
 *     This is thread safe.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class MemoryConfigSource implements ConfigSource {
    @lombok.Builder.Default
    private final String name=UUID.randomUUID().toString();

    /**
     * Ordinal.
     */
    @lombok.Builder.Default
    private final int ordinal=DEFAULT_ORDINAL;

    /**
     * Properties held.
     * In practice, this is always an instance of {@link ConcurrentMap}.
     */
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
