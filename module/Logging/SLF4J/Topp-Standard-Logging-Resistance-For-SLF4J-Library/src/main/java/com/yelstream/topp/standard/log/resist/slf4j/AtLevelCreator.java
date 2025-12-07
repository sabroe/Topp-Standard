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

package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Function;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-23
 */
@AllArgsConstructor(staticName="of",access=AccessLevel.PACKAGE)
public final class AtLevelCreator<C extends Context> {
    /**
     * Logger.
     */
    private final Logger logger;

    /**
     * Entry creator.
     */
    private final Function<LoggingEventBuilder,Entry<C,LoggingEventBuilder>> entryCreator;

    private Entry<C,LoggingEventBuilder> of(LoggingEventBuilder loggingEventBuilder) {
        return entryCreator.apply(loggingEventBuilder);
    }

    public Entry<C,LoggingEventBuilder> atError() {
        return of(logger.atError());
    }

    public Entry<C,LoggingEventBuilder> atWarn() {
        return of(logger.atWarn());
    }

    public Entry<C,LoggingEventBuilder> atIinfo() {
        return of(logger.atInfo());
    }

    public Entry<C,LoggingEventBuilder> atDebug() {
        return of(logger.atDebug());
    }

    public Entry<C,LoggingEventBuilder> atTrace() {
        return of(logger.atTrace());
    }

    public Entry<C,LoggingEventBuilder> atLevel(Level level) {
        return of(logger.atLevel(level));
    }
}
