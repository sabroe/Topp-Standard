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

package com.yelstream.topp.standard.net;

import lombok.experimental.UtilityClass;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Utility addressing instances of {@link InetSocketAddress}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-05
 */
@UtilityClass
public class InetSocketAddresses {
    /**
     * Get the IP address of an IP socket address.
     * @param inetSocketAddress IP socket address.
     *                          This may be {@code null}.
     * @return IP address.
     *         This may be {@code null}.
     */
    public static InetAddress getInetAddress(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress==null?null:inetSocketAddress.getAddress();
    }

    /**
     * Get the host name of an IP socket address.
     * @param inetSocketAddress IP socket address.
     *                          This may be {@code null}.
     * @return Host name.
     *         This may be {@code null}.
     */
    public static String getHostName(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress==null?null:inetSocketAddress.getHostName();
    }

    /**
     * Get the host string of an IP socket address.
     * @param inetSocketAddress IP socket address.
     *                          This may be {@code null}.
     * @return Host string.
     *         This may be {@code null}.
     */
    public static String getHostString(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress==null?null:inetSocketAddress.getHostString();
    }

    /**
     * Get the port of an IP socket address.
     * @param inetSocketAddress IP socket address.
     *                          This may be {@code null}.
     * @return Port.
     *         This may be {@code null}.
     */
    public static int getPort(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress==null?-1:inetSocketAddress.getPort();
    }
}
