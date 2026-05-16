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

class TypeFacetTest {

    // ------------------------------------------------------------
    // isInstance
    // ------------------------------------------------------------

    @Test
    void isInstance_shouldReturnTrue_whenValueMatchesType() {
        TypeFacet<Object> facet = Subject.<Object>of("hello").type();

        Assertions.assertTrue(facet.isInstance(String.class));
    }

    @Test
    void isInstance_shouldReturnTrue_whenValueMatchesSupertype() {
        TypeFacet<Object> facet = Subject.<Object>of("hello").type();

        Assertions.assertTrue(facet.isInstance(CharSequence.class));
    }

    @Test
    void isInstance_shouldReturnFalse_whenValueIsWrongType() {
        TypeFacet<Object> facet = Subject.<Object>of(42).type();

        Assertions.assertFalse(facet.isInstance(String.class));
    }

    @Test
    void isInstance_shouldReturnFalse_whenValueIsNull() {
        TypeFacet<Object> facet = Subject.<Object>of(null).type();

        Assertions.assertFalse(facet.isInstance(String.class));
    }

    @Test
    void isInstance_shouldThrow_whenTypeIsNull() {
        TypeFacet<Object> facet = Subject.<Object>of("hello").type();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.isInstance(null));
    }

    // ------------------------------------------------------------
    // ifInstance
    // ------------------------------------------------------------

    @Test
    void ifInstance_shouldExecuteAction_whenValueMatchesType() {
        TypeFacet<Object> facet = Subject.<Object>of("hello").type();
        List<String> captured = new ArrayList<>();

        facet.ifInstance(String.class, captured::add);

        Assertions.assertEquals(List.of("hello"), captured);
    }

    @Test
    void ifInstance_shouldPassCastValue_toAction() {
        TypeFacet<Object> facet = Subject.<Object>of(42).type();
        List<Integer> captured = new ArrayList<>();

        facet.ifInstance(Integer.class, captured::add);

        Assertions.assertEquals(List.of(42), captured);
    }

    @Test
    void ifInstance_shouldNotExecuteAction_whenValueIsWrongType() {
        TypeFacet<Object> facet = Subject.<Object>of(42).type();
        List<String> captured = new ArrayList<>();

        facet.ifInstance(String.class, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    @Test
    void ifInstance_shouldNotExecuteAction_whenValueIsNull() {
        TypeFacet<Object> facet = Subject.<Object>of(null).type();
        List<String> captured = new ArrayList<>();

        facet.ifInstance(String.class, captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    @Test
    void ifInstance_shouldThrow_whenTypeIsNull() {
        TypeFacet<Object> facet = Subject.<Object>of("hello").type();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.ifInstance(null, s -> {}));
    }

    @Test
    void ifInstance_shouldThrow_whenActionIsNull() {
        TypeFacet<Object> facet = Subject.<Object>of("hello").type();

        Assertions.assertThrows(NullPointerException.class,
                () -> facet.ifInstance(String.class, null));
    }
}