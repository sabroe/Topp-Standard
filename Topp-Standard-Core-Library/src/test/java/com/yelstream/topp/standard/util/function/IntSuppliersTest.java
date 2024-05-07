package com.yelstream.topp.standard.util.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

/**
 * Test of {@link IntSuppliers}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-05-07
 */
@Slf4j
class IntSuppliersTest {
    /**
     * Test of {@link IntSuppliers#fix(int)}.
     */
    @Test
    void fixWithValue() {
        IntSupplier supplier=IntSuppliers.fix(117);

        Assertions.assertEquals(117,supplier.getAsInt());  //First invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Second invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Third invocation
    }

    /**
     * Test of {@link IntSuppliers#fix(IntSupplier)}.
     */
    @Test
    void fix() {
        IntSupplier source=()->117;
        IntSupplier supplier=IntSuppliers.fix(source);

        Assertions.assertEquals(117,supplier.getAsInt());  //First invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Second invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Third invocation
    }

    /**
     * Test of {@link IntSuppliers#fixInAdvance(IntSupplier)}.
     */
    @Test
    void fixInAdvance() {
        Assertions.assertNull(IntSuppliers.fixInAdvance(null));

        IntSupplier source=()->117;
        IntSupplier supplier=IntSuppliers.fixInAdvance(source);

        Assertions.assertEquals(117,supplier.getAsInt());  //First invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Second invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Third invocation
    }

    /**
     * Test of {@link IntSuppliers#fixOnDemand(IntSupplier)}.
     */
    @Test
    void fixOnDemand() {
        Assertions.assertNull(IntSuppliers.fixOnDemand(null));

        AtomicInteger value=new AtomicInteger(0);  //No value set ... yet!
        IntSupplier source=value::get;
        IntSupplier supplier=IntSuppliers.fixOnDemand(source);  //If anything is executed and read here then it is not 117!
        value.set(117);  //Now do set the number expected!

        Assertions.assertEquals(117,supplier.getAsInt());  //First invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Second invocation
        Assertions.assertEquals(117,supplier.getAsInt());  //Third invocation
    }
}
