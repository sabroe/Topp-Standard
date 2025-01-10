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

package com.yelstream.topp.standard.time.watch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
@RequiredArgsConstructor
@SuppressWarnings("java:S115")
public enum StandardSleepDurationRange {
    /**
     * {@code [1 us..1 ms]}
     */
    MicroSeconds(Duration.ofNanos(1000L),Duration.ofMillis(1L)),

    /**
     * {@code [100 us..3 ms]}
     */
    Ambitious(Duration.ofNanos(10L*1000L),Duration.ofMillis(5L)),

    /**
     * {@code [1 ms..10 ms]}
     */
    FewMiliseconds(Duration.ofMillis(1L),Duration.ofMillis(10L)),

    /**
     * {@code [1 ms..100 ms]}
     */
    Normal(Duration.ofMillis(1L),Duration.ofMillis(100L));

    private final Duration minDuration;
    private final Duration maxDuration;

    public Duration estimateSleepDivergence(DurationWatch watch) {
        Duration average=minDuration.plus(maxDuration).dividedBy(2L);
        int repetitionCount=Math.min(3,(int)(ChronoUnit.SECONDS.getDuration().toNanos()/average.toNanos()));
        return estimateSleepDivergence(watch,repetitionCount);
    }

    public Duration estimateSleepDivergence(DurationWatch watch,
                                            int repetitionCount) {
        return DurationWatches.estimateSleepDivergence(watch,minDuration,maxDuration,repetitionCount);
    }
}
