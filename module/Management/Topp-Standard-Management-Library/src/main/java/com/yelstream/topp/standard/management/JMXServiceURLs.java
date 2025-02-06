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

package com.yelstream.topp.standard.management;

import lombok.experimental.UtilityClass;

import javax.management.remote.JMXServiceURL;
import java.net.MalformedURLException;

/**
 * Utilities for JMX service URLs.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-06
 */
@UtilityClass
public class JMXServiceURLs {
    /**
     * Default service URL for JMX connections.
     */
    public static final String DEFAULT_SERVICE_URL = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";

    @lombok.Builder(builderClassName="Builder")
    public JMXServiceURL createJMXServiceURL(String protocol,
                                             String host,
                                             int port,
                                             String urlPath) throws MalformedURLException {
        return new JMXServiceURL(protocol,host,port,urlPath);
    }

    public static class Builder {
        public Builder serviceURL(JMXServiceURL serviceURL) {
            protocol(serviceURL.getProtocol());
            host(serviceURL.getHost());
            port(serviceURL.getPort());
            urlPath(serviceURL.getURLPath());
            return this;
        }
    }
}
