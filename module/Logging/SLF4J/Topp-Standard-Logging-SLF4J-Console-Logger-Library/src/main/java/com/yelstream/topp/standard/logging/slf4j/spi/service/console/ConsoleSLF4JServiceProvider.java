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

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.simple.SimpleLoggerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * Console-logger as a SLF4J service provider.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-01
 */
public class ConsoleSLF4JServiceProvider implements SLF4JServiceProvider {

    @Override
    public ILoggerFactory getLoggerFactory() {
        System.out.println("ConsoleSLF4JServiceProvider.getLoggerFactory()");
        return new SimpleLoggerFactory();
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        System.out.println("ConsoleSLF4JServiceProvider.getMarkerFactory()");
        return null;
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        System.out.println("ConsoleSLF4JServiceProvider.getMDCAdapter()");
        return null;
    }

    @Override
    public String getRequestedApiVersion() {
//        System.out.println("ConsoleSLF4JServiceProvider.getRequestedApiVersion()");
        return "2.0.99";
    }

    @Override
    public void initialize() {
        System.out.println("ConsoleSLF4JServiceProvider.initialize()");
    }
}
