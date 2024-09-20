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

package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

/**
 * Origin of log entry builders.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
@UtilityClass
public final class Log {


    public static <B extends LoggingEventBuilder> Entry<B> of(B loggingEventBuilder) {
        return DefaultEntry.of(loggingEventBuilder);
    }

    public static Entry<LoggingEventBuilder> nop() {
        return of(NOPLoggingEventBuilder.singleton());
    }

    public static Entry<LoggingEventBuilder> error(Logger logger) {
        return of(logger.atError());
    }

    public static Entry<LoggingEventBuilder> warn(Logger logger) {
        return of(logger.atWarn());
    }

    public static Entry<LoggingEventBuilder> info(Logger logger) {
        return of(logger.atInfo());
    }

    public static Entry<LoggingEventBuilder> debug(Logger logger) {
        return of(logger.atDebug());
    }

    public static Entry<LoggingEventBuilder> trace(Logger logger) {
        return of(logger.atTrace());
    }

    public static Entry<LoggingEventBuilder> of(Logger logger,
                                                Level level) {
        return of(logger.atLevel(level));
    }
}
