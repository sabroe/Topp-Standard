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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.enable;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

import java.util.Collections;
import java.util.List;

/**
 * Logger enablement.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-03
 */
@FunctionalInterface
public interface LoggerEnablement {
    /**
     * Indicates if logging is enabled based on level and marker.
     * @param level Logging level.
     * @param markers Markers.
     * @return Chosen target.
     */
    boolean isEnabled(Level level,
                      List<Marker> markers);

    default boolean isEnabled(Level level) {
        return isEnabled(level,Collections.emptyList());
    }

    default boolean isEnabled(Level level,
                              Marker marker) {
        return isEnabled(level,marker==null?Collections.emptyList():List.of(marker));
    }

    default boolean isEnabled(LoggingEvent event) {
        return isEnabled(event.getLevel(),event.getMarkers());
    }

    default boolean isTraceEnabled() {
        return isEnabled(Level.TRACE);
    }

    default boolean isTraceEnabled(Marker marker) {
        return isEnabled(Level.TRACE,marker);
    }

    default  boolean isDebugEnabled() {
        return isEnabled(Level.DEBUG);
    }

    default boolean isDebugEnabled(Marker marker) {
        return isEnabled(Level.DEBUG,marker);
    }

    default boolean isInfoEnabled() {
        return isEnabled(Level.INFO);
    }

    default boolean isInfoEnabled(Marker marker) {
        return isEnabled(Level.INFO,marker);
    }

    default boolean isWarnEnabled() {
        return isEnabled(Level.WARN);
    }

    default boolean isWarnEnabled(Marker marker) {
        return isEnabled(Level.WARN,marker);
    }

    default boolean isErrorEnabled() {
        return isEnabled(Level.ERROR);
    }

    default boolean isErrorEnabled(Marker marker) {
        return isEnabled(Level.ERROR,marker);
    }
}
