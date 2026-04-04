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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.route;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

import java.util.Collections;
import java.util.List;

/**
 * Router-chosen logger.
 * <p>
 *     This may be a fixed logger.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-26
 */
@FunctionalInterface
public interface LoggerRouting {
    /**
     * Routes to a target logger based on level and marker.
     * @param level Logging level.
     * @param markers Markers.
     * @return Chosen target.
     */
    Logger target(Level level,
                  List<Marker> markers);

    default Logger target(Level level) {
        return target(level,Collections.emptyList());
    }

    default Logger target(Level level,
                          Marker marker) {
        return target(level,marker==null?Collections.emptyList():List.of(marker));
    }

    default Logger target(LoggingEvent event) {
        return target(event.getLevel(),event.getMarkers());
    }
}
