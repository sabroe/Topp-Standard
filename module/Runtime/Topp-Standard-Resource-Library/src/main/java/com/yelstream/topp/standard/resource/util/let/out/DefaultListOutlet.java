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

package com.yelstream.topp.standard.resource.util.let.out;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-04
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
final class DefaultListOutlet<X> implements ListOutlet<X> {
    /**
     * Stream-supplier.
     */
    private final Supplier<Stream<X>> streamSupplier;

    /**
     * List-supplier.
     */
    private final Supplier<List<X>> listSupplier;

    @Override
    public Stream<X> stream() {
        return streamSupplier.get();
    }

    @Override
    public List<X> get() {
        return listSupplier.get();
    }

    public static <X> DefaultListOutlet<X> ofList(Supplier<List<X>> listSupplier) {
        return of(()->listSupplier.get().stream(),
                  ()->Collections.unmodifiableList(listSupplier.get()));
    }

    public static <X> DefaultListOutlet<X> ofStream(Supplier<Stream<X>> streamSupplier) {
        return of(streamSupplier,
                  ()->streamSupplier.get().toList());
    }
}
