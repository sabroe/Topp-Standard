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

import com.yelstream.topp.standard.annotator.annotation.meta.Convenience;
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
    public static <B extends LoggingEventBuilder> Entry<Context,B> of(B loggingEventBuilder) {
        return DefaultEntry.of(loggingEventBuilder);
    }

    public static Entry<Context,LoggingEventBuilder> nop() {
        return of(NOPLoggingEventBuilder.singleton());
    }

    @Convenience
    public static AtLevelCreator<Context> of(Logger logger) {
        return AtLevelCreator.of(logger,Log::of);
    }

    @Convenience
    public static Entry<Context,LoggingEventBuilder> atError(Logger logger) {
        return of(logger.atError());
    }

    @Convenience
    public static Entry<Context,LoggingEventBuilder> atWarn(Logger logger) {
        return of(logger.atWarn());
    }

    @Convenience
    public static Entry<Context,LoggingEventBuilder> atInfo(Logger logger) {
        return of(logger.atInfo());
    }

    @Convenience
    public static Entry<Context,LoggingEventBuilder> atDebug(Logger logger) {
        return of(logger.atDebug());
    }

    @Convenience
    public static Entry<Context,LoggingEventBuilder> atTrace(Logger logger) {
        return of(logger.atTrace());
    }

    @Convenience
    public static Entry<Context,LoggingEventBuilder> atLevel(Logger logger,
                                                             Level level) {
        return of(logger.atLevel(level));
    }
}
