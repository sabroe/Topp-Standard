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
import java.util.function.Supplier;

/**
 * Test of {@code com.yelstream.topp.standard.util.function.MemoizedSupplierTest}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-29
 */
@Slf4j
class MemoizedSupplierTest {
    /**
     * Test of {@link MemoizedSupplier#of(Supplier)}.
     */
    @Test
    void of() {
        {
            MemoizedSupplier<String> x=MemoizedSupplier.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::get);
        }
        {
            MemoizedSupplier<String> x=MemoizedSupplier.of(()->"x");
            Assertions.assertNotNull(x);
            Assertions.assertEquals("x",x.get());  //First call to #get() takes on path!
            Assertions.assertEquals("x",x.get());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedSupplier#of(Supplier,MemoizedSupplier.Strategy)}.
     */
    @ParameterizedTest
    @EnumSource(MemoizedSupplier.Strategy.class)
    void ofWithStrategy(MemoizedSupplier.Strategy strategy) {
        {
            MemoizedSupplier<String> x=MemoizedSupplier.of(null,strategy);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::get);
            Assertions.assertThrows(NullPointerException.class,x::get);
        }
        {
            MemoizedSupplier<String> x=MemoizedSupplier.of(()->"x",strategy);
            Assertions.assertNotNull(x);
            Assertions.assertEquals("x",x.get());  //First call to #get() takes on path!
            Assertions.assertEquals("x",x.get());  //Second call to #get() takes on path!
        }
        {
            MemoizedSupplier<String> x=strategy.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::get);
        }
        {
            MemoizedSupplier<String> x=strategy.of(()->"x");
            Assertions.assertNotNull(x);
            Assertions.assertEquals("x",x.get());  //First call to #get() takes on path!
            Assertions.assertEquals("x",x.get());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedSupplier.Strategy#of(Supplier)}.
     */
    @ParameterizedTest
    @EnumSource(MemoizedSupplier.Strategy.class)
    void strategyOf(MemoizedSupplier.Strategy strategy) {
        {
            MemoizedSupplier<String> x=strategy.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::get);
        }
        {
            MemoizedSupplier<String> x=strategy.of(()->"x");
            Assertions.assertNotNull(x);
            Assertions.assertEquals("x",x.get());  //First call to #get() takes on path!
            Assertions.assertEquals("x",x.get());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedSupplier#get()} meant to massage the actual implementations of the two strategies.
     * <p>
     *     It is expected that this leads to full coverage of {@link MemoizedSupplier}!
     * </p>
     */
    @ParameterizedTest
    @EnumSource(MemoizedSupplier.Strategy.class)
    @Timeout(value=3,unit= TimeUnit.SECONDS)
    void getImplementations(MemoizedSupplier.Strategy strategy) {
        Random random=new SecureRandom();
        int threadCount=100;
        int repetitionCount=100;
        try (ExecutorService executor=Executors.newFixedThreadPool(threadCount)) {
            for (int i=0; i<repetitionCount; i++) {
                int expectedValue=random.nextInt(100_000);
                MemoizedSupplier<Integer> memoizedSupplier=MemoizedSupplier.of(()->expectedValue,strategy);
                Semaphore semaphore=new Semaphore(0);
                for (int j=0; j<threadCount; j++) {
                    executor.submit(() -> {
                        semaphore.acquireUninterruptibly();
                        try {
                            Assertions.assertEquals(expectedValue,memoizedSupplier.get());
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
