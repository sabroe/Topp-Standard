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

package com.yelstream.topp.standard.util;

import com.yelstream.topp.standard.util.function.MemoizedIntSupplier;
import com.yelstream.topp.standard.util.function.MemoizedSupplier;
import lombok.Singular;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@UtilityClass
public class SupplierBuilders {

    @lombok.Builder(builderClassName="SupplierBuilder",builderMethodName="supplierBuilder")
    private static <T> Supplier<T> createSupplier(Supplier<T> source,
                                                  MemoizedSupplier.Strategy memoized,
                                                  @Singular List<Consumer<Supplier<T>>> consumers) {
        Supplier<T> supplier=createSupplier(source,memoized);
        notify(supplier,consumers);
        return supplier;
    }

    private static <T> Supplier<T> createSupplier(Supplier<T> source,
                                                  MemoizedSupplier.Strategy memoized) {
        Supplier<T> supplier=source;
        if (memoized!=null) {
            supplier=memoized.of(supplier);
        }
        return supplier;
    }

    private static <T> void notify(Supplier<T> supplier,
                                   List<Consumer<Supplier<T>>> consumers) {
        if (consumers!=null) {
            consumers.forEach(consumer->consumer.accept(supplier));
        }
    }

    public static class SupplierBuilder<T> {
        private Supplier<T> source=null;

        private MemoizedSupplier.Strategy memoized=null;

        private final List<Consumer<Supplier<T>>> consumer=new ArrayList<>();

        public SupplierBuilder<T> source(T value) {
            source=()->value;
            return this;
        }
    }

    @lombok.Builder(builderClassName="IntSupplierBuilder",builderMethodName="intSupplierBuilder")
    private static IntSupplier createIntSupplier(IntSupplier source,
                                                 MemoizedIntSupplier.Strategy memoized,
                                                 @Singular List<Consumer<IntSupplier>> consumers) {
        IntSupplier supplier=createIntSupplier(source,memoized);
        notify(supplier,consumers);
        return supplier;
    }

    private static IntSupplier createIntSupplier(IntSupplier source,
                                                 MemoizedIntSupplier.Strategy memoized) {
        IntSupplier supplier=source;
        if (memoized!=null) {
            supplier=memoized.of(supplier);
        }
        return supplier;
    }

    private static void notify(IntSupplier supplier,
                               List<Consumer<IntSupplier>> consumers) {
        if (consumers!=null) {
            consumers.forEach(consumer->consumer.accept(supplier));
        }
    }

    public static class IntSupplierBuilder {
        private IntSupplier source=null;

        private MemoizedIntSupplier.Strategy memoized=null;

        private final List<Consumer<IntSupplier>> consumer=new ArrayList<>();

        public IntSupplierBuilder source(int value) {
            source=()->value;
            return this;
        }
    }
}
