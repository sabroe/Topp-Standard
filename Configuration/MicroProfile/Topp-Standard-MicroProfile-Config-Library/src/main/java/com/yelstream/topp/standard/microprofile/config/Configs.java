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

package com.yelstream.topp.standard.microprofile.config;

import com.yelstream.topp.standard.microprofile.config.source.describe.ConfigSourceDescriptor;
import com.yelstream.topp.standard.microprofile.config.source.describe.ConfigSourceDescriptors;
import com.yelstream.topp.standard.util.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@UtilityClass
public class Configs {

    public static Config getConfig() {
        return ConfigProvider.getConfig();
    }

    public static Config getConfig(ClassLoader classLoader) {
        return ConfigProvider.getConfig(classLoader);
    }

    public static List<ConfigSource> getConfigSources(Config config) {
        Iterable<ConfigSource> iterable=config.getConfigSources();
        return Lists.of(iterable);
    }

    public static void logDescription(org.slf4j.Logger log,
                                      org.slf4j.event.Level level,
                                      Config config) {
        logDescription(log,level,()->getConfigSources(config));
    }

    public static void logDescription(org.slf4j.Logger log,
                                      org.slf4j.event.Level level,
                                      Supplier<List<ConfigSource>> configSourcesSupplier) {
        log.atLevel(level).setMessage("Configuration sources.").addArgument(()->{
            List<ConfigSource> configSources=configSourcesSupplier.get();
            List<ConfigSourceDescriptor> configSourceDescriptors=ConfigSourceDescriptors.from(configSources);
            return ConfigSourceDescriptors.createDescription(configSourceDescriptors);
            }).log();
    }
}
