package com.yelstream.topp.standard.time;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.standard.time}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-26
 */
@Suite
@SelectClasses({DurationSummaryStatisticsTest.class,
                DurationSummaryStatisticsCollectorTest.class,
                RandomDurationGeneratorTest.class,
                DurationsTest.class,
                InstantsTest.class,
                InstantSourcesTest.class})
class TimeTestSuite {
}
