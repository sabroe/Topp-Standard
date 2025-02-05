package com.yelstream.topp.standard.log.assist.slf4j.scribe.factory;

import com.yelstream.topp.standard.log.assist.slf4j.logger.LoggerAt;
import com.yelstream.topp.standard.log.assist.slf4j.logger.capture.CaptureLogger;
import com.yelstream.topp.standard.log.assist.slf4j.scribe.Scriber;
import com.yelstream.topp.standard.log.assist.slf4j.scribe.Scribers;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.List;

/**
 * Tests {@link ScriberFactory}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-05
 */
@Slf4j
class ScriberFactoryTest {
    /**
     * Tests the baseline effect of using the native {@link org.slf4j.spi.LoggingEventBuilder}.
     */
    @Test
    void baseline() {
        CaptureLogger log=CaptureLogger.of();

        LoggerAt<Scriber<LoggingEventBuilder>> at=ScriberFactory.of().from(log);

        at.info().setMessage("Hello, {}!").addArgument("World").log();

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World!"));
    }

    /**
     * Tests the creation of {@link Scriber} instances using {@link ScriberFactory#from(Logger)}.
     */
    @Test
    void creationFromLoggerAndLevel() {
        CaptureLogger log=CaptureLogger.of();

        LoggerAt<Scriber<LoggingEventBuilder>> at=ScriberFactory.of().from(log);

        at.info().setMessage("Hello, {}!").addArgument("World #1").log();
        at.info().message("Hello, {}!").arg("World #2").log();
        at.info().m("Hello, {}!").a("World #3").l();

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World #1!","Hello, World #2!","Hello, World #3!"));
    }
}
