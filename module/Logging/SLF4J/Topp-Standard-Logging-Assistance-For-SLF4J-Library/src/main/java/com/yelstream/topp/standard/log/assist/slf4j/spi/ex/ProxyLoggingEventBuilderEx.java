package com.yelstream.topp.standard.log.assist.slf4j.spi.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Marker;

import java.util.function.Supplier;

/**
 * Static proxy for instances of {@link LoggingEventBuilderEx}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-14
 *
 * @param <S> Self-referential type.
 */
@SuppressWarnings({"LombokGetterMayBeUsed","ClassCanBeRecord"})
@AllArgsConstructor
public class ProxyLoggingEventBuilderEx<S extends LoggingEventBuilderEx<S>> implements LoggingEventBuilderEx<S> {
    /**
     * Delegate.
     */
    @Getter
    private final S delegate;

    @Override
    public S setCause(Throwable cause) {
        return delegate.setCause(cause);
    }

    @Override
    public S addMarker(Marker marker) {
        return delegate.addMarker(marker);
    }

    @Override
    public S addArgument(Object argument) {
        return delegate.addArgument(argument);
    }

    @Override
    public S addArgument(Supplier<?> argumentSupplier) {
        return delegate.addArgument(argumentSupplier);
    }

    @Override
    public S addKeyValue(String key,
                         Object value) {
        return delegate.addKeyValue(key,value);
    }

    @Override
    public S addKeyValue(String key,
                         Supplier<Object> valueSupplier) {
        return delegate.addKeyValue(key,valueSupplier);
    }

    @Override
    public S setMessage(String message) {
        return delegate.setMessage(message);
    }

    @Override
    public S setMessage(Supplier<String> messageSupplier) {
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
    public void log(String message,
                    Object argument) {
        delegate.log(message,argument);
    }

    @Override
    public void log(String message,
                    Object argument1,
                    Object argument2) {
        delegate.log(message,argument1,argument2);
    }

    @Override
    public void log(String message,
                    Object... arguments) {
        delegate.log(message,arguments);
    }

    @Override
    public void log(Supplier<String> messageSupplier) {
        delegate.log(messageSupplier);
    }
}
