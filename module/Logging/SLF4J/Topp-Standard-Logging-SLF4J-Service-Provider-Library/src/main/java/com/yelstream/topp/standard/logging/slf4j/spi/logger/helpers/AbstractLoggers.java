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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.helpers;

import com.yelstream.topp.standard.logging.slf4j.spi.event.FixedLoggingEvent;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import lombok.experimental.UtilityClass;
import org.slf4j.spi.MDCAdapter;

import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility for instances of {@link org.slf4j.helpers.AbstractLogger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-04
 */
@UtilityClass
public class AbstractLoggers {  //TODO: -> NormalizedLoggingCalls
    public static NormalizedLoggingCall createNormalizedLoggingCall(Supplier<Instant> timeSupplier,
                                                                    String callerBoundary,
                                                                    Supplier<Map<String, String>> contextMapSupplier,
                                                                    EventConsumer eventConsumer) {
        return (logger, level, marker, messagePattern, arguments, throwable) -> {
            Instant now = timeSupplier.get();
            FixedLoggingEvent.Builder builder = FixedLoggingEvent.Builder.create(logger, level);
            if (marker != null) {
                builder = builder.marker(marker);
            }
            builder = builder.message(messagePattern);
            if (arguments != null) {
                builder = builder.argumentArray(arguments);
            }
            builder = builder.throwable(throwable);
            builder = builder.time(now);

            builder = builder.threadName(Thread.currentThread().getName());
            if (callerBoundary != null) {
                builder = builder.callerBoundary(callerBoundary);
            }

            if (contextMapSupplier != null) {
                Map<String, String> contextMap = contextMapSupplier.get();
                builder = builder.contextMap(contextMap);
            }

            FixedLoggingEvent event = builder.build();

            eventConsumer.log(event);
        };
    }

    @lombok.Builder(builderClassName = "Builder")
    private static NormalizedLoggingCall createNormalizedLoggingCallByBuilder(Supplier<Instant> timeSupplier,
                                                                              String callerBoundary,
                                                                              Supplier<Map<String, String>> contextMapSupplier,
                                                                              EventConsumer eventConsumer) {
        return createNormalizedLoggingCall(timeSupplier,callerBoundary,contextMapSupplier,eventConsumer);
    }

    public static class Builder {
        private Supplier<Instant> timeSupplier=Instant::now;

        public Builder mdcAdapter(MDCAdapter mdcAdapter) {
            return contextMapSupplier(mdcAdapter::getCopyOfContextMap);
        }
    }
}
