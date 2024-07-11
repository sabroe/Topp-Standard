/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

import com.yelstream.topp.standard.io.IOExceptions;
import com.yelstream.topp.standard.util.function.SupplierWithException;
import com.yelstream.topp.standard.util.function.SupplierWithExceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@UtilityClass
public class Inet4Addresses {

    @Getter
    @SuppressWarnings("java:S115")
    @AllArgsConstructor
    enum StandardAddress {
        LocalhostByName(()->Inet4Address.getByName("localhost")),
        LocalHost(Inet4Address::getLocalHost),
        LoopbackAddress(Inet4Address::getLoopbackAddress),
        Zero(()->Inet4Address.getByName("0.0.0.0")),
        One(()->Inet4Address.getByName("127.0.0.1"));

        private final SupplierWithException<InetAddress,IOException> addressSupplier;

        public InetAddress getAddress() throws IOException {
            return addressSupplier.get();
        }

        public static List<InetAddress> resolve(StandardAddress... addresses) throws IOException {
            return resolve(Arrays.stream(addresses).toList());
        }

        public static List<InetAddress> resolve(List<StandardAddress> addresses) throws IOException {
            List<SupplierWithException<InetAddress,IOException>> elements=addresses.stream().map(StandardAddress::getAddressSupplier).toList();
            return SupplierWithExceptions.resolveAsList(elements,IOExceptions::create);
        }
    }
}
