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

package com.yelstream.topp.standard.operation.comparison.facet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Test of {@link ComparatorFacet}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
class ComparatorFacetTest {

    record Person(String name, int age) {}

    private final Comparator<Person> byAge = Comparator.comparingInt(Person::age);

    private final ComparatorFacet<Person> facet = ComparatorFacet.of(byAge);

    @Test
    void min_shouldReturnSmallerValue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertEquals(a, facet.min(a, b));
    }

    @Test
    void max_shouldReturnLargerValue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertEquals(b, facet.max(a, b));
    }

    @Test
    void equals_shouldDetectEquality() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 10);

        Assertions.assertTrue(facet.equals(a, b));
    }

    @Test
    void ordering_methods_shouldWork() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(facet.lessThan(a, b));
        Assertions.assertTrue(facet.lessThanOrEqual(a, b));
        Assertions.assertTrue(facet.greaterThan(b, a));
        Assertions.assertTrue(facet.greaterThanOrEqual(b, a));
    }

    @Test
    void min_collection_shouldReturnSmallest() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        Assertions.assertEquals(10, facet.min(people).age());
    }

    @Test
    void max_collection_shouldReturnLargest() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        Assertions.assertEquals(30, facet.max(people).age());
    }

    @Test
    void sort_shouldReturnSortedList() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        List<Person> sorted = facet.sort(people);

        Assertions.assertEquals(10, sorted.get(0).age());
        Assertions.assertEquals(20, sorted.get(1).age());
        Assertions.assertEquals(30, sorted.get(2).age());
    }

    @Test
    void sortInPlace_shouldMutateList() {
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        ));

        facet.sortInPlace(people);

        Assertions.assertEquals(10, people.get(0).age());
        Assertions.assertEquals(20, people.get(1).age());
        Assertions.assertEquals(30, people.get(2).age());
    }

    @Test
    void comparator_shouldReturnUnderlyingComparator() {
        Assertions.assertSame(byAge, facet.comparator());
    }

    @Test
    void reversed_shouldInvertOrdering() {
        Comparator<Person> reversed = facet.reversed();

        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(reversed.compare(a, b) > 0);
    }
}
