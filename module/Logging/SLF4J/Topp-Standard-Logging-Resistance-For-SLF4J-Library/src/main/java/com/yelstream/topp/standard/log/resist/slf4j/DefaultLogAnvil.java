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
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
//@AllArgsConstructor(access= AccessLevel.PROTECTED)
public final class DefaultLogAnvil<B extends LoggingEventBuilder> extends AbstractAnvil<LogAnvil<B>,Context,Scriber<B>> implements LogAnvil<B> {

    protected LogAnvil<B> self() {
        return this;
    }

    private DefaultLogAnvil(Context context,
                            Scriber<B> scriber) {
        super(context,scriber);
    }

    @Override
    public LogAnvil<B> journal(Consumer<Journal> consumer) {
        Journal journal=DefaultJournal.of(result);
        consumer.accept(journal);
        return self();
    }

    @Override
    public void log(Consumer<Scriber<B>> consumer) {
        apply(consumer);
        end();
    }

    @Override
    public void log(BiConsumer<Context, Scriber<B>> consumer) {
        apply(consumer);
        end();
    }

    @Override
    public void end() {
        use().log();
    }

    public static <B extends LoggingEventBuilder> DefaultLogAnvil<B> of(Context context,
                                                                        Scriber<B> scriber) {
        return new DefaultLogAnvil<>(context,scriber);
    }

    public static <B extends LoggingEventBuilder> DefaultLogAnvil<B> of(B loggingEventBuilder) {
        Context context=Context.of();
        Scriber<B> scriber=DefaultScriber.of(loggingEventBuilder);
        return new DefaultLogAnvil<>(context,scriber);
    }
}
