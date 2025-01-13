package com.yelstream.topp.standard.log.assist.slf4j;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Function;

/**
 *
 *
 */
@AllArgsConstructor(staticName="of",access= AccessLevel.PRIVATE)
public class LoggerAt<L extends Logger,R> {  //TO-DO: Fix this, replace the other 2x 'LoggerAt' implementations!
    private final L logger;
    private final Function<LoggingEventBuilder,R> resolver;

    public R error() {
        return resolver.apply(logger.atError());
    }

    public R warn() {
        return resolver.apply(logger.atWarn());
    }

    public R info() {
        return resolver.apply(logger.atInfo());
    }

    public R debug() {
        return resolver.apply(logger.atDebug());
    }

    public R trace() {
        return resolver.apply(logger.atTrace());
    }

    public R level(Level level) {
        return resolver.apply(logger.atLevel(level));
    }
}
