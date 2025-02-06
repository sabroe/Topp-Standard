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

import com.yelstream.topp.standard.log.assist.slf4j.scribe.Scriber;
import com.yelstream.topp.standard.util.function.BiAnvil;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Log entry builder.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
public interface Entry<C extends Context,B extends LoggingEventBuilder> extends BiAnvil<Entry<C,B>,C,Scriber<B>> {


//    @SuppressWarnings("java:S4977")
    //<C2 extends Context,B2 extends LoggingEventBuilder> Entry<C2,B2> filter(Consumer<Filter> consumer);
//     <D extends C,B2 extends LoggingEventBuilder> Entry<D,B2> transform(Consumer<Filter<C,B>> consumer);  //TO-DO: Transformer?
//    Entry<C,B> transform(Function<Filter<C,B>,Entry<C,B>> consumer);  //TO-DO: Transformer?

    //XXX tag();
    //LogAnvil<B> tag(Consumer<Tag> consumer);

    Entry<C,B> journal(Consumer<Journal> consumer);

    void log(Consumer<Scriber<B>> consumer);

    void log(BiConsumer<C,Scriber<B>> consumer);
}
