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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.proxy;

import com.yelstream.topp.standard.logging.slf4j.Loggers;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.enable.LoggerEnablement;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.route.LoggerRouting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.AbstractLogger;
import org.slf4j.spi.LoggingEventAware;

import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-04
 */
public abstract class AbstractEventAwareLogger extends AbstractLogger implements LoggingEventAware {

    @Override
    protected void handleNormalizedLoggingCall(Level level,
                                               Marker marker,
                                               String messagePattern,
                                               Object[] arguments,
                                               Throwable throwable) {
        DefaultLoggingEvent event = new DefaultLoggingEvent(level, this);

        event.setMessage(messagePattern);
        event.addArguments(arguments);
        event.setThrowable(throwable);
        if (marker != null) {
            event.addMarker(marker);
        }

/*
        event.addKeyValue("xxx",org.slf4j.MDC.getCopyOfContextMap());
        event.setCallerBoundary("xxx");
        event.setTimeStamp(0L);
        event.setThre
*/

        log(event);
    }
}
