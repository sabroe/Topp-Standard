package com.yelstream.topp.standard.log.assist.slf4j;

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
 * @param <R> Type of actionable objects.
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
    public R atError() {
        return resolve(logger.atError());
    }

    @Override
    public R atWarn() {
        return resolve(logger.atWarn());
    }

    @Override
    public R atInfo() {
        return resolve(logger.atInfo());
    }

    @Override
    public R atDebug() {
        return resolve(logger.atDebug());
    }

    @Override
    public R atTrace() {
        return resolve(logger.atTrace());
    }

    @Override
    public R atLevel(Level level) {
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
