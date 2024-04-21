package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
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
//            .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(v1,v2)->v1,HashMap::new));
            .collect(CustomCollector.toMap2(Map.Entry::getKey,Map.Entry::getValue));
    }

    static class CustomCollector {

        public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toMap1(final Function<? super T, K> keyMapper,
                                                                         final Function<T, V> valueMapper) {
            return Collector.of(
                    HashMap::new,
                    (kvMap, t) -> {
                        kvMap.put(keyMapper.apply(t), valueMapper.apply(t));
                    },
                    (kvMap, kvMap2) -> {
                        kvMap.putAll(kvMap2);
                        return kvMap;
                    },
                    Function.identity(),
                    Collector.Characteristics.IDENTITY_FINISH);
        }


        public static <T, K, V> Collector<T, Map<K, V>, Map<K, V>> toMap2(final Function<? super T, K> keyMapper,
                                                                          final Function<T, V> valueMapper/*,
                                                                          Supplier<V> mapFactory*/) {
            return Collector.of(
                    HashMap::new,
                    (kvMap, t) -> {
                        K key = keyMapper.apply(t);
                        V value = valueMapper.apply(t);
                        if (!kvMap.containsKey(key)) {
                            kvMap.put(key, value);
                        }
                    },
                    (kvMap, kvMap2) -> {
                        kvMap2.forEach((key, value) -> {
                            if (!kvMap.containsKey(key)) {
                                kvMap.put(key, value);
                            }
                        });
                        return kvMap;
                    },
                    Function.identity(),
                    Collector.Characteristics.IDENTITY_FINISH);
        }

        public static void main(String[] args) {
            List<Map<String, String>> listOfMaps = new ArrayList<>();
            Map<String, String> map1 = new HashMap<>();
            map1.put("key1", null);
            map1.put("key2", "value2");
            Map<String, String> map2 = new HashMap<>();
            map2.put("key1", "value1");
            map2.put("key2", "value3");
            listOfMaps.add(map1);
            listOfMaps.add(map2);

            Map<String, String> mergedMap = listOfMaps.stream()
                    .flatMap(map -> map.entrySet().stream())
                    .collect(toMap2(Map.Entry::getKey, Map.Entry::getValue));

            System.out.println(mergedMap);
        }
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
