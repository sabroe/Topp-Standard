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

package com.yelstream.topp.standard.log.assist.slf4j.spi;

import com.yelstream.topp.standard.log.assist.slf4j.Loggers;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

/**
 * Utility addressing instances of {@link LoggingEventBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 */
@UtilityClass
public class LoggingEventBuilders {
    /**
     * Indicates, if logging-event builder enables logging.
     * <p>
     *     Testing is done by a simple object reference comparison against {@link NOPLoggingEventBuilder#singleton()}.
     * </p>
     * @param builder Logging-event builder.
     * @return Indicates, if builder enables logging.
     */
    public static boolean isLoggingEnabledBySimpleCheck(LoggingEventBuilder builder) {
        return builder!=NOPLoggingEventBuilder.singleton();
    }

    /**
     * Indicates, if logging-event builder enables logging.
     * <p>
     *     Testing is done by a non-trivial check against structure - however,
     *     starting with a simple object reference comparison against {@link NOPLoggingEventBuilder#singleton()}.
     * </p>
     * @param builder Logging-event builder.
     * @return Indicates, if builder enables logging.
     */
    @SuppressWarnings({"DuplicateBranchesInSwitch"})
    public static boolean isLoggingEnabled(LoggingEventBuilder builder) {
        boolean enabled;
        if (builder==NOPLoggingEventBuilder.singleton()) {
            enabled=true;
        } else {
            enabled=
                switch (builder) {
                    case DefaultLoggingEventBuilder ignored -> true;
                    case NOPLoggingEventBuilder ignored -> false;
                    case ProxyLoggingEventBuilder<?> lev -> isLoggingEnabled(lev.getDelegate());
                    default -> false;
                };
        }
        return enabled;
    }

    /**
     * Gets the name of the logger addressed by a logging-event builder.
     * @param builder Logging-event builder.
     * @return Name of the logger addressed by builder.
     */
    public static String getLoggerName(LoggingEventBuilder builder) {
        Logger logger=getLogger(builder);
        return logger==null?null:logger.getName();
    }

    /**
     * Gets the logger addressed by a logging-event builder.
     * Or, if the builder is a NOP-builder then a substitute logger.
     * @param builder Logging-event builder.
     * @return Logger addressed by logging-event builder.
     */
    public static Logger getLogger(LoggingEventBuilder builder) {
        return
            switch (builder) {
                case DefaultLoggingEventBuilder lev -> DefaultLoggingEventBuilders.getLogger(lev);
                case NOPLoggingEventBuilder ignored -> NOPLogger.NOP_LOGGER;
                case ProxyLoggingEventBuilder<?> lev -> getLogger(lev.getDelegate());
                default -> null;
            };
    }

    /**
     * Gets the level of the logger addressed by a logging-event builder.
     * <p>
     *     This is the level or the logger itself.
     *     It is not the level addressed by e.g. {@link Logger#atDebug()} or {@link Logger#atLevel(Level)}.
     * </p>
     * @param builder Logging-event builder.
     * @return Level of the logger addressed by logging-event builder.
     */
    public static Level getLevelForLogger(LoggingEventBuilder builder) {
        Logger logger=getLogger(builder);
        return logger==null?null:Loggers.getLevel(logger);
    }

    /**
     * Gets the level of the log-statement used to create a logging-event builder in a normal, fluent usage.
     * <p>
     *     This is the level addressed by e.g. {@link Logger#atDebug()} or {@link Logger#atLevel(Level)}.
     *     It is not the level or the logger itself.
     * </p>
     * @param builder Logging-event builder.
     * @return Level of the log-statement used to create a logging-event builder.
     */
    @SuppressWarnings({"DuplicateBranchesInSwitch"})
    public static Level getLevelForStatement(LoggingEventBuilder builder) {
        return
            switch (builder) {
                case DefaultLoggingEventBuilder lev -> DefaultLoggingEventBuilders.getLevelForStatement(lev);
                case NOPLoggingEventBuilder ignored -> null;
                case ProxyLoggingEventBuilder<?> lev -> getLevelForStatement(lev.getDelegate());
                default -> null;
            };
    }
}
