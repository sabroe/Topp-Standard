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

import com.yelstream.topp.standard.security.Providers;
import com.yelstream.topp.standard.util.Arrays;
import lombok.Singular;
import lombok.experimental.UtilityClass;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.List;
import java.util.function.Consumer;

/**
 * Utility addressing {@link SSLContext} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-04
 */
@UtilityClass
public class SSLContexts {
    /**
     * Create a new SSL/TLS context.
     * @param protocol Standard name of the requested protocol.
     * @param provider Provider instance
     * @param keyManagers Sources of authentication keys.
     *                    This may be {@code null}.
     * @param trustManagers Sources of peer authentication trust decisions.
     *                      This may be {@code null}.
     * @param generator Source of randomness.
     *                  This may be {@code null}.
     * @return Created context.
     * @throws NoSuchAlgorithmException Thrown if the provider cannot present a context-SPI implementation for the protocol.
     * @throws KeyManagementException Thrown in case initialization fails.
     */
    @lombok.Builder(builderClassName="Builder")
    private static SSLContext createSSLContext(String protocol,
                                               Provider provider,
                                               @Singular List<KeyManager> keyManagers,
                                               @Singular List<TrustManager> trustManagers,
                                               SecureRandom generator) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context=null;
        if (protocol==null) {
            protocol=SSLContext.getDefault().getProtocol();
        }
        if (provider==null) {
            context=SSLContext.getInstance(protocol);
        } else {
            context=SSLContext.getInstance(protocol,provider);
        }
        context.init(Arrays.of(keyManagers),
                     Arrays.of(trustManagers),
                     generator);
        return context;
    }

    public static class Builder {
        public Builder namedProvider(String providerName) {
            Provider provider=Security.getProvider(providerName);
            return provider(provider);
        }

        public Builder buildProvider(Consumer<Providers.Builder> providerBuilderConsumer) {
            Providers.Builder providerBuilder=Providers.builder();
            providerBuilderConsumer.accept(providerBuilder);
            Provider provider=providerBuilder.build();
            return provider(provider);
        }
    }
}
