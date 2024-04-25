package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.time.DurationSummaryStatistics;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.InstantSource;
import java.util.LongSummaryStatistics;
import java.util.function.LongUnaryOperator;

/**
 * Test of {@link DurationWatches}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@Slf4j
class DurationWatchesTest {




     @Test
    void machineTimeSleep0() {
         NanoTimeSource source=NanoTimeSource.system();
         DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

         DurationSummaryStatistics statistics=
             DurationWatches.stat(watch,
                         0L,
                         100);
         log.info("Machine Stat 0: {}",statistics);
System.out.println(statistics);
     }

     @Test
     void machineTimeSleep10() {
          NanoTimeSource source=NanoTimeSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          10L,
                          100);
          log.info("Machine Stat 10: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void machineTimeSleep20() {
          NanoTimeSource source=NanoTimeSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          20L,
                          100);
          log.info("Machine Stat 20: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void machineTimeSleep30() {
          NanoTimeSource source=NanoTimeSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          30L,
                          100);
          log.info("Machine Stat 30: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void machineTimeSleep100() {
          NanoTimeSource source=NanoTimeSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          100L,
                          10);
          log.info("Machine Stat 100: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void humanTimeSleep0() {
          InstantSource source=InstantSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          0L,
                          100);
          log.info("Human Stat 0: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void humanTimeSleep10() {
          InstantSource source=InstantSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          10L,
                          100);
          log.info("Human Stat 10: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void humanTimeSleep20() {
          InstantSource source=InstantSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          20L,
                          100);
          log.info("Human Stat 20: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void humanTimeSleep30() {
          InstantSource source=InstantSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          30L,
                          100);
          log.info("Human Stat 30: {}",statistics);
          System.out.println(statistics);
     }

     @Test
     void humanTimeSleep100() {
          InstantSource source=InstantSource.system();
          DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

          DurationSummaryStatistics statistics=
                  DurationWatches.stat(watch,
                          100L,
                          10);
          log.info("Human Stat 100: {}",statistics);
          System.out.println(statistics);
     }


}
