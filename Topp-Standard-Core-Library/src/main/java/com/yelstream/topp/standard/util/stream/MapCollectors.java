package com.yelstream.topp.standard.util.stream;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Utility addressing instances of {@link Collector}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-21
 */
@UtilityClass
public class MapCollectors {
    /**
     * Creates a collector to hash-map instances.
     * @param keyMapper
     * @param valueMapper
     * @return Created collector.
     * @param <T> Type of input elements to the collector reduction operation.
     * @param <K> Type of map entry key.
     * @param <V> Type of map entry value.
     */
    public static <T,K,V> Collector<T,HashMap<K,V>,HashMap<K,V>> toHashMap(Function<? super T,K> keyMapper,
                                                                           Function<T,V> valueMapper) {
        return Collector.of(
            HashMap::new,
            (kvMap,t) -> {
                K key=keyMapper.apply(t);
                V value=valueMapper.apply(t);
                if (!kvMap.containsKey(key)) {
                    kvMap.put(key,value);
                }
            },
            (kvMap,kvMap2) -> {
                kvMap2.forEach((key,value) -> {
                    if (!kvMap.containsKey(key)) {
                        kvMap.put(key,value);
                    }
                });
                return kvMap;
            },
            Function.identity(),
            Collector.Characteristics.IDENTITY_FINISH);
    }
}
