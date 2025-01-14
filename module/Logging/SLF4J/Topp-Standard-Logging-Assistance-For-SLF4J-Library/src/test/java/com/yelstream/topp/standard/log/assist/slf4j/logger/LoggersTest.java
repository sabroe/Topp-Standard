/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.log.assist.slf4j.logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.List;

/**
 * Tests {@link Loggers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-10
 */
class LoggersTest {
    /**
     * Tests {@link Loggers#getLogger(String)}.
     */
    @Test
    void obtainLoggerByName() {
        String loggerName=Logger.ROOT_LOGGER_NAME;
        Logger logger=Loggers.getLogger(loggerName);

        Assertions.assertNotNull(logger);
        Assertions.assertEquals(loggerName,logger.getName());
    }

    /**
     * Tests {@link Loggers#getLevels(Logger)}.
     */
    @Test
    void obtainsLevelsFromLogger() {
        Logger logger=Loggers.getLogger(Logger.ROOT_LOGGER_NAME);

        List<Level> levels=Loggers.getLevels(logger);

        Assertions.assertNotNull(levels);
        Assertions.assertEquals(List.of(Level.INFO,Level.WARN,Level.ERROR),levels);
    }

    /**
     * Tests {@link Loggers#getLevel(Logger)}.
     */
    @Test
    void obtainLevelFromLogger() {
        Logger logger=Loggers.getLogger(Logger.ROOT_LOGGER_NAME);

        Level level=Loggers.getLevel(logger);

        Assertions.assertNotNull(level);
        Assertions.assertEquals(Level.INFO,level);
    }
}
