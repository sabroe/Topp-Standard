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

package com.yelstream.topp.standard.quarkus.config.option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

@Slf4j
@AllArgsConstructor
public enum EclipseVertXHTTPConfigurationOption {

    //https://quarkus.io/guides/all-config#quarkus-vertx-http_quarkus-vertx-http-eclipse-vert-x-http

    QuarkusHttpHost("quarkus.http.host",String.class),
    QuarkusHttpPort("quarkus.http.port",String.class),
    QuarkusHttpSslPort("quarkus.http.ssl-port",String.class),
    QuarkusHttpTestHost("quarkus.http.test-host",String.class),
    QuarkusHttpTestPort("quarkus.http.test-port",String.class),
    QuarkusHttpTestSslPort("quarkus.http.test-ssl-port",String.class);

    @Getter
    private final String propertyName;

    @Getter
    private final Class<?> propertyType;

    public OptionReader reader() {
        Config config=ConfigProvider.getConfig();
        return new OptionReader(this,config,propertyName,propertyType);
    }

    public static void log() {
        for (var option: values()) {
            OptionReader reader=option.reader();
            reader.log(log);
        }
    }
}
