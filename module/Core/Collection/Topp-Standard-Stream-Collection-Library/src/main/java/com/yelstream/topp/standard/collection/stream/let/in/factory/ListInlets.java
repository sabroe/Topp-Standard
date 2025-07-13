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
import com.yelstream.topp.standard.collection.stream.let.in.ListInlet;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Creation of {@link ListInlet} instances.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class ListInlets {


    public static <X> ListInlet<X> emptyInlet() {
        return byList(stream->{});
    }

    public static <X> ListInlet<X> byStream(Consumer<Stream<X>> streamConsumer) {
        Objects.requireNonNull(streamConsumer);
        return Inlets.createListInlet(streamConsumer,
                              list->streamConsumer.accept(list.stream()));
    }

    public static <X> ListInlet<X> byList(Consumer<List<X>> listConsumer) {
        Objects.requireNonNull(listConsumer);
        return Inlets.createListInlet(stream->listConsumer.accept(stream.toList()),
                                      listConsumer);
    }

    public static <X> ListInlet<X> of(Consumer<Stream<X>> streamConsumer,
                                      Consumer<List<X>> listConsumer) {
        Objects.requireNonNull(streamConsumer);
        Objects.requireNonNull(listConsumer);
        return Inlets.createListInlet(streamConsumer,listConsumer);
    }

}
