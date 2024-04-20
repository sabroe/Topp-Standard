package com.yelstream.topp.standard.util;

import lombok.Singular;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@UtilityClass
public class MapBuilders {

    /*
     *
     * ConcurrentMap:
     *   -- thread-safe
     *   -- atomic
     *   ConcurrentHashMap
     *     --hashed
     *   ConcurrentSkipListMap
     *
     *
     *
     */

    @lombok.Builder(builderClassName="MapBuilder",builderMethodName="mapBuilder")
    private static <K,V> Map<K,V> createMap(Map<K,V> values,
                                            //Boolean immutable,
                                            //Boolean threadSafe,
                                            //Boolean concurrent,
                                            //Boolean sorted,
                                            //Boolean serializable,
                                            //Boolean unmodifiable,
                                            Consumer<Map<K,V>> consumer) {
        Map<K,V> map=new HashMap<>();
        if (consumer!=null) {
            consumer.accept(map);
        }
        return map;
    }

/*
    public static class MapBuilder<K,V> {

    }
*/

    @lombok.Builder(builderClassName="ConcurrentMapBuilder",builderMethodName="concurrentMapBuilder")
    private static <K,V> ConcurrentMap<K,V> createConcurrentMap(@Singular Map<K,V> values,
                                                                //Boolean immutable,
                                                                //Boolean threadSafe,
                                                                //Boolean concurrent,
                                                                //Boolean sorted,
                                                                //Boolean serializable,
                                                                //Boolean unmodifiable,
                                                                Consumer<ConcurrentMap<K,V>> consumer) {
        ConcurrentMap<K,V> map=new ConcurrentHashMap<>();
        if (consumer!=null) {
            consumer.accept(map);
        }
        return map;
    }

    public static class ConcurrentMapBuilder<K,V> {
        private final Map<K,V> initial=new HashMap<>();

/*
        public ConcurrentMap<K,V> build() {
            return new ConcurrentHashMap<>(initial);
        }
*/
    }
}
