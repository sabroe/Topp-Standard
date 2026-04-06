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

import com.yelstream.topp.standard.logging.slf4j.spi.event.FixedLoggingEvent;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.enable.LoggerEnablement;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumers;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.helpers.NormalizedLoggingCall;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.route.LoggerRouting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.LoggingEvent;

import java.util.function.Supplier;

/**
 * Proxy for instances of {@link Logger}.
 * <p>
 *     Individual methods can be overridden.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-03
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@lombok.Builder(builderClassName = "Builder")
public class EventLogger extends AbstractEventAwareLogger {
    /**
     *
     */
    private final Supplier<String> callerBoundarySupplier;

    /**
     *
     */
    @Getter(AccessLevel.PROTECTED)
    private final NormalizedLoggingCall normalizedLoggingCall;

    /**
     * Supplier of name.
     */
    private final Supplier<String> nameSupplier;

    /**
     * Logger enablement.
     */
    @NonNull
    private final LoggerEnablement loggerEnablement;

    /**
     * Event consumer.
     * <p>
     *     This dictates the implementation of {@link #log(LoggingEvent)},
     *     that is, {@code LoggingEventAware}.
     * </p>
     */
    @NonNull
    private final EventConsumer eventConsumer;

    @Override
    public String getName() {
        return nameSupplier.get();
    }

    @Override
    public boolean isTraceEnabled() {
        return loggerEnablement.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return loggerEnablement.isTraceEnabled(marker);
    }

    @Override
    public boolean isDebugEnabled() {
        return loggerEnablement.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return loggerEnablement.isDebugEnabled(marker);
    }

    @Override
    public boolean isInfoEnabled() {
        return loggerEnablement.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return loggerEnablement.isInfoEnabled(marker);
    }

    @Override
    public boolean isWarnEnabled() {
        return loggerEnablement.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return loggerEnablement.isWarnEnabled(marker);
    }

    @Override
    public boolean isErrorEnabled() {
        return loggerEnablement.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return loggerEnablement.isErrorEnabled(marker);
    }

    @Override
    protected String getFullyQualifiedCallerName() {
        return callerBoundarySupplier.get();
    }

    @Override
    public void log(LoggingEvent event) {
        System.out.println("ZZZ:> event = "+event);

        LoggingEvent event2=FixedLoggingEvent.builder().event(event).build();
        System.out.println("ZZZ2:> event2 = "+event2);

        eventConsumer.log(event);
    }

    public static class Builder {
        public Builder name(String name) {
            return nameSupplier(()->name);
        }

        public Builder loggerRouting(LoggerRouting loggerRouting) {
            return eventConsumer(EventConsumers.create(loggerRouting));
        }
    }
}
