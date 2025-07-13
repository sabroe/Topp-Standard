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

package com.yelstream.topp.standard.collection.stream.let.in;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Utilities addressing instances of {@link Inlet}.
 * <p>
 *     The contained methods for creating inlets are not intended for direct usage;
 *     use the separate factory package for this purpose.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-04
 */
@UtilityClass
public class Inlets {


    public static <X> ListInlet<X> createListInlet(Consumer<Stream<X>> streamConsumer,
                                                   Consumer<List<X>> listConsumer) {
        Objects.requireNonNull(streamConsumer);
        Objects.requireNonNull(listConsumer);
        return DefaultListInlet.of(streamConsumer,listConsumer);
    }


    public static <X,R extends List<X>> SubListInlet<X,R> createSubListInlet(Consumer<Stream<X>> streamConsumer,
                                                                             Consumer<R> listConsumer) {
        Objects.requireNonNull(streamConsumer);
        Objects.requireNonNull(listConsumer);
        return DefaultSubListInlet.of(streamConsumer,listConsumer);
    }



}
