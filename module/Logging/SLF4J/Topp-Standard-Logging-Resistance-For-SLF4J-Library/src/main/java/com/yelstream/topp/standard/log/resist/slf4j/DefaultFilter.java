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

package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor(staticName="of",access= AccessLevel.PACKAGE)
public final class DefaultFilter<C extends Context,B extends LoggingEventBuilder> implements Filter<C,B> {

    /**
     *
     */
    private final DefaultEntry<C,B> entry;  //TO-DO: Consider exposing LESS and in non-circular fashion DefaultFilter<->DefaultEntry -- the fixed context and the item being replaceable!

    @Override
    public Filter<C,B> doSomething() {
        //TO-DO: Do something e.g. replace the item, add to the context.
        return this;
    }







    /**
     * Logging event builder.
     */
    private final LoggingEventBuilder source;

    /**
     * Container of a registry of conditionals.
     */
    private static class ConditionalRegistryHolder {
        /**
         * Associates (identifier,conditional).
         */
        private static final Map<String,Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder>> registry=new ConcurrentHashMap<>();
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
    public Entry<Context,LoggingEventBuilder> id(String id,
                                                 Consumer<Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder>> builderConsumer) {
        Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder> conditional=ConditionalRegistryHolder.registry.get(id);
        if (conditional==null) {
            Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> builder=createPresetBuilder(id);
            builderConsumer.accept(builder);
            conditional=builder.build();
            ConditionalRegistryHolder.registry.put(id,conditional);
        }
        return conditional.evaluate(source,DefaultEntry::of);
    }

    /**
     * Creates a conditional builder with the most common, neutral, default settings.
     * @param id Identifier.
     * @return Created conditional builder.
     */
    private static Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> createPresetBuilder(String id) {
        Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> builder=Conditional2.builder();
        builder.id(id);
        builder.context(Context.of());
        builder.onAccept(Context::updateStateByAccept);
        builder.onReject(Context::updateStateByReject);
        builder.neutralSourceSupplier(NOPLoggingEventBuilder::singleton);
        builder.sourceTransformation(Function.identity());
        return builder;
    }





}
