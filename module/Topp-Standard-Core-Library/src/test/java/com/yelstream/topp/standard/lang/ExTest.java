/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Test of {@link Ex}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-12
 */
class ExTest {
    /**
     * Tests {@link Ex#n(Supplier)}.
     */
    @Test
    void create() {
        {
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.n(IOException::new).build();
                });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.n(IOException::new).message("Failure to XXX!").build();
                });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            Throwable cause = new IllegalArgumentException();
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.n(IOException::new).message("Failure to XXX!").cause(cause).build();
                });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertEquals(cause,ex.getCause());
        }
    }

    /**
     * Tests {@link Ex#m(Function)}.
     */
    @Test
    void createByMessage() {
        {
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.m(IOException::new).build();
                });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.m(IOException::new).message("Failure to XXX!").build();
                });
            Assertions.assertEquals("Failure to XXX!",ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            Throwable cause = new IllegalArgumentException();
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.m(IOException::new).message("Failure to XXX!").cause(cause).build();
                });
            Assertions.assertEquals("Failure to XXX!",ex.getMessage());
            Assertions.assertEquals(cause,ex.getCause());
        }
    }

    /**
     * Tests {@link Ex#c(Function)}.
     */
    @Test
    void createByCause() {
        {
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.c(IOException::new).build();
                });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.c(IOException::new).message("Failure to XXX!").build();
                });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            Throwable cause = new IllegalArgumentException();
            IOException ex =
                Assertions.assertThrows(IOException.class, () -> {
                    throw Ex.c(IOException::new).message("Failure to XXX!").cause(cause).build();
                });
            Assertions.assertEquals(cause.getClass().getName(),ex.getMessage());  //Note: This outcome is determined by the code in 'Throwable#Throwable(Throwable)}'!
            Assertions.assertEquals(cause,ex.getCause());
        }
    }

    /**
     * Tests {@link Ex#mc(BiFunction)}.
     */
    @Test
    void createByMessageAndCause() {
        {
            IOException ex =
                    Assertions.assertThrows(IOException.class, () -> {
                        throw Ex.mc(IOException::new).build();
                    });
            Assertions.assertNull(ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            IOException ex =
                    Assertions.assertThrows(IOException.class, () -> {
                        throw Ex.mc(IOException::new).message("Failure to XXX!").build();
                    });
            Assertions.assertEquals("Failure to XXX!",ex.getMessage());
            Assertions.assertNull(ex.getCause());
        }
        {
            Throwable cause = new IllegalArgumentException();
            IOException ex =
                    Assertions.assertThrows(IOException.class, () -> {
                        throw Ex.mc(IOException::new).message("Failure to XXX!").cause(cause).build();
                    });
            Assertions.assertEquals("Failure to XXX!",ex.getMessage());
            Assertions.assertEquals(cause,ex.getCause());
        }
    }
}
