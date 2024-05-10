package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.time.DurationSummaryStatistics;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.InstantSource;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

/**
 * Test of {@link DurationWatches}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@Slf4j
class DurationWatchesTest {


    @UtilityClass
    public static class Data {
        @lombok.Builder(builderClassName = "Builder", toBuilder = true)
        @AllArgsConstructor(staticName = "of")
        public static class Source {
            private final NanoTimeSource nanoTimeSource;
            private final InstantSource instantSource;
            private final DurationWatch watch;
        }

        @lombok.Builder(builderClassName = "Builder", toBuilder = true)
        @AllArgsConstructor(staticName = "of")
        public static class Input {
             private final long sleepInMs;
             private final int repetitions;
        }

        @lombok.Builder(builderClassName = "Builder", toBuilder = true)
        @AllArgsConstructor(staticName = "of")
        public static class Output {
             private final DurationSummaryStatistics summaryStatistics;
        }
     }

    static class BlankStringsArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(Duration.ZERO, 20),
                Arguments.of(Duration.ofNanos(1L * 1000L), 100),
                Arguments.of(Duration.ofNanos(5L * 1000L), 100),
                Arguments.of(Duration.ofNanos(10L * 1000L), 100),
                Arguments.of(Duration.ofNanos(50L * 1000L), 100),
                Arguments.of(Duration.ofNanos(100L * 1000L), 100),
                Arguments.of(Duration.ofNanos(250L * 1000L), 100),
                Arguments.of(Duration.ofNanos(500L * 1000L), 100),
                Arguments.of(Duration.ofMillis(1L), 20),
                Arguments.of(Duration.ofMillis(2L), 20),
                Arguments.of(Duration.ofMillis(3L), 20),
                Arguments.of(Duration.ofMillis(4L), 20),
                Arguments.of(Duration.ofMillis(5L), 20),
                Arguments.of(Duration.ofMillis(10L), 20),
                Arguments.of(Duration.ofMillis(20L), 20),
                Arguments.of(Duration.ofMillis(30L), 20),
                Arguments.of(Duration.ofMillis(50L), 20),
                Arguments.of(Duration.ofMillis(100L), 20)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(BlankStringsArgumentsProvider.class)
    @Order(1)
    void machineTimeSleep(Duration sleep,
                          int repetitions) {
        NanoTimeSource source = NanoTimeSource.system();
        DurationWatch watch = DurationWatches.of(source, LongUnaryOperator.identity());

        DurationSummaryStatistics statistics =
            DurationWatches.stat(watch,
                                 sleep,
                                 repetitions);
        Duration absoluteDivergence = statistics.getAverage().minus(sleep);
        double relativeDivergence = (double) (absoluteDivergence.toNanos()) / sleep.toNanos();
        DecimalFormat format = new DecimalFormat("#0%");
         log.info("Machine Stat: sleep={}, repetitions={}, stat={}, absoluteDivergence={},relativeDivergence={}", sleep, repetitions, statistics, absoluteDivergence, format.format(relativeDivergence));
        System.out.println(statistics);
    }

/*
     @ParameterizedTest
     @ArgumentsSource(BlankStringsArgumentsProvider.class)
     @Order(2)
     void machineTimeSleep(Duration sleep,
                           int repetitions) {
          NanoTimeSource source=NanoTimeSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          sleep,
                          repetitions);
          Duration absoluteDivergence=statistics.getAverage().minus(sleep);
          double relativeDivergence=(double)(absoluteDivergence.toNanos())/sleep.toNanos();
          DecimalFormat format=new DecimalFormat("#0%");
          log.info("Machine Stat: sleep={}, repetitions={}, stat={}, absoluteDivergence={},relativeDivergence={}",sleep,repetitions,statistics,absoluteDivergence,format.format(relativeDivergence));
          System.out.println(statistics);
     }
*/

    void humanTimeSleep100() {
        InstantSource source = InstantSource.system();
        DurationWatch watch = DurationWatches.of(source, LongUnaryOperator.identity());

        DurationSummaryStatistics statistics =
            DurationWatches.stat(watch,
                        Duration.ofMillis(100L),
                        10);
        log.info("Human Stat 100: {}", statistics);
        System.out.println(statistics);
    }

    @ParameterizedTest(name="{index} {0} is 30 days long")
    @EnumSource(value=StandardSleepDurationRange.class)
    void estimateDivergenceOnSleepWithNanoTimeSource(StandardSleepDurationRange durationRange) {
        NanoTimeSource source=NanoTimeSource.system();
        DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

        Duration estimateSleepDivergence=durationRange.estimateSleepDivergence(watch);

        System.out.println("durationRange: "+durationRange+", estimateSleepDivergence: "+estimateSleepDivergence);

        Assertions.assertTrue(true);
    }

    @ParameterizedTest(name="{index} {0} is 30 days long")
    @EnumSource(value=StandardSleepDurationRange.class)
    void estimateDivergenceOnSleepWithInstantSource(StandardSleepDurationRange durationRange) {
        InstantSource source=InstantSource.system();
        DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

        Duration estimateSleepDivergence=durationRange.estimateSleepDivergence(watch);

        System.out.println("durationRange: "+durationRange+", estimateSleepDivergence: "+estimateSleepDivergence);

        Assertions.assertTrue(true);
    }
}
