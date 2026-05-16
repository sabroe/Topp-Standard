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

import java.util.List;

class NullFacetTest {

    // ------------------------------------------------------------
    // isNull
    // ------------------------------------------------------------

    @Test
    void isNull_shouldReturnTrue_whenValueIsNull() {
        Assertions.assertTrue(Subject.<String>of(null).nulls().isNull());
    }

    @Test
    void isNull_shouldReturnFalse_whenValueIsNonNull() {
        Assertions.assertFalse(Subject.of("hello").nulls().isNull());
    }

    // ------------------------------------------------------------
    // isNotNull
    // ------------------------------------------------------------

    @Test
    void isNotNull_shouldReturnTrue_whenValueIsNonNull() {
        Assertions.assertTrue(Subject.of("hello").nulls().isNotNull());
    }

    @Test
    void isNotNull_shouldReturnFalse_whenValueIsNull() {
        Assertions.assertFalse(Subject.<String>of(null).nulls().isNotNull());
    }

    // ------------------------------------------------------------
    // or(T fallback)
    // ------------------------------------------------------------

    @Test
    void orValue_shouldReturnOriginalSubject_whenValueIsNonNull() {
        Subject<String> subject = Subject.of("hello");
        Subject<String> result = subject.nulls().or("fallback");

        Assertions.assertEquals(Subject.of("hello"), result);
        Assertions.assertSame(subject, result);
    }

    @Test
    void orValue_shouldReturnFallback_whenValueIsNull() {
        Subject<String> result = Subject.<String>of(null).nulls().or("fallback");

        Assertions.assertEquals(Subject.of("fallback"), result);
    }

    // ------------------------------------------------------------
    // or(Subject<T> other)
    // ------------------------------------------------------------

    @Test
    void orSubject_shouldReturnOriginalSubject_whenValueIsNonNull() {
        Subject<String> subject = Subject.of("hello");
        Subject<String> other = Subject.of("other");
        Subject<String> result = subject.nulls().or(other);

        Assertions.assertSame(subject, result);
    }

    @Test
    void orSubject_shouldReturnOtherSubject_whenValueIsNull() {
        Subject<String> other = Subject.of("other");
        Subject<String> result = Subject.<String>of(null).nulls().or(other);

        Assertions.assertSame(other, result);
    }

    // ------------------------------------------------------------
    // orGet
    // ------------------------------------------------------------

    @Test
    void orGet_shouldReturnOriginalSubject_whenValueIsNonNull() {
        Subject<String> subject = Subject.of("hello");
        boolean[] invoked = {false};

        Subject<String> result = subject.nulls().orGet(() -> { invoked[0] = true; return "supplied"; });

        Assertions.assertSame(subject, result);
        Assertions.assertFalse(invoked[0]);
    }

    @Test
    void orGet_shouldReturnSuppliedValue_whenValueIsNull() {
        Subject<String> result = Subject.<String>of(null).nulls().orGet(() -> "supplied");

        Assertions.assertEquals(Subject.of("supplied"), result);
    }

    // ------------------------------------------------------------
    // orAny
    // ------------------------------------------------------------

    @Test
    void orAny_shouldReturnOriginalSubject_whenValueIsNonNull() {
        Subject<String> subject = Subject.of("hello");
        Subject<String> result = subject.nulls().orAny(List.of("a", "b"));

        Assertions.assertSame(subject, result);
    }

    @Test
    void orAny_shouldReturnFirstNonNull_whenValueIsNull() {
        Subject<String> result = Subject.<String>of(null).nulls().orAny(List.of("first", "second"));

        Assertions.assertEquals(Subject.of("first"), result);
    }

    @Test
    void orAny_shouldSkipNullsInIterable_whenValueIsNull() {
        Subject<String> result = Subject.<String>of(null).nulls().orAny(List.of("first", "second"));

        Assertions.assertEquals(Subject.of("first"), result);
    }

    @Test
    void orAny_shouldReturnEmptySubject_whenAllIterableValuesAreNull() {
        Subject<String> result = Subject.<String>of(null).nulls().orAny(java.util.Arrays.asList(null, null));

        Assertions.assertNull(result.getValue());
    }

    @Test
    void orAny_shouldReturnEmptySubject_whenIterableIsNull() {
        Subject<String> result = Subject.<String>of(null).nulls().orAny(null);

        Assertions.assertNull(result.getValue());
    }
}