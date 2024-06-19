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

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

/**
 * Test of {@link LongSuppliers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-05-07
 */
@Slf4j
class LongSuppliersTest {
    /**
     * Test of {@link LongSuppliers#fix(long)}.
     */
    @Test
    void fixWithValue() {
        LongSupplier supplier=LongSuppliers.fix(117L);

        Assertions.assertEquals(117L,supplier.getAsLong());  //First invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Second invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Third invocation
    }

    /**
     * Test of {@link LongSuppliers#fix(LongSupplier)}.
     */
    @Test
    void fix() {
        LongSupplier source=()->117L;
        LongSupplier supplier=LongSuppliers.fix(source);

        Assertions.assertEquals(117L,supplier.getAsLong());  //First invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Second invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Third invocation
    }

    /**
     * Test of {@link LongSuppliers#fixInAdvance(LongSupplier)}.
     */
    @Test
    void fixInAdvance() {
        Assertions.assertNull(LongSuppliers.fixInAdvance(null));

        LongSupplier source=()->117L;
        LongSupplier supplier=LongSuppliers.fixInAdvance(source);

        Assertions.assertEquals(117L,supplier.getAsLong());  //First invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Second invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Third invocation
    }

    /**
     * Test of {@link LongSuppliers#fixOnDemand(LongSupplier)}.
     */
    @Test
    void fixOnDemand() {
        Assertions.assertNull(LongSuppliers.fixOnDemand(null));

        AtomicLong value=new AtomicLong(0L);  //No value set ... yet!
        LongSupplier source=value::get;
        LongSupplier supplier=LongSuppliers.fixOnDemand(source);  //If anything is executed and read here then it is not 117L!
        value.set(117L);  //Now do set the number expected!

        Assertions.assertEquals(117L,supplier.getAsLong());  //First invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Second invocation
        Assertions.assertEquals(117L,supplier.getAsLong());  //Third invocation
    }
}
