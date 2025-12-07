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

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * Universal certificate trust manager.
 * <p>
 *     Note: This is intended for testing purposes only!
 *     It imposes no validation constraints, accepting all certificates as trusted.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-04
 */
public class PermissiveX509TrustManager implements X509TrustManager {
    @Override
    @SuppressWarnings("java:S4830")
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        //Imposes no validation constraints, accepting all certificates as trusted.
    }

    @Override
    @SuppressWarnings("java:S4830")
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        //Imposes no validation constraints, accepting all certificates as trusted.
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
