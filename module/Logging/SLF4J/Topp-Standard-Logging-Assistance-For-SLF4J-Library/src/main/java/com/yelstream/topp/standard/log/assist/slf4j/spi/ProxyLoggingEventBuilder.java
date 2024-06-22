/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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
    public LoggingEventBuilder setCause(Throwable throwable) {
        return delegate.setCause(throwable);
    }

    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        return delegate.addMarker(marker);
    }

    @Override
    public LoggingEventBuilder addArgument(Object o) {
        return delegate.addArgument(o);
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> supplier) {
        return delegate.addArgument(supplier);
    }

    @Override
    public LoggingEventBuilder addKeyValue(String s, Object o) {
        return delegate.addKeyValue(s,o);
    }

    @Override
    public LoggingEventBuilder addKeyValue(String s, Supplier<Object> supplier) {
        return delegate.addKeyValue(s,supplier);
    }

    @Override
    public LoggingEventBuilder setMessage(String s) {
        return delegate.setMessage(s);
    }

    @Override
    public LoggingEventBuilder setMessage(Supplier<String> supplier) {
        return delegate.setMessage(supplier);
    }

    @Override
    public void log() {
        delegate.log();
    }

    @Override
    public void log(String s) {
        delegate.log(s);
    }

    @Override
    public void log(String s, Object o) {
        delegate.log(s,o);
    }

    @Override
    public void log(String s, Object o, Object o1) {
        delegate.log(s,o,o1);
    }

    @Override
    public void log(String s, Object... objects) {
        delegate.log(s,objects);
    }

    @Override
    public void log(Supplier<String> supplier) {
        delegate.log(supplier);
    }
}
