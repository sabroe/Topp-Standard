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

package com.yelstream.topp.standard.log.assist.slf4j.ex;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-26
 */
@UtilityClass
public class Scribers {

    public static <B extends LoggingEventBuilder> Scriber<B> of(B loggingEventBuilder) {
        return DefaultScriber.of(loggingEventBuilder);
    }

    public static <L extends Logger> LoggerAt<L> at(L logger) {
        return LoggerAt.of(logger);
    }

    @AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
    public static class LoggerAt<L extends Logger> {  //TO-DO: Consider the relevance of the generic; may be future-proof, may not!
        private final L logger;

        public Scriber<LoggingEventBuilder> error() {
            return Scribers.of(logger.atError());
        }

        public Scriber<LoggingEventBuilder> warn() {
            return Scribers.of(logger.atWarn());
        }

        public Scriber<LoggingEventBuilder> info() {
            return Scribers.of(logger.atInfo());
        }

        public Scriber<LoggingEventBuilder> debug() {
            return Scribers.of(logger.atDebug());
        }

        public Scriber<LoggingEventBuilder> trace() {
            return Scribers.of(logger.atTrace());
        }

        public Scriber<LoggingEventBuilder> level(Level level) {
            return Scribers.of(logger.atLevel(level));
        }
    }
}
