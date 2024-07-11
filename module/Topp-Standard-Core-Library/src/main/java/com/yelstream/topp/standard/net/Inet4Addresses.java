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

import com.yelstream.topp.standard.util.function.SupplierWithException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Inet4Addresses {
    @Getter
    @SuppressWarnings("java:S115")
    @AllArgsConstructor
    @ToString
    enum StandardAddress {
        Zero(()->Inet4Address.getByName("0.0.0.0")),
        One(()->Inet4Address.getByName("127.0.0.1")),
        LoopBackAddress(Inet4Address::getLoopbackAddress),
        LocalHost(Inet4Address::getLocalHost),
        LocalHostByName(()->Inet4Address.getByName("localhost"));

        private final SupplierWithException<InetAddress,IOException> addressSupplier;

        public InetAddress getAddress() throws IOException {
            return addressSupplier.get();
        }

        public static List<StandardAddress> valuesAsList() {
            return valuesAsList(values());
        }

        public static List<StandardAddress> valuesAsList(StandardAddress... addresses) {
            return Arrays.stream(addresses).toList();
        }

        public static List<SupplierWithException<InetAddress,IOException>> valuesAsSupplierList(StandardAddress... addresses) {
            return Arrays.stream(addresses).map(StandardAddress::getAddressSupplier).toList();
        }

        public static List<SupplierWithException<InetAddress,IOException>> valuesAsSupplierList(List<StandardAddress> addresses) {
            return addresses.stream().map(StandardAddress::getAddressSupplier).toList();
        }
    }
}
