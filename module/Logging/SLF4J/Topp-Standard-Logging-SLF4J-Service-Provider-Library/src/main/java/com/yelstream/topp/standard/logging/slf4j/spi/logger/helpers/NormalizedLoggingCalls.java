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
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.spi.MDCAdapter;

import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility for instances of {@link NormalizedLoggingCall}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-04
 */
@UtilityClass
public class NormalizedLoggingCalls {
    /**
     *
     */
    private static FixedLoggingEvent.Builder toEventBuilder(Logger logger,
                                                            Level level,
                                                            Marker marker,
                                                            String messagePattern,
                                                            Object[] arguments,
                                                            Throwable throwable) {
        FixedLoggingEvent.Builder builder = FixedLoggingEvent.Builder.create(logger, level);
        if (marker != null) {
            builder = builder.marker(marker);
        }
        builder = builder.message(messagePattern);
        if (arguments != null) {
            builder = builder.argumentArray(arguments);
        }
        builder = builder.throwable(throwable);
        return builder;
    }

    /**
     * Default handing of a normalized logging call.
     * @param callerBoundary
     * @param eventConsumer
     * @return
     */
    public static NormalizedLoggingCall create(String callerBoundary,  //TO-DO: For caller-boundary, consider a UnaryOperator; it may have been set!
                                               EventConsumer eventConsumer) {
        return (logger, level, marker, messagePattern, arguments, throwable) -> {
            FixedLoggingEvent.Builder builder = toEventBuilder(logger,level,marker,messagePattern,arguments,throwable);
            if (callerBoundary != null) {
                builder = builder.callerBoundary(callerBoundary);
            }
            FixedLoggingEvent event = builder.build();
            eventConsumer.log(event);
        };
    }

    public static NormalizedLoggingCall create(Supplier<Instant> timeSupplier,
                                               String callerBoundary,  //TO-DO: For caller-boundary, consider a UnaryOperator; it may have been set!
                                               Supplier<Map<String, String>> contextMapSupplier,
                                               EventConsumer eventConsumer) {
        return (logger, level, marker, messagePattern, arguments, throwable) -> {
//            Instant now = timeSupplier.get();
            FixedLoggingEvent.Builder builder = FixedLoggingEvent.Builder.create(logger, level);
            if (marker != null) {
                builder = builder.marker(marker);
            }
            builder = builder.message(messagePattern);
            if (arguments != null) {
                builder = builder.argumentArray(arguments);
            }
            builder = builder.throwable(throwable);
//            builder = builder.time(now);  //TODO: Excess! Not set by the fluent API.

//            builder = builder.threadName(Thread.currentThread().getName());  //TODO: Excess! Not set by the fluent API.
            if (callerBoundary != null) {
                builder = builder.callerBoundary(callerBoundary);
            }

/*
            if (contextMapSupplier != null) {  //TODO: Excess! Not set by the fluent API.
                Map<String, String> contextMap = contextMapSupplier.get();
                builder = builder.contextMap(contextMap);
            }
*/

            FixedLoggingEvent event = builder.build();

            eventConsumer.log(event);
        };
    }

    @lombok.Builder(builderClassName = "Builder")
    private static NormalizedLoggingCall createByBuilder(Supplier<Instant> timeSupplier,
                                                         String callerBoundary,
                                                         Supplier<Map<String, String>> contextMapSupplier,
                                                         EventConsumer eventConsumer) {
        return create(timeSupplier,callerBoundary,contextMapSupplier,eventConsumer);
    }

    public static class Builder {
        private Supplier<Instant> timeSupplier=Instant::now;

        public Builder mdcAdapter(MDCAdapter mdcAdapter) {
            return contextMapSupplier(mdcAdapter::getCopyOfContextMap);
        }
    }
}
