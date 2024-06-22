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

import com.yelstream.topp.standard.lang.reflect.Reflection;
import com.yelstream.topp.standard.log.assist.slf4j.Loggers;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;
import org.slf4j.spi.DefaultLoggingEventBuilder;

/**
 * Utility addressing instances of {@link DefaultLoggingEventBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 */
@Slf4j
@UtilityClass
public class DefaultLoggingEventBuilders {
    /**
     * Name of protected {@link Logger}-field within {@link DefaultLoggingEventBuilder}.
     */
    public static final String LOGGER_FIELD_NAME="logger";

    /**
     * Name of protected {@link DefaultLoggingEvent}-field within {@link DefaultLoggingEventBuilder}.
     */
    public static final String LOGGING_EVENT_FIELD_NAME="loggingEvent";

    /**
     * Gets the name of the logger addressed by a logging-event builder.
     * @param builder Logging-event builder.
     * @return Name of the logger addressed by builder.
     */
    public static String getLoggerName(DefaultLoggingEventBuilder builder) {
        Logger logger=getLogger(builder);
        return logger==null?null:logger.getName();
    }

    /**
     * Gets the logger addressed by a logging-event builder.
     * @param builder Logging-event builder.
     * @return Logger addressed by logging-event builder.
     */
    public static Logger getLogger(DefaultLoggingEventBuilder builder) {
        return Reflection.getField(builder,LOGGER_FIELD_NAME,Logger.class);
    }

    /**
     * Gets the logging-event being build by a logging-event builder.
     * @param builder Logging-event builder.
     * @return Logging-event being build by logging-event builder.
     */
    public static DefaultLoggingEvent getLoggingEvent(DefaultLoggingEventBuilder builder) {
        return Reflection.getField(builder,LOGGING_EVENT_FIELD_NAME,DefaultLoggingEvent.class);
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
    public static Level getLevelLogger(DefaultLoggingEventBuilder builder) {
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
    public static Level getLevelForStatement(DefaultLoggingEventBuilder builder) {
        DefaultLoggingEvent loggingEvent=getLoggingEvent(builder);
        return loggingEvent==null?null:loggingEvent.getLevel();
    }
}
