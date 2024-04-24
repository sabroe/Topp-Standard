package com.yelstream.topp.standard.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test of {@link Runnables}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@Slf4j
class RunnablesTest {

    /**
     * Test of {@link Runnables#of(AutoCloseable)}
     */
    @Test
    void createRunnable() {
        {
            AtomicBoolean closed=new AtomicBoolean();
            AutoCloseable x=()->closed.set(true);
            Runnable runnable=Runnables.of(x);

            Assertions.assertFalse(closed.get());
            runnable.run();
            Assertions.assertTrue(closed.get());
        }
        {
            AutoCloseable x=()->{ throw new IllegalArgumentException("Err!"); };
            Runnable runnable=Runnables.of(x);
            Assertions.assertThrows(IllegalStateException.class,()->Runnables.run(runnable));
        }
    }

    /**
     * Test of {@link Runnables#run(Runnable)}
     */
    @Test
    void run() {
        Assertions.assertDoesNotThrow(()->Runnables.run(null));

        AtomicBoolean run=new AtomicBoolean();
        Runnable runnable=()->run.set(true);

        Assertions.assertFalse(run.get());
        Runnables.run(runnable);
        Assertions.assertTrue(run.get());
    }
}
