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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Test of {@link DurationSummaryStatistics}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-26
 */
@Slf4j
class DurationSummaryStatisticsTest {

    /**
     * Tests {@link DurationSummaryStatistics#DurationSummaryStatistics()}
     */
    @Test
    void noArgsConstructor() {
        DurationSummaryStatistics statistics=new DurationSummaryStatistics();
        Assertions.assertNotNull(statistics);
        Assertions.assertFalse(statistics.isValid());
        Assertions.assertNull(statistics.getMin());
        Assertions.assertNull(statistics.getMax());
        Assertions.assertNull(statistics.getSum());
        Assertions.assertNull(statistics.getAverage());
    }

    /**
     * Tests {@link DurationSummaryStatistics#DurationSummaryStatistics(long,Duration,Duration,Duration)} 
     */
    @Test
    void requiredArgsConstructor() {
        {
          DurationSummaryStatistics statistics=
              new DurationSummaryStatistics(2,
                                            Duration.ofDays(1),
                                            Duration.ofDays(2),
                                            Duration.ofDays(3));
            Assertions.assertNotNull(statistics);
            Assertions.assertEquals(2,statistics.getCount());
            Assertions.assertTrue(statistics.isValid());
            Assertions.assertEquals(Duration.ofDays(1),statistics.getMin());
            Assertions.assertEquals(Duration.ofDays(2),statistics.getMax());
            Assertions.assertEquals(Duration.ofDays(3),statistics.getSum());
            Assertions.assertEquals(Duration.ofHours(36),statistics.getAverage());
        }
        {
            Assertions.assertThrows(IllegalArgumentException.class,() -> {
                DurationSummaryStatistics statistics=new DurationSummaryStatistics(0,null,null,null);
            });
        }
    }

    /**
     * Tests {@link DurationSummaryStatistics#accept(Duration)}.
     */
    @Test
    void accept() {
        DurationSummaryStatistics statistics=new DurationSummaryStatistics();
        statistics.accept(null);
        statistics.accept(Duration.ofDays(1));
        statistics.accept(Duration.ofDays(2));
        statistics.accept(Duration.ofDays(3));
        statistics.accept(null);

        Assertions.assertNotNull(statistics);
        Assertions.assertEquals(3,statistics.getCount());
        Assertions.assertTrue(statistics.isValid());
        Assertions.assertEquals(Duration.ofDays(1),statistics.getMin());
        Assertions.assertEquals(Duration.ofDays(3),statistics.getMax());
        Assertions.assertEquals(Duration.ofDays(6),statistics.getSum());
        Assertions.assertEquals(Duration.ofHours(48),statistics.getAverage());
    }


    /**
     * Tests {@link DurationSummaryStatistics#combine(DurationSummaryStatistics)}.
     */
    @Test
    void combine() {
        DurationSummaryStatistics statistics=DurationSummaryStatistics.of(Duration.ofDays(1));
        DurationSummaryStatistics other=DurationSummaryStatistics.of(Duration.ofDays(2));
        statistics.combine(other);
        statistics.combine(null);

        Assertions.assertNotNull(statistics);
        Assertions.assertEquals(2,statistics.getCount());
        Assertions.assertTrue(statistics.isValid());
        Assertions.assertEquals(Duration.ofDays(1),statistics.getMin());
        Assertions.assertEquals(Duration.ofDays(2),statistics.getMax());
        Assertions.assertEquals(Duration.ofDays(3),statistics.getSum());
        Assertions.assertEquals(Duration.ofHours(36),statistics.getAverage());
    }

    /**
     * Tests {@link DurationSummaryStatistics#of(Stream)}.
     */
    @Test
    void of() {
        List<Duration> durations=new ArrayList<>();
        durations.add(Duration.ofDays(1));
        durations.add(Duration.ofDays(2));
        durations.add(Duration.ofDays(3));
        durations.add(null);

        DurationSummaryStatistics statistics=DurationSummaryStatistics.of(durations.stream());

        Assertions.assertNotNull(statistics);
        Assertions.assertEquals(3,statistics.getCount());
        Assertions.assertTrue(statistics.isValid());
        Assertions.assertEquals(Duration.ofDays(1),statistics.getMin());
        Assertions.assertEquals(Duration.ofDays(3),statistics.getMax());
        Assertions.assertEquals(Duration.ofDays(6),statistics.getSum());
        Assertions.assertEquals(Duration.ofHours(48),statistics.getAverage());
    }

    /**
     * Tests {@link DurationSummaryStatistics#getAverage()}.
     */
    @Test
    void average() {
        {
            DurationSummaryStatistics statistics=new DurationSummaryStatistics();
            Assertions.assertNotNull(statistics);
            Assertions.assertNull(statistics.getAverage());
        }
        {
            DurationSummaryStatistics statistics=
                new DurationSummaryStatistics(2,
                    Duration.ofDays(1),
                    Duration.ofDays(2),
                    Duration.ofDays(3));
            Assertions.assertNotNull(statistics);
            Assertions.assertEquals(Duration.ofHours(36),statistics.getAverage());
        }
    }
}
