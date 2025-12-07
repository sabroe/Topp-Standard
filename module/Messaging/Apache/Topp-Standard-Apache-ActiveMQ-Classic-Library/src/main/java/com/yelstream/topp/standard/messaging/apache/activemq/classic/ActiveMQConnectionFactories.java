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

package com.yelstream.topp.standard.messaging.apache.activemq.classic;

import lombok.experimental.UtilityClass;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.net.URI;
import java.util.List;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-03
 */
@UtilityClass
public class ActiveMQConnectionFactories {

    @lombok.Builder(builderClassName="Builder")
    @SuppressWarnings("unused")
    private static ActiveMQConnectionFactory createActiveMQConnectionFactoryByBuilder(URI brokerURL,
                                                                                      String username,
                                                                                      String password,
                                                                                      List<String> trustedPackages) {
        ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
        if (brokerURL!=null) {
            connectionFactory.setBrokerURL(brokerURL.toString());
        }
        if (username!=null) {
            connectionFactory.setUserName(username);
        }
        if (password!=null) {
            connectionFactory.setPassword(password);
        }
        if (trustedPackages!=null) {
            connectionFactory.setTrustedPackages(trustedPackages);
            connectionFactory.setTrustAllPackages(false);
        }
        return connectionFactory;
    }

    public static ActiveMQConnectionFactory of(URI brokerURL) {
        return builder().brokerURL(brokerURL).build();
    }

    public static ActiveMQConnectionFactory of(String brokerURL) {
        return of(URI.create(brokerURL));
    }
}
