/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.log.assist.slf4j.scribe;

import com.yelstream.topp.standard.log.assist.slf4j.logger.capture.CaptureLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.List;

/**
 * Tests {@link Scribers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-10
 */
@Slf4j
class ScribersTest {
    /**
     * Tests the baseline effect of using the native {@link org.slf4j.spi.LoggingEventBuilder}.
     */
    @Test
    void baseline() {
        CaptureLogger log=CaptureLogger.of();

        log.atInfo().setMessage("Hello, {}!").addArgument("World").log();

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World!"));
    }

    /**
     * Tests the creation of {@link Scriber} instances using {@link Scribers#of(LoggingEventBuilder)}.
     */
    @Test
    void creationFromLoggingEventBuilder() {
        CaptureLogger log=CaptureLogger.of();

        Scribers.of(log.atInfo()).setMessage("Hello, {}!").addArgument("World #1").log();
        Scribers.of(log.atInfo()).message("Hello, {}!").arg("World #2").log();
        Scribers.of(log.atInfo()).m("Hello, {}!").a("World #3").l();

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World #1!","Hello, World #2!","Hello, World #3!"));
    }

    /**
     * Tests the creation of {@link Scriber} instances using {@link Scribers#of(Logger)}.
     */
    @Test
    void creationFromLoggerAndLevel() {
        CaptureLogger log=CaptureLogger.of();

        Scribers.of(log).info().setMessage("Hello, {}!").addArgument("World #1").log();
        Scribers.of(log).info().message("Hello, {}!").arg("World #2").log();
        Scribers.of(log).info().m("Hello, {}!").a("World #3").l();

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World #1!","Hello, World #2!","Hello, World #3!"));
    }
}
