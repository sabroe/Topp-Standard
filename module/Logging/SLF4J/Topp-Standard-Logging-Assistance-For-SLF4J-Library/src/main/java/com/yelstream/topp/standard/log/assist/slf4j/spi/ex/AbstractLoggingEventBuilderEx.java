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

package com.yelstream.topp.standard.log.assist.slf4j.spi.ex;

import com.yelstream.topp.standard.log.assist.slf4j.spi.ex.LoggingEventBuilderEx;
import lombok.Getter;
import org.slf4j.Marker;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Supplier;

/**
 * Abstract, extended logging-event builder bound to a native SLF4J logging-event builder {@link LoggingEventBuilder}.
 * <p>
 *     This is essentially an abstract implementation of {@link LoggingEventBuilderEx}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-12
 *
 * @param <B> Native SLF4J logging-event builder type.
 * @param <S> Self-referential type.
 */
@SuppressWarnings({"LombokGetterMayBeUsed"})
public abstract class AbstractLoggingEventBuilderEx<B extends LoggingEventBuilder, S extends LoggingEventBuilderEx<S>> implements LoggingEventBuilderEx<S> {
    /**
     * Original SLF4J builder.
     * <p>
     *   This is kept for possible reference.
     * </p>
     */
    @Getter
    private final B loggingEventBuilder;

    /**
     * Current SLF4J "delegate" builder.
     * <p>
     *   Used for all actual invocation, in the extreme case it could in principle be updated, but is likely not to be.
     * </p>
     */
    @Getter
    protected LoggingEventBuilder delegate;

    /**
     * Constructor.
     * @param loggingEventBuilder Native SLF4J logging-event builder.
     */
    protected AbstractLoggingEventBuilderEx(B loggingEventBuilder) {
        this.loggingEventBuilder=loggingEventBuilder;
        delegate=loggingEventBuilder;
    }

    /**
     * Provides the self reference.
     * @return Self reference.
     */
    protected abstract S self();

    @Override
    public S setCause(Throwable throwable) {
        delegate=delegate.setCause(throwable);
        return self();
    }

    @Override
    public S addMarker(Marker marker) {
        delegate=delegate.addMarker(marker);
        return self();
    }

    @Override
    public S addArgument(Object o) {
        delegate=delegate.addArgument(o);
        return self();
    }

    @Override
    public S addArgument(Supplier<?> supplier) {
        delegate=delegate.addArgument(supplier);
        return self();
    }

    @Override
    public S addKeyValue(String s, Object o) {
        delegate=delegate.addKeyValue(s,o);
        return self();
    }

    @Override
    public S addKeyValue(String s, Supplier<Object> supplier) {
        delegate=delegate.addKeyValue(s,supplier);
        return self();
    }

    @Override
    public S setMessage(String s) {
        delegate=delegate.setMessage(s);
        return self();
    }

    @Override
    public S setMessage(Supplier<String> supplier) {
        delegate=delegate.setMessage(supplier);
        return self();
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
