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

package com.yelstream.topp.standard.operation.comparison.build;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

/**
 * Test of {@link ComparatorBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
class ComparatorBuilderTest {

    record Person(String firstName,
                  String lastName,
                  int age) {
    }

    @Test
    void shouldBuildNaturalOrderComparator() {
        Comparator<String> comparator =
                ComparatorBuilder.<String>naturalOrder()
                        .build();

        int result = comparator.compare("abc", "xyz");

        Assertions.assertTrue(result < 0);
    }

    @Test
    void shouldBuildComparatorUsingExtractedKeys() {
        Comparator<Person> comparator =
                ComparatorBuilder
                        .comparing(Person::lastName)
                        .thenComparing(Person::firstName)
                        .build();

        Person a = new Person("John", "Alpha", 30);
        Person b = new Person("Jane", "Beta", 25);

        int result = comparator.compare(a, b);

        Assertions.assertTrue(result < 0);
    }

    @Test
    void shouldBuildComparatorUsingPrimitiveKeys() {
        Comparator<Person> comparator =
                ComparatorBuilder
                        .comparingInt(Person::age)
                        .reversed()
                        .build();

        Person a = new Person("John", "Alpha", 30);
        Person b = new Person("Jane", "Beta", 25);

        int result = comparator.compare(a, b);

        Assertions.assertTrue(result < 0);
    }

    @Test
    void shouldBuildComparatorUsingMultipleComparisonSteps() {
        Comparator<Person> comparator =
                ComparatorBuilder
                        .comparing(Person::lastName)
                        .thenComparing(Person::firstName)
                        .thenComparingInt(Person::age)
                        .nullsLast()
                        .build();

        Person a = new Person("John", "Alpha", 30);
        Person b = new Person("John", "Alpha", 40);
        Person c = new Person("Jane", "Beta", 25);

        Assertions.assertTrue(comparator.compare(a, b) < 0);
        Assertions.assertTrue(comparator.compare(b, c) < 0);
        Assertions.assertTrue(comparator.compare(a, null) < 0);
        Assertions.assertTrue(comparator.compare(null, a) > 0);
    }
}
