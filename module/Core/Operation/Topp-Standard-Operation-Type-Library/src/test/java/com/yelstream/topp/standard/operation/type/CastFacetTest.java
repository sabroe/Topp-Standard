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

import java.util.Optional;

class CastFacetTest {

    // ------------------------------------------------------------
    // tryCast
    // ------------------------------------------------------------

    @Test
    void tryCast_shouldReturnPresentSubject_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Optional<Subject<String>> result = facet.tryCast(String.class);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("hello", result.get().getValue());
    }

    @Test
    void tryCast_shouldReturnEmpty_whenTypeDoesNotMatch() {
        CastFacet<Object> facet = Subject.<Object>of(42).casting();

        Optional<Subject<String>> result = facet.tryCast(String.class);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void tryCast_shouldReturnEmpty_whenValueIsNull() {
        CastFacet<Object> facet = Subject.<Object>of(null).casting();

        Optional<Subject<String>> result = facet.tryCast(String.class);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void tryCast_shouldThrow_whenTypeIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.tryCast(null));
    }

    // ------------------------------------------------------------
    // tryCastOrNull
    // ------------------------------------------------------------

    @Test
    void tryCastOrNull_shouldReturnCastSubject_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Subject<String> result = facet.tryCastOrNull(String.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("hello", result.getValue());
    }

    @Test
    void tryCastOrNull_shouldReturnNull_whenTypeDoesNotMatch() {
        CastFacet<Object> facet = Subject.<Object>of(42).casting();

        Subject<String> result = facet.tryCastOrNull(String.class);

        Assertions.assertNull(result);
    }

    @Test
    void tryCastOrNull_shouldReturnNull_whenValueIsNull() {
        CastFacet<Object> facet = Subject.<Object>of(null).casting();

        Subject<String> result = facet.tryCastOrNull(String.class);

        Assertions.assertNull(result);
    }

    @Test
    void tryCastOrNull_shouldThrow_whenTypeIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.tryCastOrNull(null));
    }

    // ------------------------------------------------------------
    // tryCastOr
    // ------------------------------------------------------------

    @Test
    void tryCastOr_shouldReturnCastSubject_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Subject<String> result = facet.tryCastOr(String.class, "fallback");

        Assertions.assertEquals("hello", result.getValue());
    }

    @Test
    void tryCastOr_shouldReturnFallback_whenTypeDoesNotMatch() {
        CastFacet<Object> facet = Subject.<Object>of(42).casting();

        Subject<String> result = facet.tryCastOr(String.class, "fallback");

        Assertions.assertEquals("fallback", result.getValue());
    }

    @Test
    void tryCastOr_shouldReturnFallback_whenValueIsNull() {
        CastFacet<Object> facet = Subject.<Object>of(null).casting();

        Subject<String> result = facet.tryCastOr(String.class, "fallback");

        Assertions.assertEquals("fallback", result.getValue());
    }

    @Test
    void tryCastOr_shouldThrow_whenTypeIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.tryCastOr(null, "fallback"));
    }

    // ------------------------------------------------------------
    // tryCastOrGet
    // ------------------------------------------------------------

    @Test
    void tryCastOrGet_shouldReturnCastSubject_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Subject<String> result = facet.tryCastOrGet(String.class, () -> "fallback");

        Assertions.assertEquals("hello", result.getValue());
    }

    @Test
    void tryCastOrGet_shouldReturnSuppliedFallback_whenTypeDoesNotMatch() {
        CastFacet<Object> facet = Subject.<Object>of(42).casting();

        Subject<String> result = facet.tryCastOrGet(String.class, () -> "fallback");

        Assertions.assertEquals("fallback", result.getValue());
    }

    @Test
    void tryCastOrGet_shouldNotInvokeSupplier_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();
        boolean[] invoked = {false};

        facet.tryCastOrGet(String.class, () -> { invoked[0] = true; return "fallback"; });

        Assertions.assertFalse(invoked[0]);
    }

    @Test
    void tryCastOrGet_shouldThrow_whenTypeIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.tryCastOrGet(null, () -> "fallback"));
    }

    @Test
    void tryCastOrGet_shouldThrow_whenSupplierIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.tryCastOrGet(String.class, null));
    }

    // ------------------------------------------------------------
    // cast
    // ------------------------------------------------------------

    @Test
    void cast_shouldReturnCastSubject_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Subject<String> result = facet.cast(String.class);

        Assertions.assertEquals("hello", result.getValue());
    }

    @Test
    void cast_shouldThrow_whenTypeDoesNotMatch() {
        CastFacet<Object> facet = Subject.<Object>of(42).casting();

        Assertions.assertThrows(ClassCastException.class,
                () -> facet.cast(String.class));
    }

    @Test
    void cast_shouldThrow_whenValueIsNull() {
        CastFacet<Object> facet = Subject.<Object>of(null).casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.cast(String.class));
    }

    @Test
    void cast_shouldThrow_whenTypeIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.cast(null));
    }

    // ------------------------------------------------------------
    // as
    // ------------------------------------------------------------

    @Test
    void as_shouldReturnCastSubject_whenTypeMatches() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Subject<String> result = facet.as(String.class);

        Assertions.assertEquals("hello", result.getValue());
    }

    @Test
    void as_shouldReturnEmptySubject_whenTypeDoesNotMatch() {
        CastFacet<Object> facet = Subject.<Object>of(42).casting();

        Subject<String> result = facet.as(String.class);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getValue());
    }

    @Test
    void as_shouldReturnEmptySubject_whenValueIsNull() {
        CastFacet<Object> facet = Subject.<Object>of(null).casting();

        Subject<String> result = facet.as(String.class);

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getValue());
    }

    @Test
    void as_shouldThrow_whenTypeIsNull() {
        CastFacet<Object> facet = Subject.<Object>of("hello").casting();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.as(null));
    }
}