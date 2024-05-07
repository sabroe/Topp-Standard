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
