package com.yelstream.topp.standard.time;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.stream.Collector;
import java.util.stream.Stream;

@UtilityClass
public class DurationStreams {


    public static DurationSummaryStatistics collect(Stream<Duration> stream) {
        return stream.collect(DurationSummaryStatistics::new,
                              DurationSummaryStatistics::accept,
                              DurationSummaryStatistics::combine);
    }

    /**
     *
     * @param <R> The type of the result of the reduction operation.
     */
    public static <R> R collect(Stream<Duration> stream,
                                Collector<Duration,DurationSummaryStatistics,R> collector) {
        return stream.collect(collector);
    }



}
