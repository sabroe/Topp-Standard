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
import java.util.random.RandomGenerator;

/**
 * Test of {@link RandomDurationGenerator}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-26
 */
@Slf4j
class RandomDurationGeneratorTest {
    /**
     * Tests {@link RandomDurationGenerator#of(RandomGenerator,Duration,Duration)}.
     */
    @SuppressWarnings("java:S5778")
    @Test
    void noArgsConstructor() {
        {
            RandomDurationGenerator g=RandomDurationGenerator.of(new SecureRandom(),Duration.ofSeconds(1L),Duration.ofSeconds(2L));

            Assertions.assertNotNull(g);
            Duration d=g.nextDuration();
            Assertions.assertNotNull(d);
            Assertions.assertTrue(Duration.ofSeconds(1L).compareTo(d)<=0);
            Assertions.assertTrue(d.compareTo(Duration.ofSeconds(2L))<=0);
        }
        {
            Assertions.assertThrows(IllegalArgumentException.class,()-> {
                RandomDurationGenerator.of(new SecureRandom(),Duration.ofSeconds(2L),Duration.ofSeconds(1L));
            });
        }
        {
            RandomDurationGenerator g=RandomDurationGenerator.of(new SecureRandom(),Duration.of(1000L*365L,ChronoUnit.DAYS),Duration.of(2000L*365L,ChronoUnit.DAYS));

            Assertions.assertNotNull(g);
            Duration d=g.nextDuration();
            Assertions.assertNotNull(d);
            Assertions.assertTrue(Duration.of(1000L*365L,ChronoUnit.DAYS).compareTo(d)<=0);
            Assertions.assertTrue(d.compareTo(Duration.of(2000L*365L,ChronoUnit.DAYS))<=0);
        }
    }

    /**
     * Tests basic randomness.
     */
    @SuppressWarnings("java:S5778")
    @Test
    void random() {
        Duration a=Duration.ofDays(-10);
        Duration b=Duration.ofDays(100);
        RandomDurationGenerator g=RandomDurationGenerator.of(a,b);

        Duration lastRandom=null;
        for (int i=0; i<100; i++) {
            Duration random=g.nextDuration();
            Assertions.assertNotNull(random);
            Assertions.assertTrue(a.compareTo(random)<=0);
            Assertions.assertTrue(random.compareTo(b)<=0);
            Assertions.assertFalse(Durations.equals(lastRandom,random));
            lastRandom=random;
        }
    }
}
