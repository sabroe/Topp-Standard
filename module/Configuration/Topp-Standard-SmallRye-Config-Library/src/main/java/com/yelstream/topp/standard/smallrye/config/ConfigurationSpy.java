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

package com.yelstream.topp.standard.smallrye.config;

import io.smallrye.config.SmallRyeConfig;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;

public class ConfigurationSpy {


    public static void bruteForcePrint() {

        Config config = ConfigProvider.getConfig();
        SmallRyeConfig smallRyeConfig = config.unwrap(SmallRyeConfig.class);

        System.out.println("Profiles: "+smallRyeConfig.getProfiles());

        int index=0;
        for (ConfigSource configSource: smallRyeConfig.getConfigSources()) {
            System.out.format("[%s] ConfigSource: %n",index);
            System.out.println("    Name:           "+configSource.getName());
            System.out.println("    Class:          "+configSource.getClass());
            System.out.println("    Ordinal:        "+configSource.getOrdinal());
            System.out.println("    Property names: "+configSource.getPropertyNames());
            System.out.println("    Property:       "+configSource.getProperties());
            index++;
        }
    }

}
