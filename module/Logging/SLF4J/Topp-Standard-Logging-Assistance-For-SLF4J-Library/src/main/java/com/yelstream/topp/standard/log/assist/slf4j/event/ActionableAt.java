package com.yelstream.topp.standard.log.assist.slf4j.event;

import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * Creates actionable objects depending upon the log level chosen.
 * <p>
 *     Inspired by the SLF4J logger method signatures {@link Logger#atInfo()} and {@code Logger#atXXX()}
 *     used to create newer fluent logging-event builders.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-13
 *
 * @param <R> Type of actionable objects.
 */
@FunctionalInterface
public interface ActionableAt<R> {
    default R atError() {
        return atLevel(Level.ERROR);
    }

    default R atWarn() {
        return atLevel(Level.WARN);
    }

    default R atInfo() {
        return atLevel(Level.INFO);
    }

    default R atDebug() {
        return atLevel(Level.DEBUG);
    }

    default R atTrace() {
        return atLevel(Level.TRACE);
    }

    R atLevel(Level level);
}
