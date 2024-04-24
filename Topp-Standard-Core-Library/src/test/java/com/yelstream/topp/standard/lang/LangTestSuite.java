package com.yelstream.topp.standard.lang;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for {@code com.yelstream.topp.standard.lang}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@Suite
@SelectClasses({ComparablesTest.class,RunnablesTest.class,ServiceLoadersTest.class,StringsTest.class})
class LangTestSuite {
}
