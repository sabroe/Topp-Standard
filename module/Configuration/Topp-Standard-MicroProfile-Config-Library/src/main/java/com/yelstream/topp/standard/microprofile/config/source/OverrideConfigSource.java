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

import com.yelstream.topp.standard.util.MapBuilders;
import com.yelstream.topp.standard.util.function.MemoizedIntSupplier;
import com.yelstream.topp.standard.util.stream.MapCollectors;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Overriding configuration-source.
 * <p>
 *     This may support properties whose value is {@code null} depending upon construction.
 * </p>
 * <p>
 *     This may be immutable depending upon construction.
 * </p>
 * <p>
 *     This may be thread-safe depending upon construction.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-20
 */
public class OverrideConfigSource extends ProxyConfigSource {
    /**
     * Name.
     * <p>
     *     This may be {@code null}.
     * </p>
     */
    private final Supplier<String> nameSupplier;

    /**
     * Ordinal.
     * <p>
     *    Note: If the values from this varies at runtime then the configuration-source possibly will not work.
     * </p>
     * <p>
     *     This may be {@code null}.
     * </p>
     */
    private final IntSupplier ordinalSupplier;

    /**
     * Properties held.
     */
    private final AtomicReference<Map<String,String>> propertiesReference;

//TO-DO: Add "remove properties"!

    @Override
    public String getName() {
        if (nameSupplier!=null) {
            return nameSupplier.get();
        } else {
            return super.getName();
        }
    }

    @Override
    public int getOrdinal() {
        if (ordinalSupplier!=null) {
            return ordinalSupplier.getAsInt();
        } else {
            return super.getOrdinal();
        }
    }

    @Override
    public Set<String> getPropertyNames() {
        List<Set<String>> propertyNamesList=List.of(propertiesReference.get().keySet(),super.getPropertyNames());
        return propertyNamesList.stream()
            .filter(Objects::nonNull)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public String getValue(String propertyName) {
        String value=null;
        Map<String,String> properties=propertiesReference.get();
        if (properties.containsKey(propertyName)) {
            value=properties.get(propertyName);
        } else {
            value=super.getValue(propertyName);
        }
        return value;
    }

    @Override
    public Map<String,String> getProperties() {
        List<Map<String,String>> propertiesList=List.of(propertiesReference.get(),super.getProperties());
        return propertiesList.stream()
            .filter(Objects::nonNull)
            .flatMap(map->map.entrySet().stream())
            .collect(MapCollectors.toHashMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    @SuppressWarnings("unused")
    public void replaceProperties(Map<String,String> properties) {
        propertiesReference.set(properties);
    }

    private OverrideConfigSource(ConfigSource configSource,
                                 Supplier<String> nameSupplier,
                                 IntSupplier ordinalSupplier,
                                 AtomicReference<Map<String,String>> propertiesReference) {
        super(configSource);
        this.nameSupplier=nameSupplier;
        this.ordinalSupplier=ordinalSupplier;
        this.propertiesReference=propertiesReference;
    }

    public static OverrideConfigSource of(ConfigSource configSource,
                                          Supplier<String> nameSupplier,
                                          IntSupplier ordinalSupplier,
                                          Map<String,String> properties) {
        properties=properties==null?Map.of():properties;
        AtomicReference<Map<String,String>> propertiesReference=new AtomicReference<>(properties);
        return new OverrideConfigSource(configSource,nameSupplier,ordinalSupplier,propertiesReference);
    }

    public static OverrideConfigSource of(ConfigSource configSource,
                                          String name,
                                          int ordinal,
                                          Map<String,String> properties) {
        return of(configSource,()->name,()->ordinal,properties);
    }

    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName="Builder",toBuilder=false)  //Yes, no #toBuilder() wanted!
    private static OverrideConfigSource createInstance(ConfigSource configSource,
                                                         Supplier<String> nameSupplier,
                                                         IntSupplier ordinalSupplier,
                                                         @Singular Map<String,String> properties) {
        return of(configSource,nameSupplier,ordinalSupplier,properties);
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        /**
         * Name supplier.
         * The default value is {@code null}.
         */
        private Supplier<String> nameSupplier;

        /**
         * Ordinal supplier.
         * The default value is {@code null}.
         */
        private IntSupplier ordinalSupplier;

        private Map<String,String> properties=new HashMap<>();

        public Builder name(String name) {
            return nameSupplier(()->name);
        }

        public Builder ordinal(int ordinal) {
            return ordinalSupplier(()->ordinal);
        }

        public Builder ordinalSupplierWithStrategy(IntSupplier ordinalSupplier,
                                                   MemoizedIntSupplier.Strategy strategy) {
            return ordinalSupplier(strategy.of(ordinalSupplier));
        }

        public Builder propertiesBuilder(MapBuilders.MapBuilder<String,String> mapBuilder) {
            properties=mapBuilder.build();
            return this;
        }

        @SuppressWarnings("java:S1117")
        public MapBuilders.MapBuilder<String,String> propertiesBuild() {
            MapBuilders.MapBuilder<String,String> mapBuilder=MapBuilders.mapBuilder();
            mapBuilder.consumer(properties->this.properties=properties);
            return mapBuilder;
        }
    }
}
