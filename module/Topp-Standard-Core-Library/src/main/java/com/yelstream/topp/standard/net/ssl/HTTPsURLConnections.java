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

package com.yelstream.topp.standard.net.ssl;

import lombok.experimental.UtilityClass;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.util.function.Supplier;

/**
 * Utility addressing {@link HttpsURLConnection} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-04
 */
@UtilityClass
public class HTTPsURLConnections {
    @lombok.Builder(builderClassName="Builder")
    private static HttpsURLConnection createHttpsURLConnection(Supplier<HttpsURLConnection> connectionSupplier,  //TO-DO Create the actual 'HttpsURLConnection'!
                                                               HostnameVerifier hostnameVerifier,
                                                               SSLSocketFactory sslSocketFactory) {
        HttpsURLConnection connection=connectionSupplier.get();
        if (hostnameVerifier!=null) {
            connection.setHostnameVerifier(hostnameVerifier);
        }
        if (sslSocketFactory!=null) {
            connection.setSSLSocketFactory(sslSocketFactory);
        }
        return connection;
    }

    public static class Builder {
        public Builder context(SSLContext context) {
            return sslSocketFactory(context.getSocketFactory());
        }
    }
}
