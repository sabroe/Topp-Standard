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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.factory;

import lombok.AllArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Basic logger-factory backed by a map.
 * <p>
 *     In case direct access to the kept map is required,
 *     then feed a dedicated map and to which a reference is kept.
 *     This may provide access to, say, {@link Map#clear()}, {@link Map#keySet()}, {@link Map#entrySet()}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-02
 */
@AllArgsConstructor
@lombok.Builder(builderClassName = "Builder")
public class CachedLoggerFactory implements ILoggerFactory {
    /**
     * Creates a new logger by name.
     */
    private final Function<String,Logger> creator;

    /**
     * Associates (logger-name,logger) pairs.
     * <p>
     *     Prefer to keep this as a thread-safe {@link ConcurrentHashMap}.
     * </p>
     */
    @lombok.Builder.Default
    private final Map<String,Logger> loggerMap = new ConcurrentHashMap<>();

    @Override
    public Logger getLogger(String name) {
        return loggerMap.computeIfAbsent(name,creator);
    }

    public static CachedLoggerFactory of(Function<String,Logger> creator) {
        return builder().creator(creator).build();
    }
}
