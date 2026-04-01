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

import lombok.AllArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * Proxy for instances of {@link SLF4JServiceProvider}.
 * <p>
 *     Individual methods can be overridden.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-24
 */
@AllArgsConstructor
public class ProxySLF4JServiceProvider implements SLF4JServiceProvider {

    private final SLF4JServiceProvider serviceProvider;

    @Override
    public String getRequestedApiVersion() {
        return serviceProvider.getRequestedApiVersion();
    }

    @Override
    public void initialize() {
        serviceProvider.initialize();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return serviceProvider.getLoggerFactory();
    }

    @Override
    public IMarkerFactory getMarkerFactory() {
        return serviceProvider.getMarkerFactory();
    }

    @Override
    public MDCAdapter getMDCAdapter() {
        return serviceProvider.getMDCAdapter();
    }
}
