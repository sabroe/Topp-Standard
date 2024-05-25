/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Test of {@link Instants}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-28
 */
@SuppressWarnings("ConstantValue")
@Slf4j
class InstantsTest {
    /**
     * Test of {@link Instants#min(Instant,Instant)}.
     */
    @Test
    void min() {
        Instant now=Instant.now();
        {
            Instant a=null;
            Instant b=null;
            Assertions.assertNull(Instants.min(a,b));
        }
        {
            Instant a=now.plus(1L,ChronoUnit.DAYS);
            Instant b=null;
            Assertions.assertEquals(a,Instants.min(a,b));
            Assertions.assertEquals(a,Instants.min(b,a));
        }
        {
            Instant a=now.plus(1L,ChronoUnit.DAYS);
            Instant b=now.plus(2L,ChronoUnit.DAYS);
            Assertions.assertEquals(a,Instants.min(a,b));
            Assertions.assertEquals(a,Instants.min(b,a));
        }
    }

    /**
     * Test of {@link Instants#max(Instant,Instant)}.
     */
    @Test
    void max() {
        Instant now=Instant.now();
        {
            Instant a=null;
            Instant b=null;
            Assertions.assertNull(Instants.max(a,b));
        }
        {
            Instant a=now.plus(1L,ChronoUnit.DAYS);
            Instant b=null;
            Assertions.assertEquals(a,Instants.max(a,b));
            Assertions.assertEquals(a,Instants.max(b,a));
        }
        {
            Instant a=now.plus(1L,ChronoUnit.DAYS);
            Instant b=now.plus(2L,ChronoUnit.DAYS);
            Assertions.assertEquals(b,Instants.max(a,b));
            Assertions.assertEquals(b,Instants.max(b,a));
        }

    }

    /**
     * Test of {@link Instants#equals(Instant,Instant)}.
     */
    @Test
    void equals() {
        Instant now=Instant.now();
        {
            Instant a=null;
            Instant b=null;
            Assertions.assertFalse(Instants.equals(a,b));
        }
        {
            Instant a=now.plus(1L,ChronoUnit.DAYS);
            Instant b=null;
            Assertions.assertFalse(Instants.equals(a,b));
            Assertions.assertFalse(Instants.equals(b,a));
        }
        {
            Instant a=now.plus(1L,ChronoUnit.DAYS);
            Instant b=now.plus(2L,ChronoUnit.DAYS);
            Assertions.assertFalse(Instants.equals(a,b));
            Assertions.assertFalse(Instants.equals(b,a));
        }
        {
            Instant a=now.plus(5L,ChronoUnit.DAYS);
            Instant b=now.plus(5L,ChronoUnit.DAYS);
            Assertions.assertNotSame(a,b);
            Assertions.assertTrue(Instants.equals(a,b));
            Assertions.assertTrue(Instants.equals(b,a));
        }
    }
}
