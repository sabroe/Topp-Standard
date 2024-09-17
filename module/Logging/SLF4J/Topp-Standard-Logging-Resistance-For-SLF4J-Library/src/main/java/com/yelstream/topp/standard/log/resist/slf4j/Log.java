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
public final class Log extends AbstractAnvil<Log,Context,LoggingEventBuilder> {
    @Override
    protected Log self() {
        return this;
    }

    private Log(Context context,
                LoggingEventBuilder loggingEventBuilder) {
        super(context,loggingEventBuilder);
    }

    public static Log of(LoggingEventBuilder loggingEventBuilder) {
        return new Log(Context.of(),loggingEventBuilder);
    }

    public Log nop() {
        return new Log(context,NOPLoggingEventBuilder.singleton());
    }

    public Log nop(BooleanSupplier predicate) {
        return predicate.getAsBoolean()?this:nop();
    }

    //XXX filter();

    //XXX tag();

    public Journal journal() {
        return new Journal(this);
    }

    /**
     *
     */
    public Log end() {
        result.log();
        return super.end();
    }
}
