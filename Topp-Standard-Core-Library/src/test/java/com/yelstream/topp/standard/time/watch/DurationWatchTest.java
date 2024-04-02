package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.lang.thread.Threads;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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

    @Test
    void watchSpeed() {
        LongUnaryOperator durationSpeed=t->t*10;  //t->(t+48L/2L)/48L
        Duration unscaledExpectedTime=Duration.ofSeconds(2);
        //Duration unscaledExpectedTime=Duration.ofMillis(100);

        //long drift=10L;
        //long scaledDrift=durationSpeed.applyAsLong(drift);

        NanoTimeSource source=NanoTimeSource.system();
        DurationWatch watch=DurationWatches.createDurationWatch(source,durationSpeed);


        DurationWatch.Timer durationTimer=watch.start();
        Threads.sleep(unscaledExpectedTime);
        DurationWatch.Time durationTime=durationTimer.stop();

        Duration actualTime=durationTime.toDuration();


        long expectedTimeInMillis=durationSpeed.applyAsLong(unscaledExpectedTime.toMillis());
        long actualTimeInMillis=actualTime.toMillis();

        long deltaTimeInMillis=actualTimeInMillis-expectedTimeInMillis;

        log.info("Intermediate result, expectedTimeInMillis = {} ms, scaled.",expectedTimeInMillis);
        log.info("Intermediate result, actualTimeInMillis = {} ms, scaled.",actualTimeInMillis);
        log.info("Intermediate result, deltaTimeInMillis = {} ms, scaled.",deltaTimeInMillis);

        Assertions.assertTrue(deltaTimeInMillis<durationSpeed.applyAsLong(2*10L));
        Assertions.assertTrue(deltaTimeInMillis*100<expectedTimeInMillis);
    }
}
