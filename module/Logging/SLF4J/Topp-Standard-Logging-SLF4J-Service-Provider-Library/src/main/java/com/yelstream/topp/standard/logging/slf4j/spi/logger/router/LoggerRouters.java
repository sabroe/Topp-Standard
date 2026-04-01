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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.router;

import com.yelstream.topp.standard.logging.slf4j.event.Levels;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.NOPLogger;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Utilities for instances of {@link LoggerRouter}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-29
 */
@UtilityClass
public class LoggerRouters {

    public static LoggerRouter create(Logger logger) {
        return (_,_)->logger;
    }

    @SuppressWarnings("java:S1066")
    public static LoggerRouter createLimitedByLevel(Level level,
                                                    Logger logger) {
        return (l,_)->{
            if (Levels.isLevelEnabled(l,level)) {
                if (logger.isEnabledForLevel(level)) {
                    return logger;
                }
            }
            return NOPLogger.NOP_LOGGER;
        };
    }

    public static LoggerRouter from(BiFunction<Level,List<Marker>,Logger> loggerRouter) {
        return loggerRouter::apply;
    }
}
