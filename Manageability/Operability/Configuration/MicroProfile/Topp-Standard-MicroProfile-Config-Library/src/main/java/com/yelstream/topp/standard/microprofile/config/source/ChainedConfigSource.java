package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * "Chain-of-responsibility"-like configuration-source keeping properties as a {@link Map}.
 * <p>
 *     This may be immutable depending upon the contained configuration-sources being immutable.
 * </p>
 * <p>
 *     This may be thread-safe depending upon the contained configuration-sources being thread-safe.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-20
 */
@AllArgsConstructor(staticName="of")
public class ChainedConfigSource implements ConfigSource {
    /**
     * Name.
     */
    private final String name;

    /**
     * Ordinal.
     */
    private final int ordinal;

    /**
     * Configuration-sources held.
     * This is immutable.
     */
    private final List<ConfigSource> configSources;

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
        return configSources.stream()
            .map(ConfigSource::getPropertyNames)
            .filter(Objects::nonNull)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public String getValue(String propertyName) {
        return configSources.stream()
            .map(configSource->configSource.getValue(propertyName))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Map<String,String> getProperties() {
        return configSources.stream()
            .map(ConfigSource::getProperties)
            .filter(Objects::nonNull)
            .flatMap(map->map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(v1,v2)->v1,HashMap::new));
    }

    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    private static ChainedConfigSource createInstance(String name,
                                                      int ordinal,
                                                      @Singular List<ConfigSource> configSources) {
        return of(name,ordinal,Collections.unmodifiableList(configSources));
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        private String name=ConfigSources.createName();

        private int ordinal=ConfigSources.DEFAULT_ORDINAL;
    }
}
