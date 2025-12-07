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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Test of {@link Suppliers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@Slf4j
class SuppliersTest {
    /**
     * Test of {@link Suppliers#fix(Object)}.
     */
    @Test
    void fixWithValue() {
        Supplier<Integer> supplier=Suppliers.fix(117);

        Assertions.assertEquals(117,supplier.get());  //First invocation
        Assertions.assertEquals(117,supplier.get());  //Second invocation
        Assertions.assertEquals(117,supplier.get());  //Third invocation
    }

    /**
     * Test of {@link Suppliers#fix(Supplier)}.
     */
    @Test
    void fix() {
        Supplier<Integer> source=()->117;
        Supplier<Integer> supplier=Suppliers.fix(source);

        Assertions.assertEquals(117,supplier.get());  //First invocation
        Assertions.assertEquals(117,supplier.get());  //Second invocation
        Assertions.assertEquals(117,supplier.get());  //Third invocation
    }

    /**
     * Test of {@link Suppliers#fixInAdvance(Supplier)}.
     */
    @Test
    void fixInAdvance() {
        Assertions.assertNull(Suppliers.fixInAdvance(null));

        Supplier<Integer> source=()->117;
        Supplier<Integer> supplier=Suppliers.fixInAdvance(source);

        Assertions.assertEquals(117,supplier.get());  //First invocation
        Assertions.assertEquals(117,supplier.get());  //Second invocation
        Assertions.assertEquals(117,supplier.get());  //Third invocation
    }

    /**
     * Test of {@link Suppliers#fixOnDemand(Supplier)}.
     */
    @Test
    void fixOnDemand() {
        Assertions.assertNull(Suppliers.fixOnDemand(null));

        AtomicInteger value=new AtomicInteger(0);  //No value set ... yet!
        Supplier<Integer> source=value::get;
        Supplier<Integer> supplier=Suppliers.fixOnDemand(source);  //If anything is executed and read here then it is not 117!
        value.set(117);  //Now do set the number expected!

        Assertions.assertEquals(117,supplier.get());  //First invocation
        Assertions.assertEquals(117,supplier.get());  //Second invocation
        Assertions.assertEquals(117,supplier.get());  //Third invocation
    }
}
