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

package com.yelstream.topp.standard.collect.let.out;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utilities addressing instances of {@link Outlet}.
 * <p>
 *     The contained methods for creating outlets are not intended for direct usage;
 *     use the separate factory package for this purpose.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-04
 */
@UtilityClass
public class Outlets {


    public static <X> ListOutlet<X> createListOutlet(Supplier<Stream<X>> streamSupplier,
                                                     Supplier<List<X>> listSupplier) {
        Objects.requireNonNull(streamSupplier);
        Objects.requireNonNull(listSupplier);
        return DefaultListOutlet.of(streamSupplier,listSupplier);
    }


    public static <X,R extends List<X>> SubListOutlet<X,R> createSubListOutlet(Supplier<Stream<X>> streamSupplier,
                                                                               Supplier<R> listSupplier) {
        Objects.requireNonNull(streamSupplier);
        Objects.requireNonNull(listSupplier);
        return DefaultSubListOutlet.of(streamSupplier,listSupplier);
    }




}