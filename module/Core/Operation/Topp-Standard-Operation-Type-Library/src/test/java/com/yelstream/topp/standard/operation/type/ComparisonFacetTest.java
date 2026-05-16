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

package com.yelstream.topp.standard.operation.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ComparisonFacetTest {

    // ------------------------------------------------------------
    // compareTo
    // ------------------------------------------------------------

    @Test
    void compareTo_shouldReturnNegative_whenSubjectIsLess() {
        Assertions.assertTrue(Subject.of(1).compare(Comparator.naturalOrder()).compareTo(2) < 0);
    }

    @Test
    void compareTo_shouldReturnZero_whenSubjectIsEqual() {
        Assertions.assertEquals(0, Subject.of(1).compare(Comparator.naturalOrder()).compareTo(1));
    }

    @Test
    void compareTo_shouldReturnPositive_whenSubjectIsGreater() {
        Assertions.assertTrue(Subject.of(2).compare(Comparator.naturalOrder()).compareTo(1) > 0);
    }

    // ------------------------------------------------------------
    // isLessThan
    // ------------------------------------------------------------

    @Test
    void isLessThan_shouldReturnTrue_whenSubjectIsLess() {
        Assertions.assertTrue(Subject.of(1).compare(Comparator.naturalOrder()).isLessThan(2));
    }

    @Test
    void isLessThan_shouldReturnFalse_whenSubjectIsEqual() {
        Assertions.assertFalse(Subject.of(1).compare(Comparator.naturalOrder()).isLessThan(1));
    }

    @Test
    void isLessThan_shouldReturnFalse_whenSubjectIsGreater() {
        Assertions.assertFalse(Subject.of(2).compare(Comparator.naturalOrder()).isLessThan(1));
    }

    // ------------------------------------------------------------
    // isLessThanOrEqual
    // ------------------------------------------------------------

    @Test
    void isLessThanOrEqual_shouldReturnTrue_whenSubjectIsLess() {
        Assertions.assertTrue(Subject.of(1).compare(Comparator.naturalOrder()).isLessThanOrEqual(2));
    }

    @Test
    void isLessThanOrEqual_shouldReturnTrue_whenSubjectIsEqual() {
        Assertions.assertTrue(Subject.of(1).compare(Comparator.naturalOrder()).isLessThanOrEqual(1));
    }

    @Test
    void isLessThanOrEqual_shouldReturnFalse_whenSubjectIsGreater() {
        Assertions.assertFalse(Subject.of(2).compare(Comparator.naturalOrder()).isLessThanOrEqual(1));
    }

    // ------------------------------------------------------------
    // isGreaterThan
    // ------------------------------------------------------------

    @Test
    void isGreaterThan_shouldReturnTrue_whenSubjectIsGreater() {
        Assertions.assertTrue(Subject.of(2).compare(Comparator.naturalOrder()).isGreaterThan(1));
    }

    @Test
    void isGreaterThan_shouldReturnFalse_whenSubjectIsEqual() {
        Assertions.assertFalse(Subject.of(1).compare(Comparator.naturalOrder()).isGreaterThan(1));
    }

    @Test
    void isGreaterThan_shouldReturnFalse_whenSubjectIsLess() {
        Assertions.assertFalse(Subject.of(1).compare(Comparator.naturalOrder()).isGreaterThan(2));
    }

    // ------------------------------------------------------------
    // isGreaterThanOrEqual
    // ------------------------------------------------------------

    @Test
    void isGreaterThanOrEqual_shouldReturnTrue_whenSubjectIsGreater() {
        Assertions.assertTrue(Subject.of(2).compare(Comparator.naturalOrder()).isGreaterThanOrEqual(1));
    }

    @Test
    void isGreaterThanOrEqual_shouldReturnTrue_whenSubjectIsEqual() {
        Assertions.assertTrue(Subject.of(1).compare(Comparator.naturalOrder()).isGreaterThanOrEqual(1));
    }

    @Test
    void isGreaterThanOrEqual_shouldReturnFalse_whenSubjectIsLess() {
        Assertions.assertFalse(Subject.of(1).compare(Comparator.naturalOrder()).isGreaterThanOrEqual(2));
    }

    // ------------------------------------------------------------
    // isEqual
    // ------------------------------------------------------------

    @Test
    void isEqual_shouldReturnTrue_whenComparatorConsidersValuesEqual() {
        Assertions.assertTrue(Subject.of(1).compare(Comparator.naturalOrder()).isEqual(1));
    }

    @Test
    void isEqual_shouldReturnFalse_whenComparatorConsidersValuesDifferent() {
        Assertions.assertFalse(Subject.of(1).compare(Comparator.naturalOrder()).isEqual(2));
    }

    @Test
    void isEqual_shouldReflectComparatorSemantics_notObjectEquality() {
        // Case-insensitive comparator considers "hello" and "HELLO" equal
        Assertions.assertTrue(Subject.of("hello").compare(String.CASE_INSENSITIVE_ORDER).isEqual("HELLO"));
    }

    // ------------------------------------------------------------
    // ifLessThan
    // ------------------------------------------------------------

    @Test
    void ifLessThan_shouldExecuteConsumer_whenSubjectIsLess() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifLessThan(2, captured::add);

        Assertions.assertEquals(List.of(1), captured);
    }

    @Test
    void ifLessThan_shouldNotExecuteConsumer_whenSubjectIsEqualOrGreater() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(2).compare(Comparator.naturalOrder()).ifLessThan(1, captured::add);
        Subject.of(1).compare(Comparator.naturalOrder()).ifLessThan(1, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    // ------------------------------------------------------------
    // ifLessThanOrEqual
    // ------------------------------------------------------------

    @Test
    void ifLessThanOrEqual_shouldExecuteConsumer_whenSubjectIsLess() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifLessThanOrEqual(2, captured::add);

        Assertions.assertEquals(List.of(1), captured);
    }

    @Test
    void ifLessThanOrEqual_shouldExecuteConsumer_whenSubjectIsEqual() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifLessThanOrEqual(1, captured::add);

        Assertions.assertEquals(List.of(1), captured);
    }

    @Test
    void ifLessThanOrEqual_shouldNotExecuteConsumer_whenSubjectIsGreater() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(2).compare(Comparator.naturalOrder()).ifLessThanOrEqual(1, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    // ------------------------------------------------------------
    // ifGreaterThan
    // ------------------------------------------------------------

    @Test
    void ifGreaterThan_shouldExecuteConsumer_whenSubjectIsGreater() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(2).compare(Comparator.naturalOrder()).ifGreaterThan(1, captured::add);

        Assertions.assertEquals(List.of(2), captured);
    }

    @Test
    void ifGreaterThan_shouldNotExecuteConsumer_whenSubjectIsEqualOrLess() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifGreaterThan(2, captured::add);
        Subject.of(1).compare(Comparator.naturalOrder()).ifGreaterThan(1, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    // ------------------------------------------------------------
    // ifGreaterThanOrEqual
    // ------------------------------------------------------------

    @Test
    void ifGreaterThanOrEqual_shouldExecuteConsumer_whenSubjectIsGreater() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(2).compare(Comparator.naturalOrder()).ifGreaterThanOrEqual(1, captured::add);

        Assertions.assertEquals(List.of(2), captured);
    }

    @Test
    void ifGreaterThanOrEqual_shouldExecuteConsumer_whenSubjectIsEqual() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifGreaterThanOrEqual(1, captured::add);

        Assertions.assertEquals(List.of(1), captured);
    }

    @Test
    void ifGreaterThanOrEqual_shouldNotExecuteConsumer_whenSubjectIsLess() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifGreaterThanOrEqual(2, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    // ------------------------------------------------------------
    // ifEqual
    // ------------------------------------------------------------

    @Test
    void ifEqual_shouldExecuteConsumer_whenComparatorConsidersValuesEqual() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifEqual(1, captured::add);

        Assertions.assertEquals(List.of(1), captured);
    }

    @Test
    void ifEqual_shouldNotExecuteConsumer_whenComparatorConsidersValuesDifferent() {
        List<Integer> captured = new ArrayList<>();

        Subject.of(1).compare(Comparator.naturalOrder()).ifEqual(2, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    // ------------------------------------------------------------
    // withComparator
    // ------------------------------------------------------------

    @Test
    void withComparator_shouldApplyNewComparator() {
        ComparisonFacet<String> facet = Subject.of("hello").compare(Comparator.naturalOrder());

        // Natural order: "hello" < "HELLO" is false (uppercase sorts before lowercase in natural order)
        // Case-insensitive: "hello" == "HELLO"
        ComparisonFacet<String> caseInsensitive = facet.withComparator(String.CASE_INSENSITIVE_ORDER);

        Assertions.assertTrue(caseInsensitive.isEqual("HELLO"));
    }

    // ------------------------------------------------------------
    // withSubject
    // ------------------------------------------------------------

    @Test
    void withSubject_shouldApplyNewSubject() {
        ComparisonFacet<Integer> facet = Subject.of(1).compare(Comparator.naturalOrder());

        ComparisonFacet<Integer> updated = facet.withSubject(Subject.of(5));

        Assertions.assertTrue(updated.isGreaterThan(3));
    }
}