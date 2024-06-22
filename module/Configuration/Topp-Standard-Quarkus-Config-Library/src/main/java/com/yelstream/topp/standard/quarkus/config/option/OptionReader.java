/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.quarkus.config.option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.slf4j.event.Level;

import java.util.Optional;

@Slf4j
@Getter
@AllArgsConstructor
public class OptionReader {
    private final Enum<?> enumeration;

    private final Config config;

    private final String propertyName;

    private final Class<?> propertyType;

    public String getValue() {
        return config.getValue(propertyName,String.class);
    }

    public Optional<String> getOptionalValue() {
        return config.getOptionalValue(propertyName,String.class);
    }

    public <T> T getValue(Class<T> propertyType) {
        return config.getValue(propertyName,propertyType);
    }

    public <T> Optional<T> getOptionalValue(Class<T> propertyType) {
        return config.getOptionalValue(propertyName,propertyType);
    }

    public void log(org.slf4j.Logger log,
                    org.slf4j.event.Level level) {
        log.atLevel(level).setMessage("Configuration option '{}' = {}").addArgument(()->propertyName).addArgument(()->getOptionalValue().orElse(null)).log();
    }

    public void log(org.slf4j.Logger log) {
        log(log,Level.DEBUG);
    }
}
