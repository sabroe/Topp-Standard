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

package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.Set;

/**
 * Static proxy for {@link ConfigSource} instances.
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
public class ProxyConfigSource implements ConfigSource {
    /**
     * Wrapped configuration-source.
     * Not open for external access.
     */
    @NonNull
    private final ConfigSource configSource;

    @Override
    public String getName() {
        return configSource.getName();
    }

    @Override
    public int getOrdinal() {
        return configSource.getOrdinal();
    }

    @Override
    public Set<String> getPropertyNames() {
        return configSource.getPropertyNames();
    }

    @Override
    public String getValue(String propertyName) {
        return configSource.getValue(propertyName);
    }

    @Override
    public Map<String,String> getProperties() {
        return configSource.getProperties();
    }
}
