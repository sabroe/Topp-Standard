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

import com.yelstream.topp.standard.lang.thread.Threads;
import com.yelstream.topp.standard.time.DurationSummaryStatistics;
import com.yelstream.topp.standard.time.DurationSummaryStatisticsCollector;
import com.yelstream.topp.standard.time.Durations;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.InstantSource;
import java.time.temporal.ChronoUnit;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link DurationWatch}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@UtilityClass
public class DurationWatches {
    /**
     * Creates a timer.
     * @param source Source of instants.
     * @param durationScale Applied scaling.
     *                      This could be e.g. 10x and to let time pass on ten times faster than normal.
     * @return Created timer.
     */
    public static DurationWatch of(NanoTimeSource source,
                                   LongUnaryOperator durationScale) {
        return DefaultDurationWatch.of(source::nanoTime,ChronoUnit.NANOS,durationScale);
    }

    /**
     * Creates a timer.
     * @param source Source of instants.
     * @param durationScale Applied scaling.
     *                      This could be e.g. 10x and to let time pass on ten times faster than normal.
     * @return Created timer.
     */
    public static DurationWatch of(InstantSource source,
                                   LongUnaryOperator durationScale) {
        return DefaultDurationWatch.of(source::millis,ChronoUnit.MILLIS,durationScale);
    }

/*
    1) Uret - 'watch'
    2) Parametrene til uret
    3) De målte resultater
    4) Alt sammenhørende
*/

    @UtilityClass
    public static class Data {

        @lombok.Builder(builderClassName="Builder",toBuilder=true)
        @AllArgsConstructor(staticName="of")
        public static class Source {
          private final NanoTimeSource nanoTimeSource;
          private final InstantSource instantSource;
          private final DurationWatch watch;
        }

        @lombok.Builder(builderClassName="Builder",toBuilder=true)
        @AllArgsConstructor(staticName="of")
        public static class Input {
            private final long sleepInMs;
            private final int repetitions;
        }

        @lombok.Builder(builderClassName="Builder",toBuilder=true)
        @AllArgsConstructor(staticName="of")
        public static class Characteristics {
            //private final String name;
            private final DurationWatch watch;
            private final Duration absoluteDivergence;  //Around 1 ms?
            private final DurationSummaryStatistics summaryStatistics;
        }
    }

    /**
     * Measures the duration of executing an action.
     * @param watch Watch.
     * @param action Action.
     * @return Duration measured.
     */
    public static Duration measure(DurationWatch watch,
                                   Runnable action) {
        DurationWatch.Timer durationTimer=watch.start();
        action.run();
        DurationWatch.Time durationTime=durationTimer.stop();
        return durationTime.toDuration();
    }

    /**
     * Measures the duration of putting the current thread to sleep.
     * @param watch Watch.
     * @param sleep Requested sleep duration.
     * @return Measured sleep duration.
     */
    public static Duration measureSleep(DurationWatch watch,
                                        Duration sleep) {
        return measure(watch,()->Threads.sleep(sleep));
    }


    public static Duration estimateSleepDivergence(DurationWatch watch,
                                                   Duration minDuration,
                                                   Duration maxDuration,
                                                   int repeatCount) {
        Supplier<Duration> durationSupplier=Durations.randomSupplier(minDuration,maxDuration);
        Stream<Duration> durationStream=IntStream.range(0,repeatCount).mapToObj(i->durationSupplier.get());
        Stream<Duration> statStream=durationStream.map(duration->measureSleep(watch,duration));
        DurationSummaryStatistics summaryStatistics=statStream.collect(DurationSummaryStatisticsCollector.of());

        return summaryStatistics.getAverage();
    }

    public static DurationSummaryStatistics stat(DurationWatch watch,
                                                 Duration sleep,
                                                 int repetitions) {
        return
            IntStream.range(0,repetitions).mapToObj(i->{
                DurationWatch.Timer durationTimer=watch.start();
                Threads.sleep(sleep);
                DurationWatch.Time durationTime=durationTimer.stop();
                return durationTime.toDuration();
            }).collect(DurationSummaryStatisticsCollector.of());
    }




}
