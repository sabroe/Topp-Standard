package com.yelstream.topp.standard.log.slf4j;

import lombok.AllArgsConstructor;
import org.slf4j.Marker;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.LoggingEventAware;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Supplier;

@AllArgsConstructor
public class ProxyLoggingEventBuilder<B extends LoggingEventBuilder> implements LoggingEventBuilder {
    /**
     *
     */
    private final B loggingEventBuilder;

    protected void log(LoggingEvent loggingEvent) {
        if (loggingEventBuilder instanceof LoggingEventAware loggingEventAware) {
            loggingEventAware.log(loggingEvent);
        }
    }

    @Override
    public LoggingEventBuilder setCause(Throwable throwable) {
        return loggingEventBuilder.setCause(throwable);
    }

    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        return loggingEventBuilder.addMarker(marker);
    }

    @Override
    public LoggingEventBuilder addArgument(Object o) {
        return loggingEventBuilder.addArgument(o);
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> supplier) {
        return loggingEventBuilder.addArgument(supplier);
    }

    @Override
    public LoggingEventBuilder addKeyValue(String s, Object o) {
        return loggingEventBuilder.addKeyValue(s,o);
    }

    @Override
    public LoggingEventBuilder addKeyValue(String s, Supplier<Object> supplier) {
        return loggingEventBuilder.addKeyValue(s,supplier);
    }

    @Override
    public LoggingEventBuilder setMessage(String s) {
        return loggingEventBuilder.setMessage(s);
    }

    @Override
    public LoggingEventBuilder setMessage(Supplier<String> supplier) {
        return loggingEventBuilder.setMessage(supplier);
    }

    @Override
    public void log() {
        loggingEventBuilder.log();
    }

    @Override
    public void log(String s) {
        loggingEventBuilder.log(s);
    }

    @Override
    public void log(String s, Object o) {
        loggingEventBuilder.log(s,o);
    }

    @Override
    public void log(String s, Object o, Object o1) {
        loggingEventBuilder.log(s,o,o1);
    }

    @Override
    public void log(String s, Object... objects) {
        loggingEventBuilder.log(s,objects);
    }

    @Override
    public void log(Supplier<String> supplier) {
        loggingEventBuilder.log(supplier);
    }
}
