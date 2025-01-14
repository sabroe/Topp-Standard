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
 * @param <R> Type of actionable object.
 */
@FunctionalInterface
public interface ActionableAt<R> {
    /**
     * Gets the actionable associated with the log level {@link Level#ERROR}.
     * @return Actionable.
     *         <p>
     *             Guaranteed to be non-{@code null}.
     *         </p>
     */
    default R atError() {
        return atLevel(Level.ERROR);
    }

    /**
     * Gets the actionable associated with the log level {@link Level#WARN}.
     * @return Actionable.
     *         <p>
     *             Guaranteed to be non-{@code null}.
     *         </p>
     */
    default R atWarn() {
        return atLevel(Level.WARN);
    }

    /**
     * Gets the actionable associated with the log level {@link Level#INFO}.
     * @return Actionable.
     *         <p>
     *             Guaranteed to be non-{@code null}.
     *         </p>
     */
    default R atInfo() {
        return atLevel(Level.INFO);
    }

    /**
     * Gets the actionable associated with the log level {@link Level#DEBUG}.
     * @return Actionable.
     *         <p>
     *             Guaranteed to be non-{@code null}.
     *         </p>
     */
    default R atDebug() {
        return atLevel(Level.DEBUG);
    }

    /**
     * Gets the actionable associated with the log level {@link Level#TRACE}.
     * @return Actionable.
     *         <p>
     *             Guaranteed to be non-{@code null}.
     *         </p>
     */
    default R atTrace() {
        return atLevel(Level.TRACE);
    }
    /**
     * Gets the actionable associated with the log level specified.
     * @param level Log level.
     * @return Actionable.
     *         <p>
     *             Guaranteed to be non-{@code null}.
     *         </p>
     */
    R atLevel(Level level);
}
