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

class PresenceFacetTest {

    // ------------------------------------------------------------
    // isPresent
    // ------------------------------------------------------------

    @Test
    void isPresent_shouldReturnTrue_whenValueIsNonNull() {
        PresenceFacet<String> facet = Subject.of("hello").presence();

        Assertions.assertTrue(facet.isPresent());
    }

    @Test
    void isPresent_shouldReturnFalse_whenValueIsNull() {
        PresenceFacet<String> facet = Subject.<String>of(null).presence();

        Assertions.assertFalse(facet.isPresent());
    }

    // ------------------------------------------------------------
    // isEmpty
    // ------------------------------------------------------------

    @Test
    void isEmpty_shouldReturnTrue_whenValueIsNull() {
        PresenceFacet<String> facet = Subject.<String>of(null).presence();

        Assertions.assertTrue(facet.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnFalse_whenValueIsNonNull() {
        PresenceFacet<String> facet = Subject.of("hello").presence();

        Assertions.assertFalse(facet.isEmpty());
    }

    // ------------------------------------------------------------
    // ifPresent
    // ------------------------------------------------------------

    @Test
    void ifPresent_shouldExecuteConsumer_whenValueIsPresent() {
        PresenceFacet<String> facet = Subject.of("hello").presence();
        List<String> captured = new ArrayList<>();

        facet.ifPresent(captured::add);

        Assertions.assertEquals(List.of("hello"), captured);
    }

    @Test
    void ifPresent_shouldNotExecuteConsumer_whenValueIsAbsent() {
        PresenceFacet<String> facet = Subject.<String>of(null).presence();
        List<String> captured = new ArrayList<>();

        facet.ifPresent(captured::add);

        Assertions.assertTrue(captured.isEmpty());
    }

    // ------------------------------------------------------------
    // ifEmpty
    // ------------------------------------------------------------

    @Test
    void ifEmpty_shouldExecuteAction_whenValueIsAbsent() {
        PresenceFacet<String> facet = Subject.<String>of(null).presence();
        boolean[] invoked = {false};

        facet.ifEmpty(() -> invoked[0] = true);

        Assertions.assertTrue(invoked[0]);
    }

    @Test
    void ifEmpty_shouldNotExecuteAction_whenValueIsPresent() {
        PresenceFacet<String> facet = Subject.of("hello").presence();
        boolean[] invoked = {false};

        facet.ifEmpty(() -> invoked[0] = true);

        Assertions.assertFalse(invoked[0]);
    }

    // ------------------------------------------------------------
    // orElse
    // ------------------------------------------------------------

    @Test
    void orElse_shouldReturnOriginalSubject_whenValueIsPresent() {
        Subject<String> subject = Subject.of("hello");
        Subject<String> result = subject.presence().orElse("fallback");

        Assertions.assertEquals(Subject.of("hello"), result);
        Assertions.assertSame(subject, result);
    }

    @Test
    void orElse_shouldReturnFallbackSubject_whenValueIsAbsent() {
        Subject<String> result = Subject.<String>of(null).presence().orElse("fallback");

        Assertions.assertEquals(Subject.of("fallback"), result);
    }

    @Test
    void orElse_shouldChainFurther_afterRecovery() {
        boolean present = Subject.<String>of(null)
                .presence().orElse("fallback")
                .presence().isPresent();

        Assertions.assertTrue(present);
    }
}