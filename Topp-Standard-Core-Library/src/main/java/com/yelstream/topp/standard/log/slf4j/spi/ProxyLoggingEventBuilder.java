package com.yelstream.topp.standard.log.slf4j.spi;

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
