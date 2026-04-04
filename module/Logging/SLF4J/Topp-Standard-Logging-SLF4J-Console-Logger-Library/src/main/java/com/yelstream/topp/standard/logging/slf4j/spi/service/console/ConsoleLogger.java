package com.yelstream.topp.standard.logging.slf4j.spi.service.console;

import com.yelstream.topp.standard.logging.slf4j.spi.logger.enable.LoggerEnablement;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.proxy.EventLogger;

public class ConsoleLogger extends EventLogger {

    public ConsoleLogger(String name,
                         LoggerEnablement loggerEnablement,
                         EventConsumer eventConsumer) {
        super(()->name, loggerEnablement, eventConsumer);
    }

}
