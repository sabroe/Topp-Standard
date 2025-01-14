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

package com.yelstream.topp.standard.log.assist.slf4j.spi.ex;

import org.slf4j.spi.LoggingEventBuilder;

/**
 * Default implementation of {@link LoggingEventBuilderEx}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-12
 *
 * @param <B> Native SLF4J logging-event builder type.
 */
public final class DefaultLoggingEventBuilderEx<B extends LoggingEventBuilder> extends AbstractLoggingEventBuilderEx<B,DefaultLoggingEventBuilderEx<B>> {
    /**
     * Constructor.
     * @param loggingEventBuilder Native SLF4J logging-event builder.
     */
    private DefaultLoggingEventBuilderEx(B loggingEventBuilder) {
        super(loggingEventBuilder);
    }

    @Override
    protected DefaultLoggingEventBuilderEx<B> self() {
        return this;
    }

    /**
     * Creates a new instance.
     * @param loggingEventBuilder Native SLF4J logging-event builder.
     * @return Created instance.
     * @param <B> Native SLF4J logging-event builder type.
     */
    public static <B extends LoggingEventBuilder> DefaultLoggingEventBuilderEx<B> of(B loggingEventBuilder) {
        return new DefaultLoggingEventBuilderEx<>(loggingEventBuilder);
    }
}