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

package com.yelstream.topp.standard.log.assist.slf4j.scribe.factory;

import com.yelstream.topp.standard.log.assist.slf4j.logger.LoggerAt;
import com.yelstream.topp.standard.log.assist.slf4j.scribe.Scriber;
import com.yelstream.topp.standard.log.assist.slf4j.scribe.Scribers;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * Factory of {@link Scriber} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-12
 */
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class ScriberFactory {
    public LoggerAt<Scriber<LoggingEventBuilder>> from(Logger logger) {
        return LoggerAt.of(logger,Scribers::of);
    }

    public LoggerAt<Scriber<LoggingEventBuilder>> from(Class<?> clazz) {
        return from(LoggerFactory.getLogger(clazz));
    }

    public LoggerAt<Scriber<LoggingEventBuilder>> from(String name) {
        return from(LoggerFactory.getLogger(name));
    }

    public static ScriberFactory of() {
        return new ScriberFactory();
    }
}
