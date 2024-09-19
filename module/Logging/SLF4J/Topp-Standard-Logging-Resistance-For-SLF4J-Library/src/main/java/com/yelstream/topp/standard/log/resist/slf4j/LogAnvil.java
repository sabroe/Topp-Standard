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
//public interface LogAnvil<A extends LogAnvil<A,C,B,R>,C,B extends LoggingEventBuilder,R extends LoggingEventBuilderEx<R>> extends Anvil<A,C,R> {

public interface LogAnvil<C,B extends LoggingEventBuilder,R extends DefaultLoggingEventBuilderEx<B>> extends Anvil<LogAnvil<C,B,R>,C,R> {


    //XXX filter();

    //XXX tag();

    Journal journal();

    void log(Consumer<R> consumer);

    void log(BiConsumer<C,R> consumer);
}
