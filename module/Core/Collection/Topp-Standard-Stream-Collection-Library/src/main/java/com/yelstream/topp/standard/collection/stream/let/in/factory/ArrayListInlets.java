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

import com.yelstream.topp.standard.collection.stream.let.in.SubListInlet;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Creation of {@link SubListInlet} instances using the list type {@link ArrayList}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class ArrayListInlets {

    public static <X> Supplier<ArrayList<X>> collectionFactory() {
        return ArrayList::new;
    }

    public static <X> SubListInlet<X,ArrayList<X>> emptyInlet() {
        return byList(list->{});
    }

    public static <X> SubListInlet<X,ArrayList<X>> byStream(Consumer<Stream<X>> streamConsumer) {
        return SubListInlets.byStream(streamConsumer);
    }

    public static <X> SubListInlet<X,ArrayList<X>> byList(Consumer<ArrayList<X>> listConsumer) {
        return SubListInlets.byList(listConsumer,collectionFactory());
    }

    public static <X> SubListInlet<X,ArrayList<X>> of(Consumer<Stream<X>> streamConsumer,
                                                      Consumer<ArrayList<X>> listConsumer) {
        return SubListInlets.of(streamConsumer,listConsumer);
    }

}
