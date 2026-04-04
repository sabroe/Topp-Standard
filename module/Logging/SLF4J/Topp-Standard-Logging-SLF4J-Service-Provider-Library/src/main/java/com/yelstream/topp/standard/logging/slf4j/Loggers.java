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

package com.yelstream.topp.standard.logging.slf4j;

import com.yelstream.topp.standard.logging.slf4j.spi.event.builder.LoggingEventBuilders;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.LoggingEventAware;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * Utility for instances of {@link Logger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-04
 */
@UtilityClass
public class Loggers {
    /**
     * Logs an event to a logger.
     * <p>
     *     This favours loggers implementing {@link LoggingEventAware}.
     * </p>
     * @param logger Logger.
     * @param event Event.
     */
    public static void logEvent(Logger logger,
                                LoggingEvent event) {
        if (logger instanceof LoggingEventAware aware) {
            aware.log(event);
        } else {
            logEventUsingNonEventAwareLogger(logger,event);
        }
    }

    /**
     * Logs an event to a logger.
     * <p>
     *     This assumes that the logger does not implement {@link LoggingEventAware}.
     * </p>
     * @param logger Logger.
     * @param event Event.
     */
    public static void logEventUsingNonEventAwareLogger(Logger logger,
                                                        LoggingEvent event) {
        LoggingEventBuilder builder = logger.atLevel(event.getLevel());
        LoggingEventBuilders.logEvent(builder,event);
    }
}
