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

package com.yelstream.topp.standard.log.assist.slf4j;

import com.yelstream.topp.standard.log.assist.slf4j.event.Levels;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.List;

/**
 * Utility addressing instances of {@link Logger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 */
@Slf4j
@UtilityClass
public class Loggers {
    /**
     * Gets a logger instance by name.
     * @param loggerName Name of logger.
     * @return Logger.
     */
    public static Logger getLogger(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }

    /**
     * Gets the levels enabled for a logger.
     * <p>
     *     Note that, by construction of the {@link Logger} interface,
     *     the change between enabled and disabled may not necessarily change at one point in the sorted order of levels.
     * </p>
     * @param logger Logger.
     * @return Levels enabled.
     *         This is immutable.
     */
    public static List<Level> getLevels(Logger logger) {
        return Levels.getLevelsSorted().stream().filter(logger::isEnabledForLevel).toList();
    }

    /**
     * Gets the levels enabled for a logger.
     * @param logger Logger.
     * @return Levels enabled.
     */
    public static Level getLevel(Logger logger) {
        return Levels.getLevelsSorted().stream().filter(logger::isEnabledForLevel).findFirst().orElse(null);
    }
}
