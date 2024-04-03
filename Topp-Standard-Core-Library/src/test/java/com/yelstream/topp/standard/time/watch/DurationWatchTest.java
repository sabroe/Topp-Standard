package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.lang.thread.Threads;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.InstantSource;
import java.util.function.LongUnaryOperator;

/**
 * Test of {@link DurationWatch} and {@link DurationWatches}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@Slf4j
class DurationWatchTest {

    long getExpectedOffset(DurationWatch watch,
                           long sleepInMs) {
        long accumulatedDrift=0;
        long minDrift=Long.MAX_VALUE;
        long maxDrift=0;
        int count=20;
        for (int i=0; i<count; i++) {
            DurationWatch.Timer durationTimer = watch.start();
            Threads.sleep(sleepInMs);
            DurationWatch.Time durationTime = durationTimer.stop();

            Duration actualTime=durationTime.toDuration();

            long drift=actualTime.toMillis()-sleepInMs;
            accumulatedDrift+=drift;
            minDrift=Math.min(minDrift,drift);
            maxDrift=Math.max(maxDrift,drift);

        }

        long averageDrift=(accumulatedDrift+count/2)/count;

        log.info("Min. offset = {} ms",minDrift);
        log.info("Max. offset = {} ms",maxDrift);
        log.info("Avg. offset = {} ms",averageDrift);

//        return averageDrift;
        return maxDrift;
    }

    @Test
    void watchSpeed() {
        LongUnaryOperator durationSpeed=t->t*10;  //t->(t+48L/2L)/48L
        //Duration unscaledExpectedTime=Duration.ofSeconds(2);
        Duration unscaledExpectedTime=Duration.ofMillis(1000);

        NanoTimeSource source=NanoTimeSource.system();
//        InstantSource source=InstantSource.system();
        DurationWatch watch=DurationWatches.createDurationWatch(source,durationSpeed);

        long drift=5L;
        long scaledDrift=durationSpeed.applyAsLong(drift);

        long offset=getExpectedOffset(DurationWatches.createDurationWatch(source,LongUnaryOperator.identity()),unscaledExpectedTime.toMillis());
        log.info("Intermediate result, offset = {} ms",offset);

        for (int i=0; i<100; i++) {
            DurationWatch.Timer durationTimer = watch.start();
            Threads.sleep(unscaledExpectedTime);
            DurationWatch.Time durationTime = durationTimer.stop();

            Duration actualTime = durationTime.toDuration();


            long expectedTimeInMillis = durationSpeed.applyAsLong(unscaledExpectedTime.toMillis());
            long actualTimeInMillis = actualTime.toMillis();

            long deltaTimeInMillis = Math.abs(actualTimeInMillis - expectedTimeInMillis);

            log.info("Intermediate result, expectedTimeInMillis = {} ms, scaled.", expectedTimeInMillis);
            log.info("Intermediate result, actualTimeInMillis = {} ms, scaled.", actualTimeInMillis);
            log.info("Intermediate result, deltaTimeInMillis = {} ms, scaled.", deltaTimeInMillis);

            long x_deltaTimeInMillis = Math.abs(actualTimeInMillis - (expectedTimeInMillis+durationSpeed.applyAsLong(offset)));


            log.info("Intermediate result, deltaTimeInMillis = {} ms must be less than offset+2*scaledDrift = {} ms", deltaTimeInMillis, durationSpeed.applyAsLong(offset)+2*scaledDrift);
//            log.info("Intermediate result, x_deltaTimeInMillis = {} ms must be less than durationSpeed.applyAsLong(2*drift) = {} ms", x_deltaTimeInMillis, durationSpeed.applyAsLong(2 * drift));
            Assertions.assertTrue(deltaTimeInMillis < durationSpeed.applyAsLong(offset)+2*scaledDrift);

//            log.info("Intermediate result, deltaTimeInMillis = {} ms must be less than expectedTimeInMillis*durationSpeed.applyAsLong(10L)/100L = {} ms", deltaTimeInMillis, expectedTimeInMillis * scaledDrift / 100L);
//            Assertions.assertTrue(deltaTimeInMillis < expectedTimeInMillis * scaledDrift / 100L);
        }
    }
}
