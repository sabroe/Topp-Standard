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

package com.yelstream.topp.standard.resource.util.let.out.factory;

import com.yelstream.topp.standard.resource.util.let.out.SubListOutlet;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Creation of {@link SubListOutlet} instances using the list type {@link ArrayList}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-07
 */
@UtilityClass
public class ArrayListOutlets {

    public static <X> Supplier<ArrayList<X>> collectionFactory() {
        return ArrayList::new;
    }

    public static <X> SubListOutlet<X,ArrayList<X>> emptyOutlet() {
        return byList(collectionFactory());
    }

    public static <X> SubListOutlet<X,ArrayList<X>> byStream(Supplier<Stream<X>> streamSupplier) {
        return SubListOutlets.byStream(streamSupplier,collectionFactory());
    }

    public static <X> SubListOutlet<X,ArrayList<X>> byList(Supplier<ArrayList<X>> listSupplier) {
        return SubListOutlets.byList(listSupplier);
    }

    public static <X> SubListOutlet<X,ArrayList<X>> of(Supplier<Stream<X>> streamSupplier,
                                                       Supplier<ArrayList<X>> listSupplier) {
        return SubListOutlets.of(streamSupplier,listSupplier);
    }

}
