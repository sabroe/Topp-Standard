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

package com.yelstream.topp.standard.net.resource.identification.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.net.URI;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Structured query string.
 * <p>
 *     This preserves ordering.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-09
 */
@ToString
@Getter
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class MappedQuery {
    /**
     * Query parameters.
     */
    private final List<Map.Entry<String, String>> entries;

    /**
     * Retrieves the first value for a given key.
     * @param key The parameter key.
     * @return The first value associated with the key.
     * @throws IllegalArgumentException If the key has no values.
     */
    public String value(String key) {
        return entries.stream()
                .filter(entry -> entry.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No value found for key: " + key));
    }

    /**
     * Retrieves all values for a given key.
     * @param key The parameter key.
     * @return An unmodifiable list of values for the key, or empty list if none.
     */
    public List<String> values(String key) {
        List<String> values = entries.stream()
                .filter(entry -> entry.getKey().equals(key))
                .map(Map.Entry::getValue)
                .toList();
        return Collections.unmodifiableList(values);
    }

    /**
     * Converts the query parameters to a query string.
     * @return The formatted query string, or null if empty.
     */
    public String toQuery() {
        return formatToQuery(entries);
    }

    /**
     * Creates a {@code MappedQuery} from a query string.
     * @param query The query string to parse.
     * @return A new {@code MappedQuery} instance.
     */
    public static MappedQuery of(String query) {
        return builder().entries(parseToEntries(query)).build();
    }

    /**
     * Creates a {@code MappedQuery} from a URI's query component.
     * @param uri The URI containing the query.
     * @return A new {@code MappedQuery} instance.
     * @throws NullPointerException If the URI is null.
     */
    public static MappedQuery of(URI uri) {
        Objects.requireNonNull(uri, "URI must not be null");
        return of(uri.getQuery());
    }

    /**
     * Parses a query string into a list of key-value pairs.
     * @param query The query string to parse.
     * @return A list of key-value pairs.
     */
    private static List<Map.Entry<String, String>> parseToEntries(String query) {
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            for (String pair : query.split("&")) {
                int idx = pair.indexOf("=");
                if (idx != -1) {
                    String key = pair.substring(0, idx);
                    String value = pair.substring(idx + 1);
                    entries.add(new AbstractMap.SimpleImmutableEntry<>(key, value));
                }
            }
        }
        return entries;
    }

    /**
     * Formats a list of key-value pairs into a query string.
     * @param entries The list of key-value pairs to format.
     * @return The formatted query string, or null if empty.
     */
    private static String formatToQuery(List<Map.Entry<String, String>> entries) {
        if (entries == null || entries.isEmpty()) {
            return null;
        }
        return entries.stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
    }

    /**
     * Holds a value with an associated priority for sorting.
     */
    @ToString
    @Getter
    @AllArgsConstructor
    private static class PriorityValue {
        /**
         * The priority of the value (lower is higher priority).
         */
        private final int priority;

        /**
         * The value string.
         */
        private final String value;
    }

    /**
     * Builder.
     */
    public static class Builder {
        private final Map<String, List<PriorityValue>> multiMap = new HashMap<>();
        private int nextPriority = 0;

        /**
         * Adds a single key-value pair to the builder with the next priority.
         * @param key   The parameter key.
         * @param value The parameter value.
         * @return This builder instance.
         */
        public Builder add(String key, String value) {
            multiMap.computeIfAbsent(key, k -> new ArrayList<>()).add(new PriorityValue(nextPriority, value));
            nextPriority += 10;
            return this;
        }

        /**
         * Adds multiple values for a single key to the builder with sequential priorities.
         * @param key    The parameter key.
         * @param values The list of parameter values.
         * @return This builder instance.
         */
        public Builder add(String key, List<String> values) {
            if (values != null) {
                List<PriorityValue> priorityValues = multiMap.computeIfAbsent(key, k -> new ArrayList<>());
                for (String value : values) {
                    priorityValues.add(new PriorityValue(nextPriority, value));
                    nextPriority += 10;
                }
            }
            return this;
        }

        /**
         * Sets the query parameters from a list of key-value pairs.
         * @param entries The list of key-value pairs to set.
         * @return This builder instance.
         */
        public Builder entries(List<Map.Entry<String, String>> entries) {
            if (entries != null) {
                this.multiMap.clear();
                this.nextPriority = 0;
                for (Map.Entry<String, String> entry : entries) {
                    multiMap.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                            .add(new PriorityValue(nextPriority, entry.getValue()));
                    nextPriority += 10;
                }
            }
            return this;
        }

        /**
         * Builds a new {@code MappedQuery} instance, sorting values by priority.
         * @return The constructed {@code MappedQuery}.
         */
        public MappedQuery build() {
            List<Map.Entry<String, String>> entries = new ArrayList<>();
            List<Map.Entry<String, PriorityValue>> priorityEntries = new ArrayList<>();
            multiMap.forEach((key, values) -> {
                values.forEach(pv -> priorityEntries.add(new AbstractMap.SimpleImmutableEntry<>(key, pv)));
            });
            priorityEntries.sort(Comparator.comparingInt(e -> e.getValue().getPriority()));
            priorityEntries.forEach(e -> entries.add(new AbstractMap.SimpleImmutableEntry<>(e.getKey(), e.getValue().getValue())));
            return new MappedQuery(Collections.unmodifiableList(entries));
        }
    }
}
