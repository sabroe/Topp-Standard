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

package com.yelstream.topp.standard.util.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 * Test of {@link LongRange}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2023-12-17
 */
class LongRangeTest {
    /**
     * Test {@link LongRange#of(LongStream)}.
     */
    @Test
    void rangeOfStreamTest() {
        LongStream stream=LongStream.range(0L,100L);
        LongRange range=LongRange.of(stream);
        Assertions.assertEquals(LongRange.of(0L,100L),range);
    }

    /**
     * Test {@link LongRange#contains(long)}.
     */
    @Test
    void containsTest() {
        LongRange range=LongRange.of(0L,100L);
        Assertions.assertFalse(range.contains(-1L));
        Assertions.assertTrue(range.contains(0L));
        Assertions.assertTrue(range.contains(50L));
        Assertions.assertTrue(range.contains(99L));
        Assertions.assertFalse(range.contains(100L));
    }

    /**
     * Test {@link LongRange#expand(long)}.
     */
    @Test
    void expandTest() {
        LongRange range=LongRange.of(0L,100L);
        range=range.expand(199L);
        range=range.expand(-500L);
        Assertions.assertEquals(LongRange.of(-500L,200L),range);
    }

    /**
     * Test {@link LongRange#narrow(LongPredicate)}.
     */
    @Test
    void narrowTest() {
        LongRange range=LongRange.of(0,100);
        range=range.narrow(i->(i==10 || i==50 || i==90));
        Assertions.assertEquals(LongRange.of(10L,91L),range);
    }

    /**
     * Test {@link LongRange#stream()}.
     */
    @Test
    void streamTest() {
        LongRange range=LongRange.of(0L,5L);
        LongStream stream=range.stream();
        List<Long> list=stream.boxed().toList();
        Assertions.assertEquals(List.of(0L,1L,2L,3L,4L),list);
    }
}
