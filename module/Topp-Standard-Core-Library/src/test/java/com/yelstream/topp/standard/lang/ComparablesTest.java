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

package com.yelstream.topp.standard.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * Test of {@link Comparables}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@SuppressWarnings("ConstantValue")
@Slf4j
class ComparablesTest {
    /**
     * Test of {@link Comparables#min(Comparable,Comparable)}.
     */
    @Test
    void min() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertNull(Comparables.min(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertEquals(a,Comparables.min(a,b));
            Assertions.assertEquals(a,Comparables.min(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertEquals(a,Comparables.min(a,b));
            Assertions.assertEquals(a,Comparables.min(b,a));
        }
    }

    /**
     * Test of {@link Comparables#max(Comparable,Comparable)}.
     */
    @Test
    void max() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertNull(Comparables.max(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertEquals(a,Comparables.max(a,b));
            Assertions.assertEquals(a,Comparables.max(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertEquals(b,Comparables.max(a,b));
            Assertions.assertEquals(b,Comparables.max(b,a));
        }

    }

    /**
     * Test of {@link Comparables#equals(Comparable,Comparable)}.
     */
    @Test
    void equals() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertFalse(Comparables.equals(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertFalse(Comparables.equals(a,b));
            Assertions.assertFalse(Comparables.equals(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertFalse(Comparables.equals(a,b));
            Assertions.assertFalse(Comparables.equals(b,a));
        }
        {
            Duration a=Duration.ofDays(5L);
            Duration b=Duration.ofDays(5L);
            Assertions.assertNotSame(a,b);
            Assertions.assertTrue(Comparables.equals(a,b));
            Assertions.assertTrue(Comparables.equals(b,a));
        }
    }
}
