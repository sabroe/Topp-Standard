package com.yelstream.topp.aurora.util.stream;

import com.yelstream.topp.aurora.util.stream.IntRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * Test of {@link IntRange}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2023-12-17
 */
class IntRangeTest {
    /**
     * Test {@link IntRange#of(IntStream)}.
     */
    @Test
    void rangeOfStreamTest() {
        IntStream stream=IntStream.range(0,100);
        IntRange range=IntRange.of(stream);
        Assertions.assertEquals(IntRange.of(0,100),range);
    }

    /**
     * Test {@link IntRange#contains(int)}.
     */
    @Test
    void containsTest() {
        IntRange range=IntRange.of(0,100);
        Assertions.assertFalse(range.contains(-1));
        Assertions.assertTrue(range.contains(0));
        Assertions.assertTrue(range.contains(50));
        Assertions.assertTrue(range.contains(99));
        Assertions.assertFalse(range.contains(100));
    }

    /**
     * Test {@link IntRange#expand(int)}.
     */
    @Test
    void expandTest() {
        IntRange range=IntRange.of(0,100);
        range=range.expand(199);
        range=range.expand(-500);
        Assertions.assertEquals(IntRange.of(-500,200),range);
    }

    /**
     * Test {@link IntRange#narrow(IntPredicate)}.
     */
    @Test
    void narrowTest() {
        IntRange range=IntRange.of(0,100);
        range=range.narrow(i->(i==10 || i==50 || i==90));
        Assertions.assertEquals(IntRange.of(10,91),range);
    }

    /**
     * Test {@link IntRange#stream()}.
     */
    @Test
    void streamTest() {
        IntRange range=IntRange.of(0,5);
        IntStream stream=range.stream();
        List<Integer> list=stream.boxed().toList();
        Assertions.assertEquals(List.of(0,1,2,3,4),list);
    }
}
