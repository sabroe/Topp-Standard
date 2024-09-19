package com.yelstream.topp.standard.log.resist.slf4j;

import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.util.function.BooleanSupplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
//@AllArgsConstructor(access= AccessLevel.PROTECTED)
public final class Log {

    public static LogAnvil<Context,LoggingEventBuilder> of(LoggingEventBuilder loggingEventBuilder) {
        return new XXXLogAnvil(Context.of(),loggingEventBuilder);
    }

/*
    public Log nop() {
        return new Log(context,NOPLoggingEventBuilder.singleton());
    }
*/

/*
    public Log nop(BooleanSupplier predicate) {
        return predicate.getAsBoolean()?this:nop();
    }
*/

}
