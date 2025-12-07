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

package com.yelstream.topp.standard.util.function;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Memoized supplier encapsulating an original source supplier.
 * <p>
 *     Memoization is used as a technique to speed up execution and store calls to otherwise expensive suppliers.
 *     This allows to feed in values as suppliers and letting it be up to the point of usage to decide,
 *     if sources of values are dynamic or hide some expensive operation which should not be called more than once.
 * </p>
 * <p>
 *     Source suppliers are queried only on-demand and in one of two ways:
 * </p>
 * <ol>
 *     <li>
 *         If the non-blocking strategy is chosen then there is no protection against multiple parties calling
 *         the inner source supplier simultaneously and before a result has been obtained for the first time.
 *     </li>
 *     <li>
 *         If the "double-checked locking" strategy is chosen then there is guarantied to be only a single invocation
 *         of the source supplier while blocking the first invocation of this memoized supplier.
 *     </li>
 * </ol>
 * <p>
 *     This is thread-safe given that the source supplier is thread safe or the strategy is "double-checked locking".
 * </p>
 *
 * @param <X> Type of value supplied.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-19
 */
@RequiredArgsConstructor(staticName="of")
public class MemoizedSupplier<X> implements Supplier<X> {
    /**
     * Source supplier.
     */
    private final Supplier<X> sourceSupplier;

    /**
     * Strategy for querying source supplier.
     */
    private final Strategy strategy;

    /**
     * Resolved value as a supplier.
     * <p>
     *     If the source supplier has returned {@code null} then
     *     the resolved supplier is non-{@code null} and returns {@code null}.
     * </p>
     */
    private final AtomicReference<Supplier<X>> resolvedSupplier=new AtomicReference<>();

    /**
     * Valid strategies for querying source supplier.
     */
    @SuppressWarnings("java:S115")
    public enum Strategy {
        /**
         * Non-blocking with no protection against multiple parties calling the inner source supplier simultaneously
         * and before a result has been obtained for the first time.
         */
        NonBlocking,

        /**
         * Double-checked locking with the guarantee of leading to only a single invocation of the source supplier
         * while blocking the first invocation of this memoized supplier.
         */
        DoubleChecked;

        /**
         * Creates a memoized supplier.
         * @param sourceSupplier Source supplier.
         * @return Memoized supplier.
         * @param <X> Type of value supplied.
         */
        public <X> MemoizedSupplier<X> of(Supplier<X> sourceSupplier) {
            return MemoizedSupplier.of(sourceSupplier,this);
        }
    }

    @Override
    public X get() {
        return switch(strategy) {
            case NonBlocking -> getNonBlocking();
            case DoubleChecked -> getDoubleCheckedLocking();
        };
    }

    /**
     * Get supplied value using a non-blocking strategy.
     * @return Supplied value.
     */
    private X getNonBlocking() {
        Supplier<X> currentResolvedSupplier=resolvedSupplier.get();
        if (currentResolvedSupplier==null) {
            X value=sourceSupplier.get();
            Supplier<X> newResolvedSupplier=()->value;
            if (resolvedSupplier.compareAndSet(null,newResolvedSupplier)) {
                currentResolvedSupplier=newResolvedSupplier;
            } else {
                currentResolvedSupplier=resolvedSupplier.get();
            }
        }
        return currentResolvedSupplier.get();
    }

    /**
     * Get supplied value using a "double-checked locking" strategy.
     * @return Supplied value.
     */
    private X getDoubleCheckedLocking() {
        Supplier<X> currentResolvedSupplier=resolvedSupplier.get();
        if (currentResolvedSupplier==null) {
            synchronized (resolvedSupplier) {
                currentResolvedSupplier=resolvedSupplier.get();
                if (currentResolvedSupplier==null) {
                    X value=sourceSupplier.get();
                    currentResolvedSupplier=()->value;
                    resolvedSupplier.set(currentResolvedSupplier);
                }
            }
        }
        return currentResolvedSupplier.get();
    }

    /**
     * Creates a memoized supplier.
     * <p>
     *     This uses the non-blocking strategy.
     * </p>
     * @param sourceSupplier Source supplier.
     * @return Memoized supplier.
     * @param <X> Type of value supplied.
     */
    public static <X> MemoizedSupplier<X> of(Supplier<X> sourceSupplier) {
        return MemoizedSupplier.of(sourceSupplier,Strategy.NonBlocking);
    }
}
