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

package com.yelstream.topp.standard.log.assist.slf4j.ex;

import com.yelstream.topp.standard.log.assist.slf4j.spi.LoggingEventBuilders;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * Default implementation of {@link Scriber}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-20
 *
 * @param <B> Native SLF4J logging-event builder type.
 */
public final class DefaultScriber<B extends LoggingEventBuilder> extends AbstractLoggingEventBuilderEx<B,Scriber<B>> implements Scriber<B> {
    /**
     * Constructor.
     * @param loggingEventBuilder Native SLF4J logging-event builder.
     */
    public DefaultScriber(B loggingEventBuilder) {
        super(loggingEventBuilder);
    }

    @Override
    protected DefaultScriber<B> self() {
        return this;
    }

    @Override
    public boolean isEnabled() {
        return LoggingEventBuilders.isLoggingEnabled(delegate);
    }

    /**
     * Creates a new instance.
     * @param loggingEventBuilder Native SLF4J logging-event builder.
     * @return Created instance.
     */
    public static <B extends LoggingEventBuilder> DefaultScriber<B> of(B loggingEventBuilder) {
        return new DefaultScriber<>(loggingEventBuilder);
    }
}
