package com.yelstream.topp.standard.time.watch;

import com.yelstream.topp.standard.time.DurationSummaryStatistics;
import com.yelstream.topp.standard.time.NanoTimeSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
    void xxx() {
         NanoTimeSource source=NanoTimeSource.system();
         DurationWatch watch=DurationWatches.of(source,LongUnaryOperator.identity());

         DurationSummaryStatistics statistics=
             DurationWatches.stat(watch,
                         10L,
                         100);
         log.info("Stat: {}",statistics);
System.out.println(statistics);


     }



}
