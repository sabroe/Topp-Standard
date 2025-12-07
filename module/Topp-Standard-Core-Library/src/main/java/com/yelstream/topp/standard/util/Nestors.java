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

package com.yelstream.topp.standard.util;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@UtilityClass
public class Nestors {

/*
    @AllArgsConstructor
    public static abstract class AbstractBuilder<X,Z> {
        private final X x;
        private final Consumer<Z> consumer;

        public abstract Z build();

        public X commit() {
            Z map=build();
            consumer.accept(map);
            return x;
        }
    }
*/

    public static <K,V> MapBuilder<Void,K,V> map() {
        return MapBuilder.of(null,null);
    }

    public static <V> ListBuilder<Void,V> list() {
        return ListBuilder.of(null,null);
    }

    @ToString(onlyExplicitlyIncluded=true)
    @RequiredArgsConstructor(staticName="of")
    @AllArgsConstructor(staticName="of")
    public static class MapBuilder<X,K,V> {
        private final X x;
        private final Consumer<Map<K,V>> consumer;

        @ToString.Include
        private Map<K,V> entries=new HashMap<>();

        public Map<K,V> build() {
            return entries;
        }

        public X commit() {
            Map<K,V> result=build();
            if (consumer!=null) {
                consumer.accept(result);
            }
            return x;
        }

        public MapBuilder<X,K,V> entries(Map<K,V> entries) {
            this.entries.putAll(entries);
            return this;
        }

        public MapBuilder<X,K,V> entry(K key,
                                       V value) {
            this.entries.put(key,value);
            return this;
        }

        public MapBuilder<X,K,V> entry(Map.Entry<K,V> entry) {
            //entries.put
            return this;
        }

        public MapBuilder<X,K,V> clearEntries() {
            this.entries.clear();
            return this;
        }



    }

    @ToString(onlyExplicitlyIncluded=true)
    @RequiredArgsConstructor(staticName="of")
    @AllArgsConstructor(staticName="of")
    public static class ListBuilder<X,V> {
        private final X x;
        private final Consumer<List<V>> consumer;

        @ToString.Include
        private List<V> elements=new ArrayList<>();

        public List<V> build() {
            return elements;
        }

        public X commit() {
            List<V> result=build();
            if (consumer!=null) {
                consumer.accept(result);
            }
            return x;
        }

        public ListBuilder<X,V> elements(Collection<V> elements) {
            this.elements.addAll(elements);
            return this;
        }

        public ListBuilder<X,V> element(V element) {
            this.elements.add(element);
            return this;
        }


        public ListBuilder<X,V> clearElements() {
            this.elements.clear();
            return this;
        }
    }
}
