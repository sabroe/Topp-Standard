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

package com.yelstream.topp.standard.util.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

/**
 * Test of {@code com.yelstream.topp.standard.util.function.MemoizedLongSupplierTest}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-29
 */
@Slf4j
class MemoizedLongSupplierTest {
    /**
     * Test of {@link MemoizedLongSupplier#of(LongSupplier)}.
     */
    @Test
    void of() {
        {
            MemoizedLongSupplier x=MemoizedLongSupplier.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsLong);
        }
        {
            MemoizedLongSupplier x=MemoizedLongSupplier.of(()->117);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsLong());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsLong());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedLongSupplier#of(LongSupplier,MemoizedLongSupplier.Strategy)}.
     */
    @ParameterizedTest
    @EnumSource(MemoizedLongSupplier.Strategy.class)
    void ofWithStrategy(MemoizedLongSupplier.Strategy strategy) {
        {
            MemoizedLongSupplier x=MemoizedLongSupplier.of(null,strategy);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsLong);
            Assertions.assertThrows(NullPointerException.class,x::getAsLong);
        }
        {
            MemoizedLongSupplier x=MemoizedLongSupplier.of(()->117,strategy);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsLong());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsLong());  //Second call to #get() takes on path!
        }
        {
            MemoizedLongSupplier x=strategy.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsLong);
        }
        {
            MemoizedLongSupplier x=strategy.of(()->117);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsLong());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsLong());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedLongSupplier.Strategy#of(LongSupplier)}.
     */
    @ParameterizedTest
    @EnumSource(MemoizedLongSupplier.Strategy.class)
    void strategyOf(MemoizedLongSupplier.Strategy strategy) {
        {
            MemoizedLongSupplier x=strategy.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsLong);
        }
        {
            MemoizedLongSupplier x=strategy.of(()->117);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsLong());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsLong());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedLongSupplier#getAsLong()} meant to massage the actual implementations of the two strategies.
     * <p>
     *     It is expected that this leads to full coverage of {@link MemoizedLongSupplier}!
     * </p>
     */
    @ParameterizedTest
    @EnumSource(MemoizedLongSupplier.Strategy.class)
    @Timeout(value=3,unit= TimeUnit.SECONDS)
    void getImplementations(MemoizedLongSupplier.Strategy strategy) {
        Random random=new SecureRandom();
        int threadCount=100;
        int repetitionCount=100;
        try (ExecutorService executor= Executors.newFixedThreadPool(threadCount)) {
            for (int i=0; i<repetitionCount; i++) {
                int expectedValue=random.nextInt(100_000);
                MemoizedLongSupplier memoizedSupplier=MemoizedLongSupplier.of(()->expectedValue,strategy);
                Semaphore semaphore=new Semaphore(0);
                for (int j=0; j<threadCount; j++) {
                    executor.submit(() -> {
                        semaphore.acquireUninterruptibly();
                        try {
                            Assertions.assertEquals(expectedValue,memoizedSupplier.getAsLong());
                        } finally {
                            semaphore.release();
                        }
                    });
                }
                semaphore.release(threadCount);
            }
            executor.shutdown();
        }
    }
}
