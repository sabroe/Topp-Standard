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

package com.yelstream.topp.standard.operation.type.subject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EqualityFacetTest {

    // ------------------------------------------------------------
    // isEquals
    // ------------------------------------------------------------

    @Test
    void isEquals_shouldReturnTrue_whenValuesAreEqual() {
        Assertions.assertTrue(Subject.of("hello").equality().isEquals("hello"));
    }

    @Test
    void isEquals_shouldReturnFalse_whenValuesAreNotEqual() {
        Assertions.assertFalse(Subject.of("hello").equality().isEquals("world"));
    }

    @Test
    void isEquals_shouldReturnTrue_whenBothAreNull() {
        Assertions.assertTrue(Subject.<String>of(null).equality().isEquals(null));
    }

    @Test
    void isEquals_shouldReturnFalse_whenSubjectValueIsNull_andArgumentIsNonNull() {
        Assertions.assertFalse(Subject.<String>of(null).equality().isEquals("hello"));
    }

    @Test
    void isEquals_shouldReturnFalse_whenSubjectValueIsNonNull_andArgumentIsNull() {
        Assertions.assertFalse(Subject.of("hello").equality().isEquals(null));
    }

    @Test
    void isEquals_shouldUseValueEquality_notReferenceEquality() {
        String a = new String("hello");
        String b = new String("hello");
        Assertions.assertNotSame(a, b);
        Assertions.assertTrue(Subject.of(a).equality().isEquals(b));
    }

    // ------------------------------------------------------------
    // isNotEquals
    // ------------------------------------------------------------

    @Test
    void isNotEquals_shouldReturnTrue_whenValuesAreNotEqual() {
        Assertions.assertTrue(Subject.of("hello").equality().isNotEquals("world"));
    }

    @Test
    void isNotEquals_shouldReturnFalse_whenValuesAreEqual() {
        Assertions.assertFalse(Subject.of("hello").equality().isNotEquals("hello"));
    }

    @Test
    void isNotEquals_shouldReturnFalse_whenBothAreNull() {
        Assertions.assertFalse(Subject.<String>of(null).equality().isNotEquals(null));
    }

    // ------------------------------------------------------------
    // ifEquals
    // ------------------------------------------------------------

    @Test
    void ifEquals_shouldExecuteConsumer_whenValuesAreEqual() {
        List<String> captured = new ArrayList<>();

        Subject.of("hello").equality().ifEquals("hello", captured::add);

        Assertions.assertEquals(List.of("hello"), captured);
    }

    @Test
    void ifEquals_shouldNotExecuteConsumer_whenValuesAreNotEqual() {
        List<String> captured = new ArrayList<>();

        Subject.of("hello").equality().ifEquals("world", captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    @Test
    void ifEquals_shouldExecuteConsumer_whenBothAreNull() {
        List<String> captured = new ArrayList<>();

        Subject.<String>of(null).equality().ifEquals(null, captured::add);

        Assertions.assertEquals(1, captured.size());
        Assertions.assertNull(captured.getFirst());
    }

    @Test
    void ifEquals_shouldPassSubjectValue_notArgument() {
        String subjectValue = new String("hello");
        String argument = new String("hello");
        List<String> captured = new ArrayList<>();

        Subject.of(subjectValue).equality().ifEquals(argument, captured::add);

        Assertions.assertSame(subjectValue, captured.getFirst());
    }

    // ------------------------------------------------------------
    // ifNotEquals
    // ------------------------------------------------------------

    @Test
    void ifNotEquals_shouldExecuteConsumer_whenValuesAreNotEqual() {
        List<String> captured = new ArrayList<>();

        Subject.of("hello").equality().ifNotEquals("world", captured::add);

        Assertions.assertEquals(List.of("hello"), captured);
    }

    @Test
    void ifNotEquals_shouldNotExecuteConsumer_whenValuesAreEqual() {
        List<String> captured = new ArrayList<>();

        Subject.of("hello").equality().ifNotEquals("hello", captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    @Test
    void ifNotEquals_shouldNotExecuteConsumer_whenBothAreNull() {
        List<String> captured = new ArrayList<>();

        Subject.<String>of(null).equality().ifNotEquals(null, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }
}