/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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
    /**
     * Number of durations this summarizes.
     */
    private long count;

    /**
     * Sum of all durations.
     */
    private Duration sum;

    /**
     * Minimum of all durations.
     */
    private Duration min;

    /**
     * Maximum of all durations.
     */
    private Duration max;

    /**
     * Constructor.
     * @param count Number of durations.
     * @param min Minimum duration.
     * @param max Maximum duration.
     * @param sum Durations summarized.
     */
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

    /**
     * Indicates, if this is valid.
     * @return Indicates, if this is valid.
     */
    public boolean isValid() {
        return count>0L;
    }

    /**
     * Accept a duration into the statistics.
     * @param duration Duration.
     *                 This may be {@code null}.
     */
    public void accept(Duration duration) {
        if (duration!=null) {
            count++;
            sum=Durations.sum(sum,duration);
            min=Durations.min(min,duration);
            max=Durations.max(max,duration);
        }
    }

    /**
     * Combine this with another statistics object.
     * @param other Another statistics object.
     *              This may be {@code null}.
     */
    public void combine(DurationSummaryStatistics other) {
        if (other!=null) {
            count+=other.count;
            sum=Durations.sum(sum,other.sum);
            min=Durations.min(min,other.min);
            max=Durations.max(max,other.max);
        }
    }

    /**
     * Gets the average duration.
     * @return Average duration.
     */
    @ToString.Include(name="average")
    public Duration getAverage() {
        return count==0?null:sum.dividedBy(count);
    }

    /**
     * Creates duration statistics from a single durations.
     * @param duration Duration.
     * @return Duration statistics.
     */
    public static DurationSummaryStatistics of(Duration duration) {
        DurationSummaryStatistics summaryStatistics=new DurationSummaryStatistics();
        summaryStatistics.accept(duration);
        return summaryStatistics;
    }

    /**
     * Creates duration statistics from a stream of durations.
     * @param stream Stream of durations.
     * @return Duration statistics.
     */
    public static DurationSummaryStatistics of(Stream<Duration> stream) {
        return stream.collect(DurationSummaryStatistics::new,
                              DurationSummaryStatistics::accept,
                              DurationSummaryStatistics::combine);
    }
}
