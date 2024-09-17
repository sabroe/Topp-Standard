package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor(access=AccessLevel.PROTECTED)
public abstract class AbstractAnvil<A extends Anvil<A,C,R>,C,R> implements Anvil<A,C,R> {
    /**
     *
     */
    protected final C context;

    /**
     *
     */
    protected final R result;

    protected abstract A self();

    @Override
    public R use() {
        return result;
    }

    @Override
    public A apply(Consumer<R> consumer) {
        consumer.accept(result);
        return self();
    }

    @Override
    public A apply(BiConsumer<C,R> consumer) {
        consumer.accept(context,result);
        return self();
    }

    /**
     *
     */
    @Override
    public A end() {
        return self();
    }
}
