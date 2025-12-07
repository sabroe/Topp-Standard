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

package com.yelstream.topp.standard.management;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Provides operations on maps with pattern-based merging and filtering.
 * Patterns are identified using key and value predicates.
 */
/*public*/ class PatternAwareMap<K, V> {  //TODO: For inspiration to object-name operations! Not to keep! MSM, 2025-02-09!

    private final Predicate<K> keyPatternPredicate;
    private final Predicate<V> valuePatternPredicate;

    /**
     * Creates an instance with key and value predicates.
     *
     * @param keyPatternPredicate   Predicate to identify key patterns.
     * @param valuePatternPredicate Predicate to identify value patterns.
     */
    public PatternAwareMap(Predicate<K> keyPatternPredicate, Predicate<V> valuePatternPredicate) {
        this.keyPatternPredicate = keyPatternPredicate;
        this.valuePatternPredicate = valuePatternPredicate;
    }

    /**
     * Merges two maps, expanding patterns when possible.
     *
     * @param base  The base map.
     * @param other The map to merge into base.
     * @return A new merged map.
     */
    public Map<K, V> merge(Map<K, V> base, Map<K, V> other) {
        Map<K, V> result = new HashMap<>(base);

        for (Map.Entry<K, V> entry : other.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            if (result.containsKey(key)) {
                V existingValue = result.get(key);
                if (valuePatternPredicate.test(existingValue)) {
                    result.put(key, value); // Expand pattern
                }
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * Intersects two maps, keeping only exact matches.
     *
     * @param first  The first map.
     * @param second The second map.
     * @return A new map with common entries.
     */
    public Map<K, V> intersect(Map<K, V> first, Map<K, V> second) {
        return first.entrySet().stream()
                .filter(entry -> second.containsKey(entry.getKey()) && second.get(entry.getKey()).equals(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Removes keys from a map based on a predicate.
     *
     * @param map       The input map.
     * @param keyFilter The predicate to filter keys.
     * @return A new map without filtered keys.
     */
    public Map<K, V> filterKeys(Map<K, V> map, Predicate<K> keyFilter) {
        return map.entrySet().stream()
                .filter(entry -> keyFilter.test(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Removes values from a map based on a predicate.
     *
     * @param map         The input map.
     * @param valueFilter The predicate to filter values.
     * @return A new map without filtered values.
     */
    public Map<K, V> filterValues(Map<K, V> map, Predicate<V> valueFilter) {
        return map.entrySet().stream()
                .filter(entry -> valueFilter.test(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Replaces specific values with a pattern.
     *
     * @param map         The input map.
     * @param matchValue  The value to replace.
     * @param pattern     The pattern to insert.
     * @return A new map with replaced values.
     */
    public Map<K, V> generalize(Map<K, V> map, V matchValue, V pattern) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().equals(matchValue) ? pattern : e.getValue()));
    }

    /**
     * Converts patterns into fixed values where applicable.
     *
     * @param map           The input map.
     * @param fixedValues   A map of pattern keys to fixed values.
     * @return A new map with fixed values applied.
     */
    public Map<K, V> finalizePatterns(Map<K, V> map, Map<K, V> fixedValues) {
        Map<K, V> result = new HashMap<>(map);
        result.putAll(fixedValues);
        return result;
    }

    /**
     * Computes the difference between two maps.
     *
     * @param first  The first map.
     * @param second The second map.
     * @return A new map with entries in the first map but not the second.
     */
    public Map<K, V> difference(Map<K, V> first, Map<K, V> second) {
        return first.entrySet().stream()
                .filter(entry -> !second.containsKey(entry.getKey()) || !second.get(entry.getKey()).equals(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
