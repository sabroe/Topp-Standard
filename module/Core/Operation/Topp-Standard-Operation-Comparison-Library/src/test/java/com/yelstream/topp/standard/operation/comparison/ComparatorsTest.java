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

package com.yelstream.topp.standard.operation.comparison;

import com.yelstream.topp.standard.operation.comparison.policy.StandardNullPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Test of {@link Comparators}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
class ComparatorsTest {
    @Test
    void nullSafeLast_natural_shouldPlaceNullLast() {
        Comparator<Integer> c = Comparators.nullSafeLast();

        Assertions.assertTrue(c.compare(1, 2) < 0);
        Assertions.assertTrue(c.compare(null, 1) > 0);
    }

    @Test
    void nullSafeFirst_natural_shouldPlaceNullFirst() {
        Comparator<Integer> c = Comparators.nullSafeFirst();

        Assertions.assertTrue(c.compare(1, 2) < 0);
        Assertions.assertTrue(c.compare(null, 1) < 0);
    }

    @Test
    void nullSafeLast_customComparator_shouldPlaceNullLast() {
        Comparator<Integer> base = Comparator.reverseOrder();
        Comparator<Integer> c = Comparators.nullSafeLast(base);

        Assertions.assertTrue(c.compare(2, 1) < 0);
        Assertions.assertTrue(c.compare(null, 1) > 0);
    }

    @Test
    void nullSafeFirst_customComparator_shouldPlaceNullFirst() {
        Comparator<Integer> base = Comparator.reverseOrder();
        Comparator<Integer> c = Comparators.nullSafeFirst(base);

        Assertions.assertTrue(c.compare(2, 1) < 0);
        Assertions.assertTrue(c.compare(null, 1) < 0);
    }

    record Person(String name, int age) {}

    private final Comparator<Person> byAge =
            Comparator.comparingInt(Person::age);

    @Test
    void min_shouldReturnSmallerValue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Person result = Comparators.min(byAge, a, b);

        Assertions.assertEquals(a, result);
    }

    @Test
    void max_shouldReturnLargerValue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Person result = Comparators.max(byAge, a, b);

        Assertions.assertEquals(b, result);
    }

    @Test
    void min_shouldReturnSmallestFromCollection() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        Person result = Comparators.min(byAge, people);

        Assertions.assertEquals(10, result.age());
    }

    @Test
    void max_shouldReturnLargestFromCollection() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        Person result = Comparators.max(byAge, people);

        Assertions.assertEquals(30, result.age());
    }

    @Test
    void min_shouldReturnNullForEmptyCollection() {
        List<Person> people = List.of();

        Person result = Comparators.min(byAge, people);

        Assertions.assertNull(result);
    }

    @Test
    void equals_shouldReturnTrueForEqualValues() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 10);

        boolean result = Comparators.equals(byAge, a, b);

        Assertions.assertTrue(result);
    }

    @Test
    void equalsNullSafe_should_handle_null_and_non_null_values() {

        Comparator<String> cmp = Comparator.naturalOrder();

        Assertions.assertTrue(Comparators.equalsNullSafe(cmp, "A", "A"));
        Assertions.assertFalse(Comparators.equalsNullSafe(cmp, "A", "B"));

        Assertions.assertTrue(Comparators.equalsNullSafe(cmp, null, null));
        Assertions.assertFalse(Comparators.equalsNullSafe(cmp, null, "A"));
        Assertions.assertFalse(Comparators.equalsNullSafe(cmp, "A", null));
    }

    @Test
    void lessThan_shouldReturnTrue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(Comparators.lessThan(byAge, a, b));
    }

    @Test
    void greaterThan_shouldReturnTrue() {
        Person a = new Person("A", 30);
        Person b = new Person("B", 20);

        Assertions.assertTrue(Comparators.greaterThan(byAge, a, b));
    }

    @Test
    void sort_shouldReturnSortedList() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        List<Person> sorted = Comparators.sort(byAge, people);

        Assertions.assertEquals(10, sorted.get(0).age());
        Assertions.assertEquals(20, sorted.get(1).age());
        Assertions.assertEquals(30, sorted.get(2).age());
    }

    @Test
    void sortInPlace_shouldSortListMutably() {
        List<Person> people = Arrays.asList(
                new Person("A", 30),
                new Person("B", 10),
                new Person("C", 20)
        );

        Comparators.sortInPlace(byAge, people);

        Assertions.assertEquals(10, people.get(0).age());
        Assertions.assertEquals(20, people.get(1).age());
        Assertions.assertEquals(30, people.get(2).age());
    }

    @Test
    void reversed_shouldInvertOrdering() {
        Comparator<Person> reversed = Comparators.reversed(byAge);

        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(reversed.compare(a, b) > 0);
    }

    @Test
    void applyNullPolicy_nullsLast_behavesCorrectly() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.applyNullPolicy(base, StandardNullPolicy.NullsLast.getPolicy());

        Assertions.assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void applyNullPolicy_nullsFirst_behavesCorrectly() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.applyNullPolicy(base, StandardNullPolicy.NullsFirst.getPolicy());

        Assertions.assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void applyNullPolicy_strict_throwsOnNull() {
        Person a = new Person("A", 10);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.applyNullPolicy(base, StandardNullPolicy.Strict.getPolicy());

        Assertions.assertThrows(NullPointerException.class, () -> cmp.compare(null, a));
    }

    @Test
    void nullsFirst_delegatesCorrectly() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.nullsFirst(base);

        Assertions.assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void nullsLast_delegatesCorrectly() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.nullsLast(base);

        Assertions.assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void strict_delegatesCorrectly() {
        Person a = new Person("A", 10);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.strict(base);

        Assertions.assertThrows(NullPointerException.class, () -> cmp.compare(null, a));
    }

    @Test
    void nullsFirst_placesNullBeforeValue() {
        Person a = new Person("A", 10);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.nullsFirst(base);

        Assertions.assertTrue(cmp.compare(null, a) < 0);
    }

    @Test
    void nullsLast_placesNullAfterValue() {
        Person a = new Person("A", 10);

        Comparator<Person> base = Comparator.comparing(Person::age);

        Comparator<Person> cmp = Comparators.nullsLast(base);

        Assertions.assertTrue(cmp.compare(null, a) > 0);
    }
}
