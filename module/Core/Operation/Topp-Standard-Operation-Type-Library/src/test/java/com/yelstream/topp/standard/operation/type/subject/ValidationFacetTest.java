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

class ValidationFacetTest {

    // ------------------------------------------------------------
    // nonNull
    // ------------------------------------------------------------

    @Test
    void nonNull_shouldReturnSubject_whenValueIsNonNull() {
        Subject<String> subject = Subject.of("hello");

        Subject<String> result = subject.require().nonNull();

        Assertions.assertSame(subject, result);
    }

    @Test
    void nonNull_shouldThrow_whenValueIsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Subject.<String>of(null).require().nonNull());
    }

    @Test
    void nonNull_shouldChainFurther_afterSuccessfulValidation() {
        String value = Subject.of("hello")
                .require().nonNull()
                .getValue();

        Assertions.assertEquals("hello", value);
    }

    // ------------------------------------------------------------
    // isInstance
    // ------------------------------------------------------------

    @Test
    void isInstance_shouldReturnSubject_whenValueMatchesType() {
        Subject<Object> subject = Subject.<Object>of("hello");

        Subject<Object> result = subject.require().isInstance(String.class);

        Assertions.assertSame(subject, result);
    }

    @Test
    void isInstance_shouldReturnSubject_whenValueMatchesSupertype() {
        Subject<Object> subject = Subject.<Object>of("hello");

        Subject<Object> result = subject.require().isInstance(CharSequence.class);

        Assertions.assertSame(subject, result);
    }

    @Test
    void isInstance_shouldThrow_whenValueIsWrongType() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Subject.<Object>of(42).require().isInstance(String.class));
    }

    @Test
    void isInstance_shouldThrow_whenValueIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Subject.<Object>of(null).require().isInstance(String.class));
    }

    @Test
    void isInstance_shouldThrow_whenTypeIsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Subject.of("hello").require().isInstance(null));
    }

    // ------------------------------------------------------------
    // matches
    // ------------------------------------------------------------

    @Test
    void matches_shouldReturnSubject_whenPredicateMatches() {
        Subject<String> subject = Subject.of("hello");

        Subject<String> result = subject.require().matches(s -> s.startsWith("h"));

        Assertions.assertSame(subject, result);
    }

    @Test
    void matches_shouldThrow_whenPredicateDoesNotMatch() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Subject.of("hello").require().matches(s -> s.startsWith("x")));
    }

    @Test
    void matches_shouldThrow_whenPredicateIsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Subject.of("hello").require().matches(null));
    }

    @Test
    void matches_shouldApplyPredicate_whenValueIsNull() {
        Subject<String> subject = Subject.<String>of(null);

        // predicate that accepts null passes
        Subject<String> result = subject.require().matches(s -> s == null);
        Assertions.assertSame(subject, result);

        // predicate that rejects null throws
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> subject.require().matches(s -> s != null));
    }

    // ------------------------------------------------------------
    // chaining
    // ------------------------------------------------------------

    @Test
    void validations_shouldChain_whenAllPass() {
        Subject<Object> subject = Subject.<Object>of("hello");

        Subject<Object> result = subject
                .require().nonNull()
                .require().isInstance(String.class)
                .require().matches(v -> v instanceof String s && s.length() > 3);

        Assertions.assertSame(subject, result);
    }
}