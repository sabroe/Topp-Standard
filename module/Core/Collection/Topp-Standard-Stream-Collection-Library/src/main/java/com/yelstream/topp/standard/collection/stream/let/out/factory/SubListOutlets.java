/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.collection.stream.let.out.factory;

import com.yelstream.topp.standard.collection.stream.let.out.Outlets;
import com.yelstream.topp.standard.collection.stream.let.out.SubListOutlet;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creation of {@link SubListOutlet} instances using abstract list types.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class SubListOutlets {

    public static <X,R extends List<X>> SubListOutlet<X,R> emptyOutlet(Supplier<R> collectionFactory) {
        return byList(collectionFactory);
    }

    public static <X,R extends List<X>> SubListOutlet<X,R> byStream(Supplier<Stream<X>> streamSupplier,
                                                                    Supplier<R> collectionFactory) {
        Objects.requireNonNull(streamSupplier);
        return Outlets.createSubListOutlet(streamSupplier,
                                           ()->streamSupplier.get().collect(Collectors.toCollection(collectionFactory)));
    }

    public static <X,R extends List<X>> SubListOutlet<X,R> byList(Supplier<R> listSupplier) {
        Objects.requireNonNull(listSupplier);
        return Outlets.createSubListOutlet(()->listSupplier.get().stream(),
                                           listSupplier);
    }

    public static <X,R extends List<X>> SubListOutlet<X,R> of(Supplier<Stream<X>> streamSupplier,
                                                              Supplier<R> listSupplier) {
        Objects.requireNonNull(streamSupplier);
        Objects.requireNonNull(listSupplier);
        return Outlets.createSubListOutlet(streamSupplier,listSupplier);
    }

}
