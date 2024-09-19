package com.yelstream.topp.standard.log.resist.slf4j;

import com.yelstream.topp.standard.log.assist.slf4j.ex.LoggingEventBuilderEx;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
//@AllArgsConstructor(access= AccessLevel.PROTECTED)
public abstract class AbstractLogAnvil<A extends Anvil<A,C,R>,C,R extends LoggingEventBuilderEx<R>> extends AbstractAnvil<A,C,R> {

    protected AbstractLogAnvil(C context,
                               R loggingEventBuilderEx) {
        super(context,loggingEventBuilderEx);
    }

    public void log(Consumer<R> consumer) {
        apply(consumer);
        result.log();
    }

    public void log(BiConsumer<C,R> consumer) {
        consumer.accept(context,result);
        result.log();
    }
}
