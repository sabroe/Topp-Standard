package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.lang.thread.Threads;
import com.yelstream.topp.standard.time.DurationStreams;
import com.yelstream.topp.standard.time.DurationSummaryStatistics;
import com.yelstream.topp.standard.time.DurationSummaryStatisticsCollector;
import com.yelstream.topp.standard.time.DurationSummaryStatisticsCollectors;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.InstantSource;
import java.time.temporal.ChronoUnit;
import java.util.LongSummaryStatistics;
import java.util.Optional;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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




    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor(staticName="of")
    public static class DurationWatchStat {
        private final DurationWatch watch;
        private final long sleepInMs;
        private final int repetitions;
        private final LongSummaryStatistics summaryStatistics;
    }

    public static DurationSummaryStatistics stat(DurationWatch watch,
                                                 long sleepInMs,
                                                 int repetitions) {

//        Optional<DurationSummaryStatistics> x=
        DurationSummaryStatistics x=
            IntStream.range(0,repetitions).mapToObj(i->{
                DurationWatch.Timer durationTimer=watch.start();
                if (sleepInMs>0) {
                    Threads.sleep(sleepInMs);
                }
                DurationWatch.Time durationTime = durationTimer.stop();
                return durationTime.toDuration();
            }).collect(DurationSummaryStatisticsCollector.of()/*DurationSummaryStatisticsCollectors.toOptional()*/);

        return x.toOptional().get();
//        return x.get();

//        return x;
//        return null;//x.orElse(null);
    }




}
