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

package com.yelstream.topp.standard.microprofile.config.provider;

import lombok.experimental.UtilityClass;
import org.eclipse.microprofile.config.spi.ConfigSource;

@UtilityClass
public class ConfigProviders {

    public static void addConfigSource(ConfigSource configSource) {
//        ConfigProvider.getConfig().
/*
        ConfigProvider configProvider=null;
        Config config = ConfigProvider.getConfig();


        config.getSources().add(configSource);
*/
    }

    public static void xxx() {

/*
        Map<String, String> defaults = new HashMap<>();
        defaults.put("my.property", "default-value");
*/

/*
        ConfigProvider.getConfig()
                .getBuilder()
                .withSources(new CustomConfigSource(defaults))
                .build();
*/

    }

/*
    public static class MyConfigSourceProvider implements ConfigSourceProvider {
        @Override
        public Iterable<ConfigSource> getConfigSources(ClassLoader forClassLoader) {
            Map<String, String> defaults = new HashMap<>();
            defaults.put("my.property", "default-value");
            return Collections.singletonList(new CustomConfigSource(defaults));
        }
    }
*/

/*
    public static void yyy() {
        ConfigProviderResolver.instance()
                .registerConfig(

                        ConfigProvider.getConfig(null*/
/*forClassLoader*//*
)
            .withSources(new MyConfigSourceProvider()

            )


                    .build();
    }
*/



}
