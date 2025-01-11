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

package com.yelstream.topp.standard.log.assist.slf4j.capture;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests {@link CaptureLogger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-11
 */
class CaptureLoggerTest {
    /**
     * Tests capture of plain logging.
     */
    @Test
    void logAtLevelInfo() {
        CaptureLogger log=CaptureLogger.of();

        log.info("Hello, World!");

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World!"));
    }

    /**
     * Tests capture of fluent logging.
     */
    @Test
    void fluentLoggingAtLevelDebug() {
        CaptureLogger log=CaptureLogger.of();

        log.atDebug().setMessage("Hello, {}!").addArgument("World").log();

        List<String> messages=log.getFormattedMessages();
        Assertions.assertEquals(messages,List.of("Hello, World!"));
    }
}
