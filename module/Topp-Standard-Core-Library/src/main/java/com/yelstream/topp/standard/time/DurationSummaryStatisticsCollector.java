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
