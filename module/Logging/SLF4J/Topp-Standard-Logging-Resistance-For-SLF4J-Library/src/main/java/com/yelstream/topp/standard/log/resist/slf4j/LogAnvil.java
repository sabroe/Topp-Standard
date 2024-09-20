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
public interface LogAnvil<B extends LoggingEventBuilder> extends Anvil<LogAnvil<B>,Context,Scriber<B>> {


    //XXX filter();

    //XXX tag();
    //LogAnvil<B> tag(Consumer<Tag> consumer);

    LogAnvil<B> journal(Consumer<Journal> consumer);

    void log(Consumer<Scriber<B>> consumer);

    void log(BiConsumer<Context,Scriber<B>> consumer);
}