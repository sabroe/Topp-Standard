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

package com.yelstream.topp.standard.log.slf4j.spi;

import lombok.AllArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

import java.util.List;
import java.util.function.Consumer;

/**
 * Chain-of-responsibility for instances of {@link SLF4JServiceProvider}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-24
 */
@AllArgsConstructor(staticName = "of")
class ChainSLF4JServiceProvider implements SLF4JServiceProvider {

    private final String requestedApiVersion;
    private final List<SLF4JServiceProvider> serviceProviders;

    @Override
    public String getRequestedApiVersion() {
        return requestedApiVersion;
    }

    @Override
    public void initialize() {
        serviceProviders.forEach(SLF4JServiceProvider::initialize);
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return null;//loggerFactory;
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return new BasicMarkerFactory();
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return new NOPMDCAdapter();
    }
}
