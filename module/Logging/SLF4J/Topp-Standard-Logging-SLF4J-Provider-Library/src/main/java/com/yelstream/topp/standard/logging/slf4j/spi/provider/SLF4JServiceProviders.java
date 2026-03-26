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

package com.yelstream.topp.standard.logging.slf4j.spi.provider;

import lombok.experimental.UtilityClass;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.util.function.Consumer;

/**
 * Utility for instances of {@link SLF4JServiceProvider}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-24
 */
@UtilityClass
public class SLF4JServiceProviders {

    public static final String DEFAULT_REQUESTED_API_VERSION="2.0.99";

    @lombok.Builder(builderClassName = "Builder")
    private static SLF4JServiceProvider createByBuilder(String requestedApiVersion,
                                                        Consumer<SLF4JServiceProvider> initializeOperator,
                                                        ILoggerFactory loggerFactory,
                                                        IMarkerFactory markerFactory,
                                                        MDCAdapter mdcAdapter) {
        return SimpleSLF4JServiceProvider.of(requestedApiVersion,initializeOperator,loggerFactory,markerFactory,mdcAdapter);
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        private String requestedApiVersion=DEFAULT_REQUESTED_API_VERSION;
        private Consumer<SLF4JServiceProvider> initializeOperator=SLF4JServiceProvider::initialize;
        private ILoggerFactory loggerFactory;
        private IMarkerFactory markerFactory;
        private MDCAdapter mdcAdapter;
    }
}
