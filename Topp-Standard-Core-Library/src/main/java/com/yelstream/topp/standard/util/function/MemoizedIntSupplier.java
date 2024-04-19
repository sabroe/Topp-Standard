package com.yelstream.topp.standard.util.function;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;

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
 * </ol>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-19
 */
@RequiredArgsConstructor(staticName="of")
public class MemoizedIntSupplier implements IntSupplier {
    /**
     * Source supplier.
     */
    private final IntSupplier sourceSupplier;

    /**
     * Strategy for querying source supplier.
     */
    private final Strategy strategy;

    /**
     * Resolved value.
     * If the source supplier has been called then this is non-{@code null}.
     */
    private final AtomicReference<Integer> resolvedValue=new AtomicReference<>();

    /**
     * Valid strategies for querying source supplier.
     */
    @SuppressWarnings("java:S115")
    public enum Strategy {
        NonBlocking,
        DoubleChecked;

        /**
         * Creates a memoized supplier.
         * @param sourceSupplier Source supplier.
         * @return Memoized supplier.
         */
        public MemoizedIntSupplier of(IntSupplier sourceSupplier) {
            return MemoizedIntSupplier.of(sourceSupplier,this);
        }
    }

    @Override
    public int getAsInt() {
        return switch(strategy) {
            case NonBlocking -> getAsIntNonBlocking();
            case DoubleChecked -> getAsIntDoubleCheckedLocking();
        };
    }

    /**
     * Get supplied value using a non-blocking strategy.
     * @return Supplied value.
     */
    private int getAsIntNonBlocking() {
        Integer value=resolvedValue.get();
        if (value==null) {
            int newValue=sourceSupplier.getAsInt();
            if (resolvedValue.compareAndSet(null,newValue)) {
                value=newValue;
            } else {
                value=resolvedValue.get();
            }
        }
        return value;
    }

    /**
     * Get supplied value using a "double-checked locking" strategy.
     * @return Supplied value.
     */
    private int getAsIntDoubleCheckedLocking() {
        Integer value=resolvedValue.get();
        if (value==null) {
            synchronized(resolvedValue) {
                value=resolvedValue.get();
                if (value==null) {
                    value=sourceSupplier.getAsInt();
                    resolvedValue.set(value);
                }
            }
        }
        return value;
    }

    /**
     * Creates a memoized supplier.
     * @param sourceSupplier Source supplier.
     * @return Memoized supplier.
     */
    public static MemoizedIntSupplier of(IntSupplier sourceSupplier) {
        return MemoizedIntSupplier.of(sourceSupplier,Strategy.NonBlocking);
    }
}
