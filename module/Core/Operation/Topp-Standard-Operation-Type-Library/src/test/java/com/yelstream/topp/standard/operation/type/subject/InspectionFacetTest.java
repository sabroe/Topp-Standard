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

class InspectionFacetTest {

    // ------------------------------------------------------------
    // matches
    // ------------------------------------------------------------

    @Test
    void matches_shouldReturnTrue_whenPredicateMatches() {
        Assertions.assertTrue(Subject.of("hello").inspect().matches(s -> s.startsWith("h")));
    }

    @Test
    void matches_shouldReturnFalse_whenPredicateDoesNotMatch() {
        Assertions.assertFalse(Subject.of("hello").inspect().matches(s -> s.startsWith("x")));
    }

    @Test
    void matches_shouldApplyPredicate_whenValueIsNull() {
        Assertions.assertTrue(Subject.<String>of(null).inspect().matches(s -> s == null));
        Assertions.assertFalse(Subject.<String>of(null).inspect().matches(s -> s != null));
    }

    // ------------------------------------------------------------
    // ifMatches
    // ------------------------------------------------------------

    @Test
    void ifMatches_shouldExecuteConsumer_whenPredicateMatches() {
        List<String> captured = new ArrayList<>();

        Subject.of("hello").inspect().ifMatches(s -> s.startsWith("h"), captured::add);

        Assertions.assertEquals(List.of("hello"), captured);
    }

    @Test
    void ifMatches_shouldNotExecuteConsumer_whenPredicateDoesNotMatch() {
        List<String> captured = new ArrayList<>();

        Subject.of("hello").inspect().ifMatches(s -> s.startsWith("x"), captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    @Test
    void ifMatches_shouldExecuteConsumer_whenValueIsNull_andPredicateAcceptsNull() {
        List<String> captured = new ArrayList<>();

        Subject.<String>of(null).inspect().ifMatches(s -> s == null, captured::add);

        Assertions.assertEquals(1, captured.size());
        Assertions.assertNull(captured.get(0));
    }

    // ------------------------------------------------------------
    // filter
    // ------------------------------------------------------------

    @Test
    void filter_shouldReturnSameSubject_whenPredicateMatches() {
        Subject<String> subject = Subject.of("hello");

        Subject<String> result = subject.inspect().filter(s -> s.startsWith("h"));

        Assertions.assertSame(subject, result);
    }

    @Test
    void filter_shouldReturnEmptySubject_whenPredicateDoesNotMatch() {
        Subject<String> result = Subject.of("hello").inspect().filter(s -> s.startsWith("x"));

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getValue());
    }

    @Test
    void filter_shouldReturnEmptySubject_whenValueIsNull_andPredicateRejectsNull() {
        Subject<String> result = Subject.<String>of(null).inspect().filter(s -> s != null);

        Assertions.assertNull(result.getValue());
    }

    @Test
    void filter_shouldReturnSubject_whenValueIsNull_andPredicateAcceptsNull() {
        Subject<String> subject = Subject.<String>of(null);

        Subject<String> result = subject.inspect().filter(s -> s == null);

        Assertions.assertSame(subject, result);
    }

    @Test
    void filter_emptyResult_isIndistinguishableFrom_nullValueSubject() {
        // Documents the known ambiguity: filter mismatch and a null-valued subject
        // both produce a Subject with a null value.
        Subject<String> fromMismatch = Subject.of("hello").inspect().filter(s -> false);
        Subject<String> fromNullValue = Subject.<String>of(null);

        Assertions.assertEquals(fromNullValue, fromMismatch);
    }

    // ------------------------------------------------------------
    // chaining
    // ------------------------------------------------------------

    @Test
    void filter_shouldChainIntoNullFacet_forFallbackOnMismatch() {
        Subject<String> result = Subject.of("hello")
                .inspect().filter(s -> s.startsWith("x"))
                .nulls().or("fallback");

        Assertions.assertEquals(Subject.of("fallback"), result);
    }

    @Test
    void filter_shouldChainIntoPresenceFacet() {
        boolean present = Subject.of("hello")
                .inspect().filter(s -> s.length() > 3)
                .presence().isPresent();

        Assertions.assertTrue(present);
    }
}