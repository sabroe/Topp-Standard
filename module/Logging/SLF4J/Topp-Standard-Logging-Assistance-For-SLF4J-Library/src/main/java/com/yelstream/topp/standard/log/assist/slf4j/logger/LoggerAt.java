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

package com.yelstream.topp.standard.log.assist.slf4j.logger;

import com.yelstream.topp.standard.log.assist.slf4j.event.ActionableAt;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Function;

/**
 * Creates actionable objects from an existing logger and the log level chosen.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-13
 *
 * @param <R> Type of actionable object.
 */
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class LoggerAt<R> implements ActionableAt<R> {
    /**
     * SLF4J logger.
     */
    private final Logger logger;

    /**
     * Resolves SLF4J logging-event builders to actionable objects.
     */
    private final Function<LoggingEventBuilder,R> resolver;

    /**
     * Resolves a SLF4J logging-event builder.
     * @param loggingEventBuilder Logging-event builder.
     * @return Resolved, actionable object.
     */
    protected R resolve(LoggingEventBuilder loggingEventBuilder) {
        return resolver.apply(loggingEventBuilder);
    }

    @Override
    public R error() {
        return resolve(logger.atError());
    }

    @Override
    public R warn() {
        return resolve(logger.atWarn());
    }

    @Override
    public R info() {
        return resolve(logger.atInfo());
    }

    @Override
    public R debug() {
        return resolve(logger.atDebug());
    }

    @Override
    public R trace() {
        return resolve(logger.atTrace());
    }

    @Override
    public R level(Level level) {
        return resolve(logger.atLevel(level));
    }

    /**
     * Create a new instance.
     * @param logger Logger.
     * @return Created instane.
     */
    public static LoggerAt<LoggingEventBuilder> of(Logger logger) {
        return new LoggerAt<>(logger,Function.identity());
    }

    /**
     * Create a new instance.
     * @param logger Logger.
     * @param resolver Resolver of logging-event builders to actionable objects.
     * @return Created instane.
     */
    public static <R> LoggerAt<R> of(Logger logger,
                                     Function<LoggingEventBuilder,R> resolver) {
        return new LoggerAt<>(logger,resolver);
    }
}
