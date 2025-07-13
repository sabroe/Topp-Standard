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

package com.yelstream.topp.standard.collection.stream.let.in.factory;

import com.yelstream.topp.standard.collection.stream.let.in.Inlets;
import com.yelstream.topp.standard.collection.stream.let.in.SubListInlet;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creation of {@link SubListInlet} instances using abstract list types.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class SubListInlets {


    public static <X,R extends List<X>> SubListInlet<X,R> emptyInlet(Supplier<R> collectionFactory) {
        return byList(list->{},collectionFactory);
    }

    public static <X,R extends List<X>> SubListInlet<X,R> byStream(Consumer<Stream<X>> streamConsumer) {
        Objects.requireNonNull(streamConsumer);
        return Inlets.createSubListInlet(streamConsumer,
                                      list->streamConsumer.accept(list.stream()));
    }

    public static <X,R extends List<X>> SubListInlet<X,R> byList(Consumer<R> listConsumer,
                                                                 Supplier<R> collectionFactory) {
        Objects.requireNonNull(listConsumer);
        return Inlets.createSubListInlet(stream->listConsumer.accept(stream.collect(Collectors.toCollection(collectionFactory))),
                                         listConsumer);
    }

    public static <X,R extends List<X>> SubListInlet<X,R> of(Consumer<Stream<X>> streamConsumer,
                                                             Consumer<R> listConsumer) {
        Objects.requireNonNull(streamConsumer);
        Objects.requireNonNull(listConsumer);
        return Inlets.createSubListInlet(streamConsumer,listConsumer);
    }


}
