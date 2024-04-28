package com.yelstream.topp.standard.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

/**
 * Test of {@link DurationSummaryStatisticsCollectorTest}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-28
 */
@Slf4j
class DurationSummaryStatisticsCollectorTest {
    /**
     * Test of {@link DurationSummaryStatisticsCollector#supplier()}.
     */
    @Test
    void supplier() {
        List<Duration> l=List.of();
        @SuppressWarnings("RedundantOperationOnEmptyContainer")
        DurationSummaryStatistics summaryStatistics=l.stream().collect(DurationSummaryStatisticsCollector.of());
        Assertions.assertNotNull(summaryStatistics);
        Assertions.assertFalse(summaryStatistics.isValid());
    }

    /**
     * Test of {@link DurationSummaryStatisticsCollector#accumulator()}.
     */
    @Test
    void accumulator() {
        List<Duration> l=List.of(Duration.ofDays(1L));
        DurationSummaryStatistics summaryStatistics=l.stream().collect(DurationSummaryStatisticsCollector.of());
        Assertions.assertNotNull(summaryStatistics);
        Assertions.assertTrue(summaryStatistics.isValid());
        Assertions.assertEquals(Duration.ofDays(1L),summaryStatistics.getAverage());
    }

    /**
     * Test of {@link DurationSummaryStatisticsCollector#combiner()}.
     */
    @Test
    void combiner() {
        DurationSummaryStatisticsCollector collector=DurationSummaryStatisticsCollector.of();
        DurationSummaryStatistics s1=new DurationSummaryStatistics();
        s1.accept(Duration.ofDays(1L));
        DurationSummaryStatistics s2=new DurationSummaryStatistics();
        s2.accept(Duration.ofDays(2L));

        DurationSummaryStatistics combined=collector.combiner().apply(s1,s2);

        Assertions.assertEquals(2,combined.getCount());
        Assertions.assertEquals(Duration.ofDays(3L),combined.getSum());
    }

    @Test
    void finisher() {
        DurationSummaryStatisticsCollector collector=DurationSummaryStatisticsCollector.of();
        final DurationSummaryStatistics value=collector.supplier().get();
        collector.accumulator().accept(value,Duration.ofDays(5L));
        DurationSummaryStatistics result=collector.finisher().apply(value);
        Assertions.assertEquals(1,result.getCount());
        Assertions.assertEquals(Duration.ofDays(5L),result.getSum());
    }
}
