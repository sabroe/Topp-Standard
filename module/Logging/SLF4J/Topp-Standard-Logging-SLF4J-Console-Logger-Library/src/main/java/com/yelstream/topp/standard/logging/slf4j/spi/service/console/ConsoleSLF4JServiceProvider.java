/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.logging.slf4j.spi.service.console;

import com.yelstream.topp.standard.logging.slf4j.spi.event.FixedLoggingEvent;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.enable.LoggerEnablement;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.enable.LoggerEnablements;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumer;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume.EventConsumers;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.factory.CachedLoggerFactory;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.helpers.NormalizedLoggingCalls;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.helpers.NormalizedLoggingCall;
import com.yelstream.topp.standard.logging.slf4j.spi.message.MessageRenderers;
import com.yelstream.topp.standard.logging.slf4j.spi.version.Versions;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Console-logger as a SLF4J service provider.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-01
 */
public class ConsoleSLF4JServiceProvider implements SLF4JServiceProvider {

    private LoggerEnablement loggerEnablement=LoggerEnablements.create(Level.INFO);

    private EventConsumer eventConsumer;

    private static void log(LoggingEvent event) {
        System.out.println("EVENT:> event = "+event);
        String renderedMessage=MessageRenderers.DEFAULT_MESSAGE_RENDERER.render(event);
        System.out.println(String.format("[%s] %s",event.getLevel(),renderedMessage));
    }

    private MDCAdapter mdcAdapter;
    private IMarkerFactory markerFactory;

    @Override
    public String getRequestedApiVersion() {
        return Versions.DEFAULT_REQUESTED_API_VERSION;
    }

    @Override
    public void initialize() {
        mdcAdapter=new BasicMDCAdapter();
        markerFactory=new BasicMarkerFactory();
        eventConsumer=EventConsumers.builder2().mdcAdapter(mdcAdapter).eventConsumer(ConsoleSLF4JServiceProvider::log).build();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        NormalizedLoggingCall normalizedLoggingCall=
            NormalizedLoggingCalls.builder().callerBoundary(ConsoleLogger.class.getName()).mdcAdapter(mdcAdapter).eventConsumer(eventConsumer).build();
        return CachedLoggerFactory.of(name->new ConsoleLogger(ConsoleLogger.class.getName(),normalizedLoggingCall,name,loggerEnablement,eventConsumer));
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }
}
