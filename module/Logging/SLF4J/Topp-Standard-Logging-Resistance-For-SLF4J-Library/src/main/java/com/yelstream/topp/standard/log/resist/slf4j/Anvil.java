package com.yelstream.topp.standard.log.resist.slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @param <A> Type of self object.
 * @param <C> Type of context.
 * @param <R> Type of resulting item.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
public interface Anvil<A extends Anvil<A,C,R>,C,R> {
    /**
     *
     */
    R use();

    /**
     * Accesses the result of log filtering by handing it to a consumer.
     * @param consumer Receiver of the result.
     */
    A apply(Consumer<R> consumer);

    /**
     * Accesses the result of log filtering together with the context by handing these objects to a consumer.
     * @param consumer Receiver of the context and the result.
     */
    A apply(BiConsumer<C,R> consumer);

    /**
     *
     */
    A end();  //TO-DO: Consider removing this!
}
