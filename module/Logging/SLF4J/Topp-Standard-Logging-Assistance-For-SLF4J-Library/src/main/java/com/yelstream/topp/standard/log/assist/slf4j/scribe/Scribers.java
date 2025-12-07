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

package com.yelstream.topp.standard.log.assist.slf4j.scribe;

import com.yelstream.topp.standard.log.assist.slf4j.logger.LoggerAt;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * Utility addressing instances of {@link Scriber}.
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

    public static LoggerAt<Scriber<LoggingEventBuilder>> of(Logger logger) {
        return LoggerAt.of(logger,Scribers::of);
    }
}
