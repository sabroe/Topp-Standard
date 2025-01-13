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

import com.yelstream.topp.standard.annotator.annotation.meta.Consideration;
import com.yelstream.topp.standard.util.function.BooleanConsumer;
import org.slf4j.spi.LoggingEventBuilder;

/**
 * Enhanced logging-event builder improving upon the native SLF4J {@link LoggingEventBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-19
 *
 * @param <B> Type of native SLF4J logging-event builder.
 */
public interface Scriber<B extends LoggingEventBuilder> extends LoggingEventBuilderEx<Scriber<B>>,LoggingEventBuilderExAlias<Scriber<B>>,LoggingEventBuilderExAliasAggressive<Scriber<B>> {
    /**
     * Gets the enabling of logging.
     * @return Indicates, if logging is enabled.
     */
    boolean isEnabled();

    @Consideration
    default Scriber<B> enabled(BooleanConsumer consumer) {
        consumer.accept(isEnabled());
        return this;
    }

    @Consideration
    default Scriber<B> onEnabled(Runnable runnable) {
        if (isEnabled()) {
            runnable.run();
        }
        return this;
    }

    @Consideration
    default Scriber<B> onDisabled(Runnable runnable) {
        if (!isEnabled()) {
            runnable.run();
        }
        return this;
    }
}
