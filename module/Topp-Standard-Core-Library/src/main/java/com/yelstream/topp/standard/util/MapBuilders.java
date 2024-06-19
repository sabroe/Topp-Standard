/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.util;

import lombok.Singular;
import lombok.experimental.UtilityClass;

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
