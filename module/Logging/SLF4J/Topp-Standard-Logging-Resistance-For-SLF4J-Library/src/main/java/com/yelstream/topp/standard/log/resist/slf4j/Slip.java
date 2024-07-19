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

package com.yelstream.topp.standard.log.resist.slf4j;

import com.yelstream.topp.standard.log.resist.slf4j.filter.Conditional;
import com.yelstream.topp.standard.log.resist.slf4j.filter.FilterResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Main entry point for SLF4J log filtering.
 * <p>
 *     This builds upon the fluent API introduced by SLF4J version 2.0.0.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@Slf4j
@AllArgsConstructor(staticName="of")
public class Slip {
    /**
     * Logging event builder.
     */
    private final LoggingEventBuilder source;

    /**
     * Transforms this entry point to a result ready for logging, performing no filtering.
     * @return Log filtering result.
     */
    public FilterResult<Context,LoggingEventBuilder> nop() {
        Conditional<Context,LoggingEventBuilder,LoggingEventBuilder> conditional=Conditional.identity();
        return conditional.evaluate(source);
    }

    /**
     * Container of a registry of conditionals.
     */
    private static class ConditionalRegistryHolder {
        /**
         * Associates (identifier,conditional).
         */
        private static final Map<String,Conditional<Context,LoggingEventBuilder,LoggingEventBuilder>> registry=new ConcurrentHashMap<>();
    }

    /**
     * Transforms this entry point to a result ready for logging, performing active filtering.
     * @param id Identification.
     * @param builderConsumer Handler doing initialization of a conditional transformation.
     *                        Initialization is done by addressing a conditional transformation builder with preset values.
     *                        Note that the actual initialization is done only for the first invocation.
     * @return Log filtering result.
     */
    @SuppressWarnings("java:S1854")
    public FilterResult<Context,LoggingEventBuilder> id(String id,
                                                        Consumer<Conditional.Builder<Context,LoggingEventBuilder,LoggingEventBuilder>> builderConsumer) {
        Conditional<Context,LoggingEventBuilder,LoggingEventBuilder> conditional=ConditionalRegistryHolder.registry.get(id);
        if (conditional==null) {
            Conditional.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> builder=createPresetBuilder(id);
            builderConsumer.accept(builder);
            conditional=builder.build();
            ConditionalRegistryHolder.registry.put(id,conditional);
        }
        return conditional.evaluate(source);
    }

    /**
     * Creates a conditional builder with the most common, neutral, default settings.
     * @param id Identifier.
     * @return Created conditional builder.
     */
    private static Conditional.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> createPresetBuilder(String id) {
        Conditional.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> builder=Conditional.builder();
        builder.id(id);
        builder.context(Context.of());
        builder.onAccept(Context::updateStateByAccept);
        builder.onReject(Context::updateStateByReject);
        builder.neutralSourceSupplier(NOPLoggingEventBuilder::singleton);
        builder.sourceTransformation(Function.identity());
        return builder;
    }
}
