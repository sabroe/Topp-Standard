package com.yelstream.topp.standard.log.resist.slf4j;

import com.yelstream.topp.standard.log.assist.slf4j.ex.DefaultLoggingEventBuilderEx;
import com.yelstream.topp.standard.log.assist.slf4j.ex.LoggingEventBuilderEx;
import org.slf4j.spi.LoggingEventBuilder;

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
public class XXXLogAnvil<C,B extends LoggingEventBuilder>
    extends AbstractAnvil<XXXLogAnvil<C,B>,C,DefaultLoggingEventBuilderEx<B>>
    implements LogAnvil<XXXLogAnvil<C,B>,C,B,DefaultLoggingEventBuilderEx<B>> {


    protected XXXLogAnvil(C context, DefaultLoggingEventBuilderEx<B> result) {
        super(context, result);
    }

    @Override
    protected XXXLogAnvil<C, B> self() {
        return null;
    }

    @Override
    public Journal journal() {
        return null;
    }

    @Override
    public void log(Consumer<DefaultLoggingEventBuilderEx<B>> consumer) {

    }

    @Override
    public void log(BiConsumer<C, DefaultLoggingEventBuilderEx<B>> consumer) {

    }
}
