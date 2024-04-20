package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Concurrent, in-memory configuration-source keeping properties as a {@link Map}.
 * <p>
 *     All values are set and locked in the most conservative manner possible.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 * <p>
 *     This is thread safe.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-20
 */
@AllArgsConstructor(staticName="of")
public class ConcurrentMapConfigSource implements ConfigSource {
    /**
     * Name.
     */
    private final String name;

    /**
     * Ordinal.
     */
    private final int ordinal;

    /**
     * Properties held.
     */
    private final ConcurrentMap<String,String> properties;

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

    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    private static ConcurrentMapConfigSource createInstance(String name,
                                                            int ordinal,
                                                            @Singular Map<String,String> properties) {
        return of(name,ordinal,new ConcurrentHashMap<>(properties));
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        private String name=ConfigSources.createName();

        private int ordinal=ConfigSources.DEFAULT_ORDINAL;

        private Map<String,String> properties=new HashMap<>();
    }
}
