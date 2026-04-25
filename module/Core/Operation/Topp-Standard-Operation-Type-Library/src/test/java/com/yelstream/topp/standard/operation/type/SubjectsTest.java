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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

class SubjectsTest {

    // ------------------------------------------------------------
    // tryCast
    // ------------------------------------------------------------

    @Test
    void tryCast_shouldReturnSubject_whenTypeMatches() {
        Subject<Object> input = Subject.of("hello");

        Optional<Subject<String>> result =
                Subjects.tryCast(input, String.class);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("hello", result.get().getValue());

        // identity preserved (same Subject instance wrapped logic)
        Assertions.assertSame("hello", result.get().getValue());
    }

    @Test
    void tryCast_shouldReturnEmpty_whenTypeDoesNotMatch() {
        Subject<Object> input = Subject.of(123);

        Optional<Subject<String>> result =
                Subjects.tryCast(input, String.class);

        Assertions.assertTrue(result.isEmpty());
    }

    // ------------------------------------------------------------
    // as
    // ------------------------------------------------------------

    @Test
    void as_shouldReturnSameSubject_whenTypeMatches() {
        Subject<Object> input = Subject.of("hello");

        Subject<String> result =
                Subjects.as(input, String.class);

        Assertions.assertEquals("hello", result.getValue());
        Assertions.assertSame("hello", result.getValue());
    }

    @Test
    void as_shouldReturnEmptySubject_whenTypeDoesNotMatch() {
        Subject<Object> input = Subject.of(123);

        Subject<String> result =
                Subjects.as(input, String.class);

        Assertions.assertNull(result.getValue());
    }

    @Test
    void as_shouldReturnEmptySubject_whenValueIsNull() {
        Subject<Object> input = Subject.of(null);

        Subject<String> result =
                Subjects.as(input, String.class);

        Assertions.assertNull(result.getValue());
    }

    // ------------------------------------------------------------
    // mapByValue
    // ------------------------------------------------------------

    @Test
    void mapByValue_shouldTransformValue() {
        Subject<String> input = Subject.of("abc");

        Optional<Subject<Integer>> result =
                Subjects.mapByValue(input, String::length);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(3, result.get().getValue());
    }

    @Test
    void mapByValue_shouldReturnEmpty_whenSubjectIsNull() {
        Optional<Subject<Integer>> result =
                Subjects.mapByValue(null, String::length);

        Assertions.assertTrue(result.isEmpty());
    }

    // ------------------------------------------------------------
    // to
    // ------------------------------------------------------------

    @Test
    void to_shouldTransformValue() {
        Subject<String> input = Subject.of("abc");

        Subject<Integer> result =
                Subjects.to(input, String::length);

        Assertions.assertEquals(3, result.getValue());
    }

    // ------------------------------------------------------------
    // filterByValue
    // ------------------------------------------------------------

    @Test
    void filterByValue_shouldReturnSubject_whenPredicateMatches() {
        Subject<String> input = Subject.of("hello");

        Optional<Subject<String>> result =
                Subjects.filterByValue(input, s -> s.startsWith("h"));

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("hello", result.get().getValue());
    }

    @Test
    void filterByValue_shouldReturnEmpty_whenPredicateFails() {
        Subject<String> input = Subject.of("hello");

        Optional<Subject<String>> result =
                Subjects.filterByValue(input, s -> s.startsWith("x"));

        Assertions.assertTrue(result.isEmpty());
    }

    // ------------------------------------------------------------
    // or (state-preservation policy)
    // ------------------------------------------------------------

    @Test
    void or_shouldPreserveExistingValue() {
        Subject<String> input = Subject.of("hello");

        Subject<String> result =
                Subjects.or(input, "fallback");

        Assertions.assertEquals("hello", result.getValue());

        // identity preserved (same instance returned)
        Assertions.assertSame(input, result);
    }

    @Test
    void or_shouldReplace_whenValueIsNull() {
        Subject<String> input = Subject.of(null);

        Subject<String> result =
                Subjects.or(input, "fallback");

        Assertions.assertEquals("fallback", result.getValue());
    }

    @Test
    void or_shouldFail_whenSubjectIsNull() {
        Subject<String> input = null;

        Assertions.assertThrows(
                NullPointerException.class,
                () -> Subjects.or(input, "fallback")
        );
    }
}
