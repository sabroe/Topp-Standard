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

package com.yelstream.topp.standard.collection.stream.let.out.factory;

import com.yelstream.topp.standard.collection.stream.let.out.ListOutlet;
import com.yelstream.topp.standard.collection.stream.let.out.Outlets;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Creation of {@link ListOutlet} instances.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class ListOutlets {

    public static <X> ListOutlet<X> emptyOutlet() {
        return byList(List::of);
    }

    public static <X> ListOutlet<X> byStream(Supplier<Stream<X>> streamSupplier) {
        Objects.requireNonNull(streamSupplier);
        return Outlets.createListOutlet(streamSupplier,
                                        ()->streamSupplier.get().toList());
    }

    public static <X> ListOutlet<X> byList(Supplier<List<X>> listSupplier) {
        Objects.requireNonNull(listSupplier);
        return Outlets.createListOutlet(()->listSupplier.get().stream(),
                                        listSupplier);
    }

    public static <X> ListOutlet<X> of(Supplier<Stream<X>> streamSupplier,
                                       Supplier<List<X>> listSupplier) {
        Objects.requireNonNull(streamSupplier);
        Objects.requireNonNull(listSupplier);
        return Outlets.createListOutlet(streamSupplier,listSupplier);
    }
}
