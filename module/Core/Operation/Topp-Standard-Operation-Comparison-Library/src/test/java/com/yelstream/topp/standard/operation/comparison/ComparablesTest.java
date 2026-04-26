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

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;

/**
 * Test of {@link Comparables}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
class ComparablesTest {
    /**
     * Simple domain object with natural ordering by age.
     */
    public record Person(String name, int age) implements Comparable<Person> {

        /**
         * Natural ordering is defined by age.
         *
         * @param o Other person.
         * @return Comparison result based on age.
         */
        @Override
        public int compareTo(@NonNull Person o) {
            Objects.requireNonNull(o, "o");
            return Integer.compare(this.age, o.age);
        }
    }


    @Test
    void min_shouldReturnSmaller() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertEquals(a, Comparables.min(a, b));
    }

    @Test
    void max_shouldReturnLarger() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertEquals(b, Comparables.max(a, b));
    }

    @Test
    void minNullLast_shouldHandleNulls() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertEquals(b, Comparables.minNullLast(null, b));
        Assertions.assertEquals(a, Comparables.minNullLast(a, null));
    }

    @Test
    void maxNullFirst_shouldHandleNulls() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertEquals(b, Comparables.maxNullFirst(null, b));
        Assertions.assertEquals(a, Comparables.maxNullFirst(a, null));
    }

    @Test
    void equals_shouldDetectEquality() {
        Person a = new Person("A", 10);

        Assertions.assertTrue(Comparables.equals(a, new Person("X", 10)));
    }

    @Test
    void equalsNullSafe_shouldHandleNullsAndValues() {
        String a = "A";
        String b = "A";
        String c = "B";

        Assertions.assertTrue(Comparables.equalsNullSafe(a, b));
        Assertions.assertFalse(Comparables.equalsNullSafe(a, c));

        Assertions.assertTrue(Comparables.equalsNullSafe(null, null));
        Assertions.assertFalse(Comparables.equalsNullSafe(null, a));
        Assertions.assertFalse(Comparables.equalsNullSafe(a, null));
    }
    @Test
    void lessThan_shouldBeTrue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(Comparables.lessThan(a, b));
    }

    @Test
    void greaterThan_shouldBeTrue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(Comparables.greaterThan(b, a));
    }

    @Test
    void lessThanOrEqual_shouldWork() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(Comparables.lessThanOrEqual(a, a));
        Assertions.assertTrue(Comparables.lessThanOrEqual(a, b));
    }

    @Test
    void greaterThanOrEqual_shouldWork() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Assertions.assertTrue(Comparables.greaterThanOrEqual(b, b));
        Assertions.assertTrue(Comparables.greaterThanOrEqual(b, a));
    }

    @Test
    void median_shouldReturnMiddleValue() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);
        Person c = new Person("C", 15);

        Assertions.assertEquals(c, Comparables.median(a, b, c));
    }

    @Test
    void naturalComparator_shouldRespectOrdering() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Comparator<Person> cmp = Comparables.naturalComparator();

        Assertions.assertTrue(cmp.compare(a, b) < 0);
    }

    @Test
    void reverseComparator_shouldInvertOrdering() {
        Person a = new Person("A", 10);
        Person b = new Person("B", 20);

        Comparator<Person> cmp = Comparables.reverseComparator();

        Assertions.assertTrue(cmp.compare(a, b) > 0);
    }

    @Test
    void strict_min_shouldThrowOnNull() {
        Person b = new Person("B", 20);

        Assertions.assertThrows(NullPointerException.class,
                () -> Comparables.min(null, b));
    }

    @Test
    void strict_equals_shouldThrowOnNull() {
        Person a = new Person("A", 10);

        Assertions.assertThrows(NullPointerException.class,
                () -> Comparables.equals(a, null));
    }
}
