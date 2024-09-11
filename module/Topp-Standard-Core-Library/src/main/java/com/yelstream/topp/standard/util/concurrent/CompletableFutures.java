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

package com.yelstream.topp.standard.util.concurrent;

import com.yelstream.topp.standard.util.function.ex.SupplierWithException;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link CompletableFuture}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-10
 */
@UtilityClass
public class CompletableFutures {  //TO-DO: Consider moving to 'Furnace' project!
    /**
     * Creates a future that completes when all the given futures complete.
     * <p>
     *     If any of the given futures complete exceptionally,
     *     then the created future completes exceptionally.
     * </p>
     * <p>
     *     Otherwise, the created future provides a list of the individual results.
     * </p>
     * @param futures Given futures.
     * @return Created future completing when all argument futures complete.
     * @param <T> Type of result.
     */
    public static <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futures) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        CompletableFuture<Void> allFutures=CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allFutures.thenApply(v -> futures.stream().map(CompletableFuture::join).toList());
    }

    /**
     * Creates a future that completes when any of the given futures completes.
     * <p>
     *     If all of the given futures complete exceptionally,
     *     then the created future completes exceptionally.
     * </p>
     * <p>
     *     Otherwise, the created future provides a result from one of the given futures.
     * </p>
     * @param futures Given futures.
     * @return Created future completing when all argument futures complete.
     * @param <T> Type of result.
     */
    public static <T> CompletableFuture<T> anyOf(List<CompletableFuture<T>> futures) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        CompletableFuture<Object> anyFuture=CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0]));
        return anyFuture.thenApply(result ->
            futures.stream()
                .filter(future -> future.isDone() && !future.isCompletedExceptionally())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Failure to find any completed future!"))
                .join());
    }

    /**
     * Creates a future that completes when all the given futures have been created and completed in sequence.
     * <p>
     *     If any of the given futures complete exceptionally,
     *     then the created future completes exceptionally.
     * </p>
     * <p>
     *     Otherwise, the created future provides a list of the individual results.
     * </p>
     * @param futureSuppliers Given factories to futures.
     * @return Created future completing when all argument futures complete.
     * @param <T> Type of result.
     */
    public static <T> CompletableFuture<List<T>> allOfSequentially(List<Supplier<CompletableFuture<T>>> futureSuppliers) {
        CompletableFuture<Void> future=CompletableFuture.completedFuture(null);
        List<T> results=new ArrayList<>();
        for (Supplier<CompletableFuture<T>> futureSupplier: futureSuppliers) {
            future=future.thenCompose(v->futureSupplier.get().thenApply(result -> {
                results.add(result);
                return null;
            }));
        }
        return future.thenApply(v->results);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier);  //Yes, functionality as normal!
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
                                                       Executor executor) {
        return CompletableFuture.supplyAsync(supplier,executor);  //Yes, functionality as normal!
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);  //Yes, functionality as normal!
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable,
                                                   Executor executor) {
        return CompletableFuture.runAsync(runnable,executor);  //Yes, functionality as normal!
    }

    public static <U,E extends Exception> CompletableFuture<U> supplyAsync(SupplierWithException<U,E> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.get();
            } catch (Exception ex) {
                throw new CompletionException(ex);  //TO-DO: Add message!
            }
        });
    }

    public static <U,E extends Exception> CompletableFuture<U> supplyAsync(SupplierWithException<U,E> supplier,
                                                                           Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.get();
            } catch (Exception ex) {
                throw new CompletionException(ex);  //TO-DO: Add message!
            }
        },executor);
    }

    public static <U> CompletableFuture<U> runAsync(Callable<U> callable) {
        SupplierWithException<U,Exception> supplier=callable::call;
        return supplyAsync(supplier);
    }

    public static <U> CompletableFuture<U> runAsync(Callable<U> callable,
                                                    Executor executor) {
        SupplierWithException<U,Exception> supplier=callable::call;
        return supplyAsync(supplier,executor);
    }

    public static <S,T> BiFunction<? super S, Throwable, ? extends T> toFullTransformation(Function<? super S,? extends T> valueTransformation) {
        return (value,ex) -> {
            if (ex!=null) {
                throw new CompletionException(ex);  //Yes, explicitly wrap the exception!
            } else {
                return valueTransformation.apply(value);
            }
        };
    }

    public static <S,T> CompletableFuture<T> thenApply(CompletableFuture<S> future,
                                                       Function<? super S,? extends T> transformation) {
        return future.thenApply(transformation);
    }

    public static <S,T> CompletableFuture<T> thenApplyAsync(CompletableFuture<S> future,
                                                            Function<? super S,? extends T> transformation) {
        return future.thenApplyAsync(transformation);
    }

    public static <S,T> CompletableFuture<T> thenApplyAsync(CompletableFuture<S> future,
                                                            Function<? super S,? extends T> transformation,
                                                            Executor executor) {
        return future.thenApplyAsync(transformation,executor);
    }

    public static <S,T> CompletableFuture<T> handle(CompletableFuture<S> future,
                                                    Function<? super S,? extends T> transformation) {
        return future.handle(toFullTransformation(transformation));
    }

    public static <S,T> CompletableFuture<T> handleAsync(CompletableFuture<S> future,
                                                         Function<? super S,? extends T> transformation) {
        return future.handleAsync(toFullTransformation(transformation));
    }

    public static <S,T> CompletableFuture<T> handleAsync(CompletableFuture<S> future,
                                                         Function<? super S,? extends T> transformation,
                                                         Executor executor) {
        return future.handleAsync(toFullTransformation(transformation),executor);
    }
}
