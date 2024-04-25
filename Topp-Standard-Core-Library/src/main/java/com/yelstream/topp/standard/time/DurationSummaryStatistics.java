package com.yelstream.topp.standard.time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * Summary statistics for a stream of {@link Duration} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-25
 */@Getter
@ToString
@NoArgsConstructor
public class DurationSummaryStatistics {
    private long count;
    private Duration sum;
    private Duration min;
    private Duration max;

    public DurationSummaryStatistics(long count,
                                     Duration min,
                                     Duration max,
                                     Duration sum) {
        if (count<=0L) {
            throw new IllegalArgumentException(String.format("Failure to create object; 'count' %d must be positive!", count));
        }
        this.count=count;
        this.sum=sum;
        this.min=min;
        this.max=max;
    }

    public boolean isValid() {
        return count>0L;
    }

    public void accept(Duration duration) {
        count++;
        sum=Durations.sum(sum,duration);
        min=Durations.min(min,duration);
        max=Durations.max(max,duration);
    }

    public void combine(DurationSummaryStatistics other) {
        count+=other.count;
        sum=Durations.sum(sum,other.sum);
        min=Durations.min(min,other.min);
        max=Durations.max(max,other.max);
    }

    @ToString.Include(name="average")
    public Duration getAverage() {
        return count==0?null:sum.dividedBy(count);
    }

    public static DurationSummaryStatistics of(Stream<Duration> stream) {
        return stream.collect(DurationSummaryStatistics::new,
                              DurationSummaryStatistics::accept,
                              DurationSummaryStatistics::combine);
    }
}
