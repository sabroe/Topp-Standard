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

package com.yelstream.topp.standard.net;

import lombok.experimental.UtilityClass;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Utility addressing instances of {@link SocketAddress}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-05
 */
@UtilityClass
public class SocketAddresses {
    /**
     * Gets the IP socket address of a socket address, if applicable.
     * @param socketAddress Socket address.
     *        This may be {@code null}.
     * @return IP socket address.
     *         This may be {@code null}.
     */
    @SuppressWarnings("java:S1066")
    public static InetSocketAddress getInetSocketAddress(SocketAddress socketAddress) {
        InetSocketAddress inetSocketAddress=null;
        if (socketAddress!=null) {
            if (socketAddress instanceof InetSocketAddress x) {
                inetSocketAddress=x;
            }
        }
        return inetSocketAddress;
    }
}
