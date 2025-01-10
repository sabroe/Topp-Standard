/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.smallrye.config.source.factory;

import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.OptionalInt;

/**
 * Static proxy for {@link ConfigSourceFactory} instances.
 * <p>
 *     The primary, intended usage is to be able to set up non-trivial configuration-sources within
 *     a specific sub-class of this proxy having a default constructor and hence applicable
 *     for a {@link java.util.ServiceLoader}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
@AllArgsConstructor
public class ProxyConfigSourceFactory implements ConfigSourceFactory {
    /**
     * Wrapped configuration-source factory.
     * Not open for external access.
     */
    private final ConfigSourceFactory configSourceFactory;

    @Override
    public OptionalInt getPriority() {
        return configSourceFactory.getPriority();
    }

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext configSourceContext) {
        return configSourceFactory.getConfigSources(configSourceContext);
    }
}
