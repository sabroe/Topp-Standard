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

package com.yelstream.topp.standard.logging.slf4j.spi.logger;

import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CompositeLogger implements Logger {

    private final List<Logger> delegates;
    private final String name;

    public CompositeLogger(List<Logger> delegates) {
        this.delegates = List.copyOf(Objects.requireNonNull(delegates));
        this.name = delegates.isEmpty() ? "CompositeLogger" : delegates.get(0).getName();
    }

    private void log(Consumer<Logger> action) {
        for (Logger logger : delegates) {
            action.accept(logger);
        }
    }

    private void logSafe(Consumer<Logger> action) {
        for (Logger l : delegates) {
            try {
                action.accept(l);
            } catch (Exception e) {
                // swallow or handle
            }
        }
    }

    private boolean anyEnabled(java.util.function.Predicate<Logger> predicate) {
        for (Logger logger : delegates) {
            if (predicate.test(logger)) {
                return true;
            }
        }
        return false;
    }

    // --------------------------------------------------
    // Basic info
    // --------------------------------------------------

    @Override
    public String getName() {
        return name;
    }

    // --------------------------------------------------
    // TRACE
    // --------------------------------------------------

    @Override
    public boolean isTraceEnabled() {
        return anyEnabled(Logger::isTraceEnabled);
    }

    @Override
    public void trace(String msg) {
        log(l -> l.trace(msg));
    }

    @Override
    public void trace(String format, Object arg) {
        log(l -> l.trace(format, arg));
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        log(l -> l.trace(format, arg1, arg2));
    }

    @Override
    public void trace(String format, Object... arguments) {
        log(l -> l.trace(format, arguments));
    }

    @Override
    public void trace(String msg, Throwable t) {
        log(l -> l.trace(msg, t));
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return anyEnabled(l -> l.isTraceEnabled(marker));
    }

    @Override
    public void trace(Marker marker, String msg) {
        log(l -> l.trace(marker, msg));
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        log(l -> l.trace(marker, format, arg));
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        log(l -> l.trace(marker, format, arg1, arg2));
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        log(l -> l.trace(marker, format, argArray));
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        log(l -> l.trace(marker, msg, t));
    }

    // --------------------------------------------------
    // DEBUG
    // --------------------------------------------------

    @Override
    public boolean isDebugEnabled() {
        return anyEnabled(Logger::isDebugEnabled);
    }

    @Override
    public void debug(String msg) {
        log(l -> l.debug(msg));
    }

    @Override
    public void debug(String format, Object arg) {
        log(l -> l.debug(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        log(l -> l.debug(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        log(l -> l.debug(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        log(l -> l.debug(msg, t));
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return anyEnabled(l -> l.isDebugEnabled(marker));
    }

    @Override
    public void debug(Marker marker, String msg) {
        log(l -> l.debug(marker, msg));
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        log(l -> l.debug(marker, format, arg));
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        log(l -> l.debug(marker, format, arg1, arg2));
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        log(l -> l.debug(marker, format, arguments));
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        log(l -> l.debug(marker, msg, t));
    }

    // --------------------------------------------------
    // INFO
    // --------------------------------------------------

    @Override
    public boolean isInfoEnabled() {
        return anyEnabled(Logger::isInfoEnabled);
    }

    @Override
    public void info(String msg) {
        log(l -> l.info(msg));
    }

    @Override
    public void info(String format, Object arg) {
        log(l -> l.info(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        log(l -> l.info(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        log(l -> l.info(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        log(l -> l.info(msg, t));
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return anyEnabled(l -> l.isInfoEnabled(marker));
    }

    @Override
    public void info(Marker marker, String msg) {
        log(l -> l.info(marker, msg));
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        log(l -> l.info(marker, format, arg));
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        log(l -> l.info(marker, format, arg1, arg2));
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        log(l -> l.info(marker, format, arguments));
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        log(l -> l.info(marker, msg, t));
    }

    // --------------------------------------------------
    // WARN
    // --------------------------------------------------

    @Override
    public boolean isWarnEnabled() {
        return anyEnabled(Logger::isWarnEnabled);
    }

    @Override
    public void warn(String msg) {
        log(l -> l.warn(msg));
    }

    @Override
    public void warn(String format, Object arg) {
        log(l -> l.warn(format, arg));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        log(l -> l.warn(format, arg1, arg2));
    }

    @Override
    public void warn(String format, Object... arguments) {
        log(l -> l.warn(format, arguments));
    }

    @Override
    public void warn(String msg, Throwable t) {
        log(l -> l.warn(msg, t));
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return anyEnabled(l -> l.isWarnEnabled(marker));
    }

    @Override
    public void warn(Marker marker, String msg) {
        log(l -> l.warn(marker, msg));
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        log(l -> l.warn(marker, format, arg));
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        log(l -> l.warn(marker, format, arg1, arg2));
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        log(l -> l.warn(marker, format, arguments));
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        log(l -> l.warn(marker, msg, t));
    }

    // --------------------------------------------------
    // ERROR
    // --------------------------------------------------

    @Override
    public boolean isErrorEnabled() {
        return anyEnabled(Logger::isErrorEnabled);
    }

    @Override
    public void error(String msg) {
        log(l -> l.error(msg));
    }

    @Override
    public void error(String format, Object arg) {
        log(l -> l.error(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        log(l -> l.error(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        log(l -> l.error(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        log(l -> l.error(msg, t));
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return anyEnabled(l -> l.isErrorEnabled(marker));
    }

    @Override
    public void error(Marker marker, String msg) {
        log(l -> l.error(marker, msg));
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        log(l -> l.error(marker, format, arg));
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        log(l -> l.error(marker, format, arg1, arg2));
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        log(l -> l.error(marker, format, arguments));
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        log(l -> l.error(marker, msg, t));
    }
}
