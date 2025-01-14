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

package com.yelstream.topp.standard.log.assist.slf4j.spi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Marker;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.LoggingEventAware;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Supplier;

/**
 * Static proxy for instances of {@link LoggingEventBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 *
 * @param <B> Native SLF4J logging-event builder type.
 */
@SuppressWarnings({"LombokGetterMayBeUsed","ClassCanBeRecord"})
@AllArgsConstructor
public class ProxyLoggingEventBuilder<B extends LoggingEventBuilder> implements LoggingEventBuilder {
    /**
     * Delegate.
     */
    @Getter
    private final B delegate;

    /**
     * Logs a logging-event if this implements {@link LoggingEventAware}.
     * @param loggingEvent Logging-event.
     */
    protected void log(LoggingEvent loggingEvent) {
        if (delegate instanceof LoggingEventAware loggingEventAware) {
            loggingEventAware.log(loggingEvent);
        }
    }

    @Override
    public LoggingEventBuilder setCause(Throwable cause) {
        return delegate.setCause(cause);
    }

    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        return delegate.addMarker(marker);
    }

    @Override
    public LoggingEventBuilder addArgument(Object argument) {
        return delegate.addArgument(argument);
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> argumentSupplier) {
        return delegate.addArgument(argumentSupplier);
    }

    @Override
    public LoggingEventBuilder addKeyValue(String key, Object value) {
        return delegate.addKeyValue(key,value);
    }

    @Override
    public LoggingEventBuilder addKeyValue(String key, Supplier<Object> valueSupplier) {
        return delegate.addKeyValue(key,valueSupplier);
    }

    @Override
    public LoggingEventBuilder setMessage(String message) {
        return delegate.setMessage(message);
    }

    @Override
    public LoggingEventBuilder setMessage(Supplier<String> messageSupplier) {
        return delegate.setMessage(messageSupplier);
    }

    @Override
    public void log() {
        delegate.log();
    }

    @Override
    public void log(String message) {
        delegate.log(message);
    }

    @Override
    public void log(String message, Object argument) {
        delegate.log(message,argument);
    }

    @Override
    public void log(String message, Object argument1, Object argument2) {
        delegate.log(message,argument1,argument2);
    }

    @Override
    public void log(String message, Object... arguments) {
        delegate.log(message,arguments);
    }

    @Override
    public void log(Supplier<String> messageSupplier) {
        delegate.log(messageSupplier);
    }
}
