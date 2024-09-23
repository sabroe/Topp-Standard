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

import com.yelstream.topp.standard.log.assist.slf4j.ex.DefaultScriber;
import com.yelstream.topp.standard.log.assist.slf4j.ex.Scriber;
import com.yelstream.topp.standard.util.function.AbstractBiAnvil;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Default implementation of {@link Entry}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
public final class DefaultEntry<C extends Context,B extends LoggingEventBuilder> extends AbstractBiAnvil<Entry<C,B>,C,Scriber<B>> implements Entry<C,B> {

    protected Entry<C,B> self() {
        return this;
    }

    private DefaultEntry(C context,
                         Scriber<B> scriber) {
        super(context,scriber);
    }

//    public <C2 extends Context,B2 extends LoggingEventBuilder> Entry<C2,B2> filter(Consumer<Filter> consumer) {
/*
    @Override
    public Entry<C,B> filter(Consumer<Filter> consumer) {
        Filter filter=DefaultFilter.of(this);
        consumer.accept(filter);
        return self();
    }
*/

    @Override
    public Entry<C,B> journal(Consumer<Journal> consumer) {
        Journal journal=DefaultJournal.of(item);
        consumer.accept(journal);
        return self();
    }

    @Override
    public void log(Consumer<Scriber<B>> consumer) {
        apply(consumer);
        end();
    }

    @Override
    public void log(BiConsumer<C,Scriber<B>> consumer) {
        apply(consumer);
        end();
    }

    @Override
    public void end() {
        use().log();
    }

    public static <C extends Context,B extends LoggingEventBuilder> Entry<C,B> of(C context,
                                                                                  Scriber<B> scriber) {
        return new DefaultEntry<>(context,scriber);
    }

    public static <B extends LoggingEventBuilder> Entry<Context,B> of(B loggingEventBuilder) {
        Context context=Context.of();
        Scriber<B> scriber=DefaultScriber.of(loggingEventBuilder);
        return new DefaultEntry<>(context,scriber);
    }


    public static <C extends Context,B extends LoggingEventBuilder> Entry<Context,B> of(C context,
                                                                                        B loggingEventBuilder) {
        Scriber<B> scriber=DefaultScriber.of(loggingEventBuilder);
        return new DefaultEntry<>(context,scriber);
    }

}
