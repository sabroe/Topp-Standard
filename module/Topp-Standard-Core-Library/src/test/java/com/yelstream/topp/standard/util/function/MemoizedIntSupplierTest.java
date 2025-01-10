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
import java.util.function.IntSupplier;

/**
 * Test of {@code com.yelstream.topp.standard.util.function.MemoizedIntSupplierTest}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-29
 */
@Slf4j
class MemoizedIntSupplierTest {
    /**
     * Test of {@link MemoizedIntSupplier#of(IntSupplier)}.
     */
    @Test
    void of() {
        {
            MemoizedIntSupplier x=MemoizedIntSupplier.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsInt);
        }
        {
            MemoizedIntSupplier x=MemoizedIntSupplier.of(()->117);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsInt());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsInt());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedIntSupplier#of(IntSupplier,MemoizedIntSupplier.Strategy)}.
     */
    @ParameterizedTest
    @EnumSource(MemoizedIntSupplier.Strategy.class)
    void ofWithStrategy(MemoizedIntSupplier.Strategy strategy) {
        {
            MemoizedIntSupplier x=MemoizedIntSupplier.of(null,strategy);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsInt);
            Assertions.assertThrows(NullPointerException.class,x::getAsInt);
        }
        {
            MemoizedIntSupplier x=MemoizedIntSupplier.of(()->117,strategy);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsInt());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsInt());  //Second call to #get() takes on path!
        }
        {
            MemoizedIntSupplier x=strategy.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsInt);
        }
        {
            MemoizedIntSupplier x=strategy.of(()->117);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsInt());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsInt());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedIntSupplier.Strategy#of(IntSupplier)}.
     */
    @ParameterizedTest
    @EnumSource(MemoizedIntSupplier.Strategy.class)
    void strategyOf(MemoizedIntSupplier.Strategy strategy) {
        {
            MemoizedIntSupplier x=strategy.of(null);
            Assertions.assertNotNull(x);
            Assertions.assertThrows(NullPointerException.class,x::getAsInt);
        }
        {
            MemoizedIntSupplier x=strategy.of(()->117);
            Assertions.assertNotNull(x);
            Assertions.assertEquals(117,x.getAsInt());  //First call to #get() takes on path!
            Assertions.assertEquals(117,x.getAsInt());  //Second call to #get() takes on path!
        }
    }

    /**
     * Test of {@link MemoizedIntSupplier#getAsInt()} meant to massage the actual implementations of the two strategies.
     * <p>
     *     It is expected that this leads to full coverage of {@link MemoizedIntSupplier}!
     * </p>
     */
    @ParameterizedTest
    @EnumSource(MemoizedIntSupplier.Strategy.class)
    @Timeout(value=3,unit= TimeUnit.SECONDS)
    void getImplementations(MemoizedIntSupplier.Strategy strategy) {
        Random random=new SecureRandom();
        int threadCount=100;
        int repetitionCount=100;
        try (ExecutorService executor= Executors.newFixedThreadPool(threadCount)) {
            for (int i=0; i<repetitionCount; i++) {
                int expectedValue=random.nextInt(100_000);
                MemoizedIntSupplier memoizedSupplier=MemoizedIntSupplier.of(()->expectedValue,strategy);
                Semaphore semaphore=new Semaphore(0);
                for (int j=0; j<threadCount; j++) {
                    executor.submit(() -> {
                        semaphore.acquireUninterruptibly();
                        try {
                            Assertions.assertEquals(expectedValue,memoizedSupplier.getAsInt());
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
