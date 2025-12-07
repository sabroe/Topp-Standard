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

package com.yelstream.topp.standard.util.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Test of {@link Callables}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-11-11
 */
class CallablesTest {

    /**
     * Test of {@link Callables#requireNonException(Callable)}
     */
    @Test
    void requireNonExceptionTest() {
        Assertions.assertDoesNotThrow(()-> {
            Callables.requireNonException(()->0);
        });

        Assertions.assertThrows(IllegalStateException.class,()-> {
            Callables.requireNonException(()->{ throw new IOException("Err!"); });
        });
    }
}
