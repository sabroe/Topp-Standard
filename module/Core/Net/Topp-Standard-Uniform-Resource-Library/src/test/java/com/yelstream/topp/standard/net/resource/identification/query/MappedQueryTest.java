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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.net.URI;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Tests for the {@link MappedQuery} class.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-18
 */
class MappedQueryTest {

    @Test
    void testBuilderConstructionAndReadout() {
        MappedQuery query = MappedQuery.builder()
                .add("name", "John")
                .add("age", "30")
                .add("name", "Jane")
                .build();

        // Verify entries
        List<Map.Entry<String, String>> entries = query.getEntries();
        Assertions.assertEquals(3, entries.size(), "Entry list size should be 3");
        Assertions.assertEquals("name", entries.get(0).getKey(), "First key should be 'name'");
        Assertions.assertEquals("John", entries.get(0).getValue(), "First value should be 'John'");
        Assertions.assertEquals("age", entries.get(1).getKey(), "Third key should be 'age'");
        Assertions.assertEquals("30", entries.get(1).getValue(), "Third value should be '30'");
        Assertions.assertEquals("name", entries.get(2).getKey(), "Second key should be 'name'");
        Assertions.assertEquals("Jane", entries.get(2).getValue(), "Second value should be 'Jane'");

        // Verify value and values
        Assertions.assertEquals("John", query.value("name"), "First value for 'name' should be 'John'");
        Assertions.assertEquals(List.of("John", "Jane"), query.values("name"), "Values for 'name' should be [John, Jane]");
        Assertions.assertEquals("30", query.value("age"), "Value for 'age' should be '30'");
        Assertions.assertEquals(List.of("30"), query.values("age"), "Values for 'age' should be [30]");

        // Verify query string
        Assertions.assertEquals("name=John&age=30&name=Jane", query.toQuery(), "Query string should match input order");
    }

    @Test
    void testOfStringConstruction() {
        MappedQuery query = MappedQuery.of("name=John&age=30&name=Jane");

        // Verify entries
        List<Map.Entry<String, String>> entries = query.getEntries();
        Assertions.assertEquals(3, entries.size(), "Entry list size should be 3");
        Assertions.assertEquals("name", entries.get(0).getKey(), "First key should be 'name'");
        Assertions.assertEquals("John", entries.get(0).getValue(), "First value should be 'John'");
        Assertions.assertEquals("age", entries.get(1).getKey(), "Second key should be 'age'");
        Assertions.assertEquals("30", entries.get(1).getValue(), "Second value should be '30'");
        Assertions.assertEquals("name", entries.get(2).getKey(), "Third key should be 'name'");
        Assertions.assertEquals("Jane", entries.get(2).getValue(), "Third value should be 'Jane'");

        // Verify value and values
        Assertions.assertEquals("John", query.value("name"), "First value for 'name' should be 'John'");
        Assertions.assertEquals(List.of("John", "Jane"), query.values("name"), "Values for 'name' should be [John, Jane]");
        Assertions.assertEquals("30", query.value("age"), "Value for 'age' should be '30'");

        // Verify query string
        Assertions.assertEquals("name=John&age=30&name=Jane", query.toQuery(), "Query string should match input");
    }

    @Test
    void testOfUriConstruction() {
        URI uri = URI.create("http://example.com?name=John&age=30&name=Jane");
        MappedQuery query = MappedQuery.of(uri);

        // Verify entries
        List<Map.Entry<String, String>> entries = query.getEntries();
        Assertions.assertEquals(3, entries.size(), "Entry list size should be 3");
        Assertions.assertEquals("name", entries.get(0).getKey(), "First key should be 'name'");
        Assertions.assertEquals("John", entries.get(0).getValue(), "First value should be 'John'");
        Assertions.assertEquals("age", entries.get(1).getKey(), "Second key should be 'age'");
        Assertions.assertEquals("30", entries.get(1).getValue(), "Second value should be '30'");
        Assertions.assertEquals("name", entries.get(2).getKey(), "Third key should be 'name'");
        Assertions.assertEquals("Jane", entries.get(2).getValue(), "Third value should be 'Jane'");

        // Verify query string
        Assertions.assertEquals("name=John&age=30&name=Jane", query.toQuery(), "Query string should match URI query");
    }

    @Test
    void testEmptyQuery() {
        MappedQuery query = MappedQuery.of("");
        Assertions.assertTrue(query.getEntries().isEmpty(), "Empty query should have no entries");
        Assertions.assertNull(query.toQuery(), "Empty query should return null");
        Assertions.assertThrows(IllegalArgumentException.class, () -> query.value("name"), "value() should throw for missing key");
        Assertions.assertEquals(List.of(), query.values("name"), "values() should return empty list for missing key");
    }

    @Test
    void testImmutability() {
        MappedQuery query = MappedQuery.builder()
                .add("name", "John")
                .build();
        List<Map.Entry<String, String>> entries = query.getEntries();
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> entries.add(new AbstractMap.SimpleEntry<>("test", "value")),
                "Entries list should be unmodifiable");
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> query.values("name").add("Jane"),
                "Values list should be unmodifiable");
    }

    @Test
    void testPriorityOrderInBuilder() {
        MappedQuery query = MappedQuery.builder()
                .add("name", "John")  // Priority 0
                .add("name", "Jane")  // Priority 10
                .add("name", "Alice") // Priority 20
                .add("age", "30")     // Priority 30
                .build();

        // Verify entries (keys sorted alphabetically, values by priority)
        List<Map.Entry<String, String>> entries = query.getEntries();
        Assertions.assertEquals(4, entries.size(), "Entry list size should be 4");
        Assertions.assertEquals("name", entries.get(0).getKey(), "Second key should be 'name'");
        Assertions.assertEquals("John", entries.get(0).getValue(), "Second value should be 'John'");
        Assertions.assertEquals("name", entries.get(1).getKey(), "Third key should be 'name'");
        Assertions.assertEquals("Jane", entries.get(1).getValue(), "Third value should be 'Jane'");
        Assertions.assertEquals("name", entries.get(2).getKey(), "Fourth key should be 'name'");
        Assertions.assertEquals("Alice", entries.get(2).getValue(), "Fourth value should be 'Alice'");
        Assertions.assertEquals("age", entries.get(3).getKey(), "First key should be 'age'");
        Assertions.assertEquals("30", entries.get(3).getValue(), "First value should be '30'");

        // Verify query string
        Assertions.assertEquals("name=John&name=Jane&name=Alice&age=30", query.toQuery(), "Query string should reflect priority order");
    }

    @Test
    void testNullUri() {
        Assertions.assertThrows(NullPointerException.class,
                () -> MappedQuery.of((URI) null),
                "of(URI) should throw NullPointerException for null URI");
    }
}