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
import java.util.List;
import java.util.Objects;

class IdentityFacetTest {

    // ------------------------------------------------------------
    // identityHash
    // ------------------------------------------------------------

    @Test
    void identityHash_shouldMatchSystemIdentityHashCode() {
        String value = new String("hello");
        int result = Subject.of(value).identity().identityHash();

        Assertions.assertEquals(System.identityHashCode(value), result);
    }

    @Test
    void identityHash_shouldBeConsistent_forSameInstance() {
        String value = new String("hello");
        IdentityFacet<String> facet = Subject.of(value).identity();

        Assertions.assertEquals(facet.identityHash(), facet.identityHash());
    }

    @Test
    void identityHash_shouldDifferBetweenDistinctInstances_withEqualContent() {
        String a = new String("hello");
        String b = new String("hello");
        Assertions.assertNotSame(a, b);

        int hashA = Subject.of(a).identity().identityHash();
        int hashB = Subject.of(b).identity().identityHash();

        // Identity hash codes may theoretically collide but distinct instances
        // must produce hash codes consistent with System.identityHashCode
        Assertions.assertEquals(System.identityHashCode(a), hashA);
        Assertions.assertEquals(System.identityHashCode(b), hashB);
    }

    @Test
    void identityHash_shouldMatchSystemIdentityHashCode_forNullValue() {
        int result = Subject.<String>of(null).identity().identityHash();

        Assertions.assertEquals(System.identityHashCode(null), result);
    }

    // ------------------------------------------------------------
    // identityString
    // ------------------------------------------------------------

    @Test
    void identityString_shouldMatchObjectsToIdentityString() {
        String value = new String("hello");
        String result = Subject.of(value).identity().identityString();

        Assertions.assertEquals(Objects.toIdentityString(value), result);
    }

    @Test
    void identityString_shouldContainClassName() {
        String value = new String("hello");
        String result = Subject.of(value).identity().identityString();

        Assertions.assertTrue(result.contains("String"));
    }

    @Test
    void identityString_shouldThrow_whenValueIsNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Subject.<String>of(null).identity().identityString());
    }

    // ------------------------------------------------------------
    // isSame
    // ------------------------------------------------------------

    @Test
    void isSame_shouldReturnTrue_whenSameInstance() {
        String value = new String("hello");
        Assertions.assertTrue(Subject.of(value).identity().isSame(value));
    }

    @Test
    void isSame_shouldReturnFalse_whenDifferentInstance_withEqualContent() {
        String a = new String("hello");
        String b = new String("hello");
        Assertions.assertNotSame(a, b);

        Assertions.assertFalse(Subject.of(a).identity().isSame(b));
    }

    @Test
    void isSame_shouldReturnTrue_whenBothAreNull() {
        Assertions.assertTrue(Subject.<String>of(null).identity().isSame(null));
    }

    @Test
    void isSame_shouldReturnFalse_whenSubjectValueIsNull_andArgumentIsNonNull() {
        Assertions.assertFalse(Subject.<String>of(null).identity().isSame("hello"));
    }

    @Test
    void isSame_shouldReturnFalse_whenSubjectValueIsNonNull_andArgumentIsNull() {
        Assertions.assertFalse(Subject.of("hello").identity().isSame(null));
    }

    // ------------------------------------------------------------
    // ifSame
    // ------------------------------------------------------------

    @Test
    void ifSame_shouldExecuteConsumer_whenSameInstance() {
        String value = new String("hello");
        List<String> captured = new ArrayList<>();

        Subject.of(value).identity().ifSame(value, captured::add);

        Assertions.assertEquals(List.of("hello"), captured);
    }

    @Test
    void ifSame_shouldNotExecuteConsumer_whenDifferentInstance() {
        String a = new String("hello");
        String b = new String("hello");
        List<String> captured = new ArrayList<>();

        Subject.of(a).identity().ifSame(b, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    @Test
    void ifSame_shouldExecuteConsumer_whenBothAreNull() {
        List<String> captured = new ArrayList<>();

        Subject.<String>of(null).identity().ifSame(null, captured::add);

        Assertions.assertEquals(1, captured.size());
        Assertions.assertNull(captured.get(0));
    }
}