/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.log.assist.slf4j.logger.capture;

import com.yelstream.topp.standard.log.assist.slf4j.logger.capture.OpenEventRecordingLogger;
import org.junit.jupiter.api.Test;
import org.slf4j.event.SubstituteLoggingEvent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link OpenEventRecordingLogger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-11
 */
class OpenEventRecordingLoggerTest {
    /**
     * Tests that the initial event queue is empty.
     */
    @Test
    void initialEventQueueIsEmpty() {
        OpenEventRecordingLogger log=OpenEventRecordingLogger.of();

        List<SubstituteLoggingEvent> events=log.getEvents();

        assertEquals(0,events.size());
    }

    /**
     * Tests basic event capture.
     */
    @Test
    void basicEventCapture() {
        OpenEventRecordingLogger log=OpenEventRecordingLogger.of();

        log.info("Info log message");
        log.error("Error log message");

        List<SubstituteLoggingEvent> events=log.getEvents();

        assertEquals(2,events.size());
        assertEquals("Info log message",events.get(0).getMessage());
        assertEquals("Error log message",events.get(1).getMessage());
    }
}
