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
public class DefaultLogAnvil<B extends LoggingEventBuilder> extends AbstractLogAnvil<DefaultLogAnvil<B>,Context,DefaultLoggingEventBuilderEx<B>> {
    @Override
    protected DefaultLogAnvil<B> self() {
        return this;
    }

    public DefaultLogAnvil(Context context,
                           DefaultLoggingEventBuilderEx<B> loggingEventBuilderEx) {
        super(context,loggingEventBuilderEx);
    }
}
