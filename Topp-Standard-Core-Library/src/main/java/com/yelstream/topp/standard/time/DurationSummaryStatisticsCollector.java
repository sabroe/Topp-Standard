package com.yelstream.topp.standard.time;

import lombok.AllArgsConstructor;

import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@AllArgsConstructor(staticName="of")
public class DurationSummaryStatisticsCollector implements Collector<Duration,DurationSummaryStatistics,DurationSummaryStatistics> {
    @Override
    public Supplier<DurationSummaryStatistics> supplier() {
        return DurationSummaryStatistics::new;
    }

    @Override
    public BiConsumer<DurationSummaryStatistics, Duration> accumulator() {
        return DurationSummaryStatistics::accept;
    }

    @Override
    public BinaryOperator<DurationSummaryStatistics> combiner() {
        return (a,b) -> {
            a.combine(b);
            return a;
        };
    }

    @Override
    public Function<DurationSummaryStatistics,DurationSummaryStatistics> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH);
    }
}
