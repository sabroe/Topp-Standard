package com.yelstream.topp.standard.microprofile.config.source;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.standard.microprofile.config.source}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-20
 */
@Suite
@SelectClasses({FixedMapConfigSourceTest.class,
                ConcurrentMapConfigSourceTest.class,
                DynamicMapConfigSourceTest.class,
                ChainedConfigSourceTest.class})
class ConfigSourceTestSuite {
}
