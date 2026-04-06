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

package com.yelstream.topp.standard.logging.slf4j.spi.event;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility methods for {@code null}-safe handling of collections.
 * <p>
 *     Provides three strategies:
 * </p>
 * <ul>
 *   <li>Preserve: Return original instance if non-{@code null}.</li>
 *   <li>Immutable: Return unmodifiable copy.</li>
 *   <li>Mutable: Return modifiable copy.</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-06
 */
@UtilityClass
public final class NullSafe {  //TODO: Move to collection-util!
    /**
     * Returns the given list, or an empty immutable list if {@code null}.
     * <p>
     *     If the input is non-{@code null}, it is returned unchanged.
     *     The returned list may be mutable or immutable.
     * </p>
     * <p>
     *     Use {@code preserve} while building, {@code immutable} when publishing.
     * </p>
     * @param list Input list, may be {@code null}.
     * @param <T> Element type.
     * @return Original list if non-{@code null}, otherwise empty immutable list.
     */
    public static <T> List<T> preserve(List<T> list) {
        return list == null ? List.of() : list;
    }

    /**
     * Returns an immutable list based on the given list.
     * <p>
     *     If the input is {@code null}, returns an empty immutable list.
     *     If the input is non-{@code null}, returns an unmodifiable copy.
     * </p>
     * @param list Input list, may be {@code null}.
     * @param <T> Element type.
     * @return Immutable list, never {@code null}.
     * @throws NullPointerException If the input contains {@code null} elements.
     */
    public static <T> List<T> immutable(List<T> list) {
        return list == null ? List.of() : List.copyOf(list);
    }

    /**
     * Returns a mutable list based on the given list.
     * <p>
     *     If the input is {@code null}, returns a new empty mutable list.
     *     If the input is non-{@code null}, returns a mutable copy.
     * </p>
     * @param list Input list, may be {@code null}.
     * @param <T> Element type.
     * @return Mutable list, never {@code null}.
     */
    public static <T> List<T> mutable(List<T> list) {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    /**
     * Returns the given set, or an empty immutable set if {@code null}.
     * <p>
     *     If the input is non-{@code null}, it is returned unchanged.
     *     The returned set may be mutable or immutable.
     * </p>
     * @param set Input set, may be {@code null}.
     * @param <T> Element type.
     * @return Original set if non-{@code null}, otherwise empty immutable set.
     */
    public static <T> Set<T> preserve(Set<T> set) {
        return set == null ? Set.of() : set;
    }

    /**
     * Returns an immutable set based on the given set.
     * <p>
     *     If the input is {@code null}, returns an empty immutable set.
     *     If the input is non-{@code null}, returns an unmodifiable copy.
     * </p>
     * @param set Input set, may be {@code null}.
     * @param <T> Element type.
     * @return Immutable set, never {@code null}.
     * @throws NullPointerException If the input contains {@code null} elements.
     */
    public static <T> Set<T> immutable(Set<T> set) {
        return set == null ? Set.of() : Set.copyOf(set);
    }

    /**
     * Returns a mutable set based on the given set.
     * <p>
     *     If the input is {@code null}, returns a new empty mutable set.
     *     If the input is non-{@code null}, returns a mutable copy.
     * </p>
     * @param set Input set, may be {@code null}.
     * @param <T> Element type.
     * @return Mutable set, never {@code null}.
     */
    public static <T> Set<T> mutable(Set<T> set) {
        return set == null ? new HashSet<>() : new HashSet<>(set);
    }

    /**
     * Returns the given map, or an empty immutable map if {@code null}.
     * <p>
     *     If the input is non-{@code null}, it is returned unchanged.
     *     The returned map may be mutable or immutable.
     * </p>
     * @param map Input map, may be {@code null}.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Original map if non-{@code null}, otherwise empty immutable map.
     */
    public static <K, V> Map<K, V> preserve(Map<K, V> map) {
        return map == null ? Map.of() : map;
    }

    /**
     * Returns an immutable map based on the given map.
     * <p>
     *     If the input is {@code null}, returns an empty immutable map.
     *     If the input is non-{@code null}, returns an unmodifiable copy.
     * </p>
     * @param map Input map, may be {@code null}.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Immutable map, never {@code null}.
     * @throws NullPointerException If the input contains {@code null} keys or values.
     */
    public static <K, V> Map<K, V> immutable(Map<K, V> map) {
        return map == null ? Map.of() : Map.copyOf(map);
    }

    /**
     * Returns a mutable map based on the given map.
     * <p>
     *     If the input is {@code null}, returns a new empty mutable map.
     *     If the input is non-{@code null}, returns a mutable copy.
     * </p>
     * @param map Input map, may be {@code null}.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Mutable map, never {@code null}.
     */
    public static <K, V> Map<K, V> mutable(Map<K, V> map) {
        return map == null ? new HashMap<>() : new HashMap<>(map);
    }
}
