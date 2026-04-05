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

import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.route.LoggerRouting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.CallerBoundaryAware;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventAware;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Supplier;

/**
 * Proxy for instances of {@link Logger}.
 * <p>
 *     Individual methods can be overridden.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-26
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProxyLogger implements Logger, LoggingEventAware {
    /**
     * Supplier of name.
     */
    private final Supplier<String> nameSupplier;

    /**
     * Router-chosen logger.
     * <p>
     *     This may be a fixed logger.
     * </p>
     */
    @NonNull
    private final LoggerRouting loggerRouting;

    /**
     * Event consumer.
     * <p>
     *     This dictates the implementation of {@link #log(LoggingEvent)},
     *     that is, {@code LoggingEventAware}.
     * </p>
     */
    @NonNull
    private final EventConsumer eventConsumer;

    /**
     * Central routing helper for classic logging methods (no marker).
     */
    private Logger getTarget(Level level) {
        return loggerRouting.target(level);
    }

    /**
     * Central routing helper for classic logging methods (with marker).
     */
    private Logger getTarget(Level level,
                             Marker marker) {
        return loggerRouting.target(level,marker);
    }

    @Override
    public String getName() {
        return nameSupplier.get();
    }

    @Override
    public boolean isEnabledForLevel(Level level) {
        return getTarget(level).isEnabledForLevel(level);
    }

    //TODO: Implement MDC-to-marker conversion!

    private static final String PROXY_BOUNDARY = ProxyLogger.class.getName();

    private LoggingEventBuilder withCallerBoundary(LoggingEventBuilder builder) {
        if (builder instanceof CallerBoundaryAware cba) {
            cba.setCallerBoundary(PROXY_BOUNDARY);
        }
        return builder;
    }

    @Override
    public LoggingEventBuilder atLevel(Level level) {  //TODO:
        LoggingEventBuilder builder = getTarget(level).atLevel(level);
        if (builder instanceof CallerBoundaryAware cba) {
            cba.setCallerBoundary(PROXY_BOUNDARY);
        }
        return builder;
    }

    @Override
    public LoggingEventBuilder makeLoggingEventBuilder(Level level) {
        LoggingEventBuilder builder = new DefaultLoggingEventBuilder(this,level);
//        LoggingEventBuilder builder = getTarget(level).makeLoggingEventBuilder(level);
        if (builder instanceof CallerBoundaryAware cba) {
            cba.setCallerBoundary(PROXY_BOUNDARY);
        }
        return builder;
    }

    @Override
    public void log(LoggingEvent event) {
        eventConsumer.log(event);
    }

    /*
     * ****************************************
     *     Level TRACE
     * ****************************************
     */

    @Override
    public LoggingEventBuilder atTrace() {
        return getTarget(Level.TRACE).atTrace();
    }

    @Override
    public boolean isTraceEnabled() {
        return getTarget(Level.TRACE).isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return getTarget(Level.TRACE,marker).isTraceEnabled(marker);
    }

    @Override
    public void trace(String msg) {
        getTarget(Level.TRACE).trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        getTarget(Level.TRACE).trace(format,arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        getTarget(Level.TRACE).trace(format,arg1,arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        getTarget(Level.TRACE).trace(format,arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        getTarget(Level.TRACE).trace(msg,t);
    }

    @Override
    public void trace(Marker marker, String msg) {
        getTarget(Level.TRACE,marker).trace(marker,msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        getTarget(Level.TRACE,marker).trace(marker,format,arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        getTarget(Level.TRACE,marker).trace(marker,format,arg1,arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        getTarget(Level.TRACE,marker).trace(marker,format,argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        getTarget(Level.TRACE,marker).trace(marker,msg,t);
    }

    /*
     * ****************************************
     *     Level DEBUG
     * ****************************************
     */

    @Override
    public LoggingEventBuilder atDebug() {
        return getTarget(Level.DEBUG).atDebug();
    }

    @Override
    public boolean isDebugEnabled() {
        return getTarget(Level.DEBUG).isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return getTarget(Level.DEBUG,marker).isDebugEnabled(marker);
    }

    @Override
    public void debug(String msg) {
        getTarget(Level.DEBUG).debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        getTarget(Level.DEBUG).debug(format,arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        getTarget(Level.DEBUG).debug(format,arg1,arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        getTarget(Level.DEBUG).debug(format,arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        getTarget(Level.DEBUG).debug(msg,t);
    }

    @Override
    public void debug(Marker marker, String msg) {
        getTarget(Level.DEBUG,marker).debug(marker,msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        getTarget(Level.DEBUG,marker).debug(marker,format,arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        getTarget(Level.DEBUG,marker).debug(marker,format,arg1,arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... argArray) {
        getTarget(Level.DEBUG,marker).debug(marker,format,argArray);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        getTarget(Level.DEBUG,marker).debug(marker,msg,t);
    }

    /*
     * ****************************************
     *     Level INFO
     * ****************************************
     */

    @Override
    public LoggingEventBuilder atInfo() {
        return getTarget(Level.INFO).atInfo();
    }

    @Override
    public boolean isInfoEnabled() {
        return getTarget(Level.INFO).isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return getTarget(Level.INFO,marker).isInfoEnabled(marker);
    }

    @Override
    public void info(String msg) {
        getTarget(Level.INFO).info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        getTarget(Level.INFO).info(format,arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        getTarget(Level.INFO).info(format,arg1,arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        getTarget(Level.INFO).info(format,arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        getTarget(Level.INFO).info(msg,t);
    }

    @Override
    public void info(Marker marker, String msg) {
        getTarget(Level.INFO,marker).info(marker,msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        getTarget(Level.INFO,marker).info(marker,format,arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        getTarget(Level.INFO,marker).info(marker,format,arg1,arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... argArray) {
        getTarget(Level.INFO,marker).info(marker,format,argArray);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        getTarget(Level.INFO,marker).info(marker,msg,t);
    }

    /*
     * ****************************************
     *     Level WARNING
     * ****************************************
     */

    @Override
    public LoggingEventBuilder atWarn() {
        return getTarget(Level.WARN).atWarn();
    }

    @Override
    public boolean isWarnEnabled() {
        return getTarget(Level.WARN).isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return getTarget(Level.WARN,marker).isWarnEnabled(marker);
    }

    @Override
    public void warn(String msg) {
        getTarget(Level.WARN).warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        getTarget(Level.WARN).warn(format,arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        getTarget(Level.WARN).warn(format,arg1,arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        getTarget(Level.WARN).warn(format,arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        getTarget(Level.WARN).warn(msg,t);
    }

    @Override
    public void warn(Marker marker, String msg) {
        getTarget(Level.WARN,marker).warn(marker,msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        getTarget(Level.WARN,marker).warn(marker,format,arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        getTarget(Level.WARN,marker).warn(marker,format,arg1,arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... argArray) {
        getTarget(Level.WARN,marker).warn(marker,format,argArray);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        getTarget(Level.WARN,marker).warn(marker,msg,t);
    }

    /*
     * ****************************************
     *     Level ERROR
     * ****************************************
     */

    @Override
    public LoggingEventBuilder atError() {
        return getTarget(Level.ERROR).atError();
    }

    @Override
    public boolean isErrorEnabled() {
        return getTarget(Level.ERROR).isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return getTarget(Level.ERROR,marker).isErrorEnabled(marker);
    }

    @Override
    public void error(String msg) {
        getTarget(Level.ERROR).error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        getTarget(Level.ERROR).error(format,arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        getTarget(Level.ERROR).error(format,arg1,arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        getTarget(Level.ERROR).error(format,arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        getTarget(Level.ERROR).error(msg,t);
    }

    @Override
    public void error(Marker marker, String msg) {
        getTarget(Level.ERROR,marker).error(marker,msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        getTarget(Level.ERROR,marker).error(marker,format,arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        getTarget(Level.ERROR,marker).error(marker,format,arg1,arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... argArray) {
        getTarget(Level.ERROR,marker).error(marker,format,argArray);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        getTarget(Level.ERROR,marker).error(marker,msg,t);
    }
}
