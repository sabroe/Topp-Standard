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

package com.yelstream.topp.standard.log.assist.slf4j.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Marker;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Supplier;

@SuppressWarnings("all")
@AllArgsConstructor
public abstract class AbstractLoggingEventBuilderEx<S extends AbstractLoggingEventBuilderEx<S,B>,B extends LoggingEventBuilder> implements AliasLoggingEventBuilderEx<S> {
    /**
     * Delegate.
     */
    @Getter
    protected final B delegate;

    protected abstract S self();

    @Override
    public S setCause(Throwable throwable) {
        delegate.setCause(throwable);
        return self();
    }

    @Override
    public S addMarker(Marker marker) {
        delegate.addMarker(marker);
        return self();
    }

    @Override
    public S addArgument(Object o) {
        delegate.addArgument(o);
        return self();
    }

    @Override
    public S addArgument(Supplier<?> supplier) {
        delegate.addArgument(supplier);
        return self();
    }

    @Override
    public S addKeyValue(String s, Object o) {
        delegate.addKeyValue(s,o);
        return self();
    }

    @Override
    public S addKeyValue(String s, Supplier<Object> supplier) {
        delegate.addKeyValue(s,supplier);
        return self();
    }

    @Override
    public S setMessage(String s) {
        delegate.setMessage(s);
        return self();
    }

    @Override
    public S setMessage(Supplier<String> supplier) {
        delegate.setMessage(supplier);
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
