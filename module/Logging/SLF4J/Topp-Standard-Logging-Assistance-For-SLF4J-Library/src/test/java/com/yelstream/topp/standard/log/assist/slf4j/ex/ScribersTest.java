package com.yelstream.topp.standard.log.assist.slf4j.ex;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Test suite for {@code com.yelstream.topp.standard.log.assist.slf4j.ex.Scribers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-10
 */
@Slf4j
class ScribersTest {
    @Test
    void basicExtension() {
        log.atDebug().setMessage("Hello, {}!").addArgument("World").log();

        Scribers.of(log.atDebug()).message("Hello, {}!").arg("World").log();
        Scribers.at(log).debug().message("Hello, {}!").arg("World").log();

        Scribers.of(log.atDebug()).m("Hello, {}!").a("World").l();
        Scribers.at(log).debug().m("Hello, {}!").a("World").l();
    }

    @Test
    void basicExtensionAlias() {
        Scribers.of(log.atDebug()).message(()->"Hello, {}!").arg(()->"World").log();
    }
}
