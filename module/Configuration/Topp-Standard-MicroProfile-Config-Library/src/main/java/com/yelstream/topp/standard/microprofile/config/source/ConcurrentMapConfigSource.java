/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

import lombok.AccessLevel;
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
 *     This supports properties whose value is {@code null}.
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
@AllArgsConstructor(access= AccessLevel.PRIVATE)
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
     * <p>
     *     Note that this {@link ConcurrentMap}, together with {@link ConcurrentHashMap}, does not carry {@code null} values.
     * </p>
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

    public static ConcurrentMapConfigSource of(String name,
                                               int ordinal,
                                               ConcurrentMap<String,String> properties) {
        properties=properties==null?new ConcurrentHashMap<>():properties;
        return new ConcurrentMapConfigSource(name,ordinal,properties);
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
