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

package com.yelstream.topp.standard.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;

/**
 * Test of {@link Durations}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-28
 */
@SuppressWarnings("ConstantValue")
@Slf4j
class DurationsTest {
    /**
     * Test of {@link Durations#of(TemporalUnit)}.
     */
    @Test
    void ofTemporalUnit() {
        Duration d=Durations.of(ChronoUnit.DAYS);
        Assertions.assertNotNull(d);
        Assertions.assertEquals(Duration.ofDays(1L),d);
    }

    /**
     * Test of {@link Durations#of(TimeUnit)}.
     */
    @Test
    void ofTimeUnit() {
        Duration d=Durations.of(TimeUnit.DAYS);
        Assertions.assertNotNull(d);
        Assertions.assertEquals(Duration.ofDays(1L),d);
    }

    /**
     * Test of {@link Durations#min(Duration,Duration)}.
     */
    @Test
    void min() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertNull(Durations.min(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertEquals(a,Durations.min(a,b));
            Assertions.assertEquals(a,Durations.min(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertEquals(a,Durations.min(a,b));
            Assertions.assertEquals(a,Durations.min(b,a));
        }
    }

    /**
     * Test of {@link Durations#max(Duration,Duration)}.
     */
    @Test
    void max() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertNull(Durations.max(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertEquals(a,Durations.max(a,b));
            Assertions.assertEquals(a,Durations.max(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertEquals(b,Durations.max(a,b));
            Assertions.assertEquals(b,Durations.max(b,a));
        }

    }

    /**
     * Test of {@link Durations#equals(Duration,Duration)}.
     */
    @Test
    void equals() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertFalse(Durations.equals(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertFalse(Durations.equals(a,b));
            Assertions.assertFalse(Durations.equals(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertFalse(Durations.equals(a,b));
            Assertions.assertFalse(Durations.equals(b,a));
        }
        {
            Duration a=Duration.ofDays(5L);
            Duration b=Duration.ofDays(5L);
            Assertions.assertNotSame(a,b);
            Assertions.assertTrue(Durations.equals(a,b));
            Assertions.assertTrue(Durations.equals(b,a));
        }
    }

    /**
     * Test of {@link Durations#sum(Duration,Duration)}.
     */
    @Test
    void sum() {
        {
            Duration a=null;
            Duration b=null;
            Assertions.assertNull(Durations.sum(a,b));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=null;
            Assertions.assertEquals(a,Durations.sum(a,b));
            Assertions.assertEquals(a,Durations.sum(b,a));
        }
        {
            Duration a=Duration.ofDays(1L);
            Duration b=Duration.ofDays(2L);
            Assertions.assertEquals(Duration.ofDays(3L),Durations.sum(a,b));
            Assertions.assertEquals(Duration.ofDays(3L),Durations.sum(b,a));
        }
    }

    /**
     * Test of {@link Durations#isInfinite(Duration)}.
     */
    @Test
    void isInfinite() {
        Assertions.assertFalse(Durations.isInfinite(null));
        Assertions.assertFalse(Durations.isInfinite(Duration.ofDays(1L)));
        Assertions.assertFalse(Durations.isInfinite(Duration.ofDays(-1L)));
        Assertions.assertTrue(Durations.isInfinite(Durations.MIN_VALUE));
        Assertions.assertTrue(Durations.isInfinite(Durations.MAX_VALUE));
    }


    /**
     * Test of {@link Durations#isFinite(Duration)}.
     */
    @Test
    void isFinite() {
        Assertions.assertFalse(Durations.isFinite(null));
        Assertions.assertTrue(Durations.isFinite(Duration.ofDays(1L)));
        Assertions.assertTrue(Durations.isFinite(Duration.ofDays(-1L)));
        Assertions.assertFalse(Durations.isFinite(Durations.MIN_VALUE));
        Assertions.assertFalse(Durations.isFinite(Durations.MAX_VALUE));
    }

    /**
     * Test of {@link Durations#randomSupplier(RandomGenerator, Duration, Duration)}.
     */
    @Test
    void randomSupplier1() {
        Duration a=Duration.ofDays(-10);
        Duration b=Duration.ofDays(100);
        Supplier<Duration> s=Durations.randomSupplier(new SecureRandom(),a,b);

        Duration lastRandom=null;
        for (int i=0; i<100; i++) {
            Duration random=s.get();
            Assertions.assertNotNull(random);
            Assertions.assertTrue(a.compareTo(random)<=0);
            Assertions.assertTrue(random.compareTo(b)<=0);
            Assertions.assertFalse(Durations.equals(lastRandom,random));
            lastRandom=random;
        }
    }

    /**
     * Test of {@link Durations#randomSupplier(Duration, Duration)}.
     */
    @Test
    void randomSupplier2() {
        Duration a=Duration.ofDays(-10);
        Duration b=Duration.ofDays(100);
        Supplier<Duration> s=Durations.randomSupplier(a,b);

        Duration lastRandom=null;
        for (int i=0; i<100; i++) {
            Duration random=s.get();
            Assertions.assertNotNull(random);
            Assertions.assertTrue(a.compareTo(random)<=0);
            Assertions.assertTrue(random.compareTo(b)<=0);
            Assertions.assertFalse(Durations.equals(lastRandom,random));
            lastRandom=random;
        }
    }
}
