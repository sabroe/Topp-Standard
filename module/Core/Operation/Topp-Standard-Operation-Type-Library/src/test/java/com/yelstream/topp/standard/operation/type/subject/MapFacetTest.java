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

class MapFacetTest {

    // ------------------------------------------------------------
    // to
    // ------------------------------------------------------------

    @Test
    void to_shouldTransformValue() {
        Subject<String> result = Subject.of("hello").map().to(String::toUpperCase);

        Assertions.assertEquals(Subject.of("HELLO"), result);
    }

    @Test
    void to_shouldChangeType() {
        Subject<Integer> result = Subject.of("hello").map().to(String::length);

        Assertions.assertEquals(Subject.of(5), result);
    }

    @Test
    void to_shouldWrapNull_whenMapperReturnsNull() {
        Subject<String> result = Subject.of("hello").map().to(v -> null);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getValue());
    }

    @Test
    void to_shouldApplyMapper_whenValueIsNull() {
        Subject<Integer> result = Subject.<String>of(null).map().to(v -> 42);

        Assertions.assertEquals(Subject.of(42), result);
    }

    @Test
    void to_shouldThrow_whenMapperIsNull() {
        MapFacet<String> facet = Subject.of("hello").map();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.to(null));
    }

    // ------------------------------------------------------------
    // toFlat
    // ------------------------------------------------------------

    @Test
    void toFlat_shouldReturnSubjectFromMapper() {
        Subject<Integer> result = Subject.of("hello").map().toFlat(v -> Subject.of(v.length()));

        Assertions.assertEquals(Subject.of(5), result);
    }

    @Test
    void toFlat_shouldNotDoubleWrap() {
        Subject<String> inner = Subject.of("inner");
        Subject<String> result = Subject.of("hello").map().toFlat(v -> inner);

        Assertions.assertSame(inner, result);
    }

    @Test
    void toFlat_shouldApplyMapper_whenValueIsNull() {
        Subject<String> fallback = Subject.of("fallback");
        Subject<String> result = Subject.<String>of(null).map().toFlat(v -> fallback);

        Assertions.assertSame(fallback, result);
    }

    @Test
    void toFlat_shouldThrow_whenMapperIsNull() {
        MapFacet<String> facet = Subject.of("hello").map();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.toFlat(null));
    }

    // ------------------------------------------------------------
    // chaining
    // ------------------------------------------------------------

    @Test
    void to_shouldChainWithNullFacet() {
        Subject<Integer> result = Subject.of("hello")
                .map().to(String::length)
                .nulls().or(0);

        Assertions.assertEquals(Subject.of(5), result);
    }

    @Test
    void toFlat_shouldChainWithNullFacet() {
        Subject<Integer> result = Subject.of("hello")
                .map().toFlat(v -> Subject.of(v.length()))
                .nulls().or(0);

        Assertions.assertEquals(Subject.of(5), result);
    }
}