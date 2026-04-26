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

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test of {@link ComparatorBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
class ComparingTest {

    record Person(String name,
                  int age,
                  double score) {
    }
    private final Person a = new Person("Alice", 30, 88.5);
    private final Person b = new Person("Bob", 40, 92.0);

    @Test
    void by_shouldCreateComparatorByStringKey() {
        Comparator<Person> cmp =
                Comparing.by(Person::name)
                        .build();

        assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void by_shouldOrderAlphabetically() {
        Comparator<Person> cmp =
                Comparing.by(Person::name)
                        .build();

        assertEquals(0, cmp.compare(a, new Person("Alice", 99, 0)));
    }

    @Test
    void byInt_shouldCompareAges() {
        Comparator<Person> cmp =
                Comparing.byInt(Person::age)
                        .build();

        assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void byDouble_shouldCompareScore() {
        Comparator<Person> cmp =
                Comparing.byDouble(Person::score)
                        .build();

        assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void chaining_shouldRespectPrimaryThenSecondaryKey() {
        Comparator<Person> cmp =
                Comparing.by(Person::name)
                        .thenComparing(Person::age)
                        .build();

        Person p1 = new Person("Alice", 50, 0);
        Person p2 = new Person("Alice", 20, 0);

        assertTrue(cmp.compare(p2, p1) < 0);
    }

    @Test
    void builder_shouldProduceValidComparator() {
        Comparator<Person> cmp =
                Comparing.by(Person::name)
                        .thenComparing(Person::age)
                        .thenComparing(Person::score)
                        .build();

        assertNotNull(cmp);

        assertTrue(cmp.compare(
                new Person("A", 1, 1.0),
                new Person("B", 1, 1.0)
        ) < 0);
    }
}
