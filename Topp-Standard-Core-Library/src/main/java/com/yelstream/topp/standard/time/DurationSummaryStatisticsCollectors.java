package com.yelstream.topp.standard.time;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Collector;

@UtilityClass
public class DurationSummaryStatisticsCollectors {

/*
    public static Collector<Duration,DurationSummaryStatistics,Optional<DurationSummaryStatistics>> toOptional() {
        return DurationSummaryStatisticsCollector.of();
    }
*/
    public static Collector<Duration,DurationSummaryStatistics,DurationSummaryStatistics> toOptional() {
        return DurationSummaryStatisticsCollector.of();
    }

}
