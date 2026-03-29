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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.proxy;

import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.router.LoggerRouter;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.router.LoggerRouters;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventAware;

import java.util.function.Supplier;

/**
 * Utilities for instances of {@link ProxyLogger}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-29
 */
@UtilityClass
public class ProxyLoggers {

    @SuppressWarnings({"unused","java:S1066"})
    @lombok.Builder(builderClassName = "Builder")
    private static ProxyLogger createByBuilder(Supplier<String> nameSupplier,
                                               LoggerRouter loggerRouter,
                                               EventConsumer eventConsumer) {
        return new ProxyLogger(nameSupplier,loggerRouter,eventConsumer);
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private Supplier<String> nameSupplier;

        private LoggerRouter loggerRouter;

        private EventConsumer eventConsumer;

        public Builder name(String name) {
            return nameSupplier(()->name);
        }

        public Builder logger(Logger logger) {
            return loggerRouter((l,m)->logger);
        }

        public Builder logger(Level level,
                              Logger logger) {
            return loggerRouter(LoggerRouters.createLimitedByLevel(level,logger));
        }
    }
}
