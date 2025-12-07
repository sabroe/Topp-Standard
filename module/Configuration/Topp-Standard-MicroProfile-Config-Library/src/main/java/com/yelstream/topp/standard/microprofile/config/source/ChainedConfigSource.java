/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.microprofile.config.source;

import com.yelstream.topp.standard.util.stream.MapCollectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Collections;
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
@AllArgsConstructor(access=AccessLevel.PRIVATE)
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
    @NonNull
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
            .filter(Objects::nonNull)
            .map(ConfigSource::getPropertyNames)
            .filter(Objects::nonNull)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public String getValue(String propertyName) {
        return configSources.stream()
            .filter(Objects::nonNull)
            .filter(configSource->configSource.getPropertyNames().contains(propertyName))
            .findFirst()
            .map(configSource->configSource.getValue(propertyName))
            .orElse(null);
    }

    @Override
    public Map<String,String> getProperties() {
        return configSources.stream()
            .filter(Objects::nonNull)
            .map(ConfigSource::getProperties)
            .filter(Objects::nonNull)
            .flatMap(map->map.entrySet().stream())
            .collect(MapCollectors.toHashMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public static ChainedConfigSource of(String name,
                                         int ordinal,
                                         List<ConfigSource> configSources) {
        configSources=configSources==null?List.of():configSources;
        configSources=Collections.unmodifiableList(configSources);
        return new ChainedConfigSource(name,ordinal,configSources);
    }

    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    private static ChainedConfigSource createInstance(String name,
                                                      int ordinal,
                                                      @Singular List<ConfigSource> configSources) {
        return of(name,ordinal,configSources);
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        private String name=ConfigSources.createName();

        private int ordinal=ConfigSources.DEFAULT_ORDINAL;
    }
}
