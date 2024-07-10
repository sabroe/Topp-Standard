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

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
     * @param futures the CompletableFutures
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
     * @param futures the CompletableFutures
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
}
