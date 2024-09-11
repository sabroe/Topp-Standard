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

import com.yelstream.topp.standard.util.function.ex.SupplierWithException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

/**
 * Utility addressing instances of {@link Inet6Addresses}.
 * Provides standard IPv6 addresses and related utilities.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-11
 */
@UtilityClass
public class Inet6Addresses {  //TO-DO: Let tests take care of this: System.setProperty("java.net.preferIPv6Addresses", "true");
    /**
     * The "unspecified" IP address.
     */
    public static final String UNSPECIFIED_ADDRESS_NAME="::0";

    /**
     * The loopback address.
     */
    public static final String LOOPBACK_ADDRESS_NAME="::1";

    /**
     * The localhost address name.
     */
    public static final String LOCALHOST_ADDRESS_NAME="localhost";

    /**
     * Standard addresses in the sense that they are special or often referenced.
     */
    @Getter
    @SuppressWarnings("java:S115")
    @AllArgsConstructor
    @ToString
    enum StandardAddress {
        /**
         * The loopback address.
         */
        LoopBack(Inet6Address::getLoopbackAddress),

        /**
         * The localhost address.
         */
        LocalHost(Inet6Address::getLocalHost),

        /**
         * The localhost address by name.
         */
        LocalHostByName(()->Inet6Address.getByName(LOCALHOST_ADDRESS_NAME)),

        /**
         * The loopback address by name.
         */
        LoopBackByName(()->Inet6Address.getByName(LOOPBACK_ADDRESS_NAME)),

        /**
         * The "unspecified" address by name.
         */
        Unspecified(()->Inet6Address.getByName(UNSPECIFIED_ADDRESS_NAME));

        /**
         * Address supplier.
         */
        private final SupplierWithException<InetAddress,IOException> addressSupplier;

        /**
         * Gets the address.
         * @return Address.
         * @throws IOException Thrown in case of I/O error.
         */
        public InetAddress getAddress() throws IOException {
            return addressSupplier.get();
        }

        /**
         * Gets all enumeration values.
         * @return All enumeration values.
         */
        public static List<StandardAddress> getAll() {
            return Arrays.asList(values());
        }

        /**
         * Converts enumeration values to a list.
         * @param addresses Values.
         * @return Created list of values.
         */
        public static List<StandardAddress> toList(StandardAddress... addresses) {
            return Arrays.asList(addresses);
        }

        /**
         * Gets all address suppliers
         * @return Address suppliers.
         */
        public static List<SupplierWithException<InetAddress,IOException>> getAllSuppliers() {
            return toSupplierList(getAll());
        }

        /**
         * Converts values to a list of suppliers of values.
         * @param addresses Values.
         * @return Address suppliers.
         */
        public static List<SupplierWithException<InetAddress,IOException>> toSupplierList(StandardAddress... addresses) {
            return Arrays.stream(addresses).map(StandardAddress::getAddressSupplier).toList();
        }

        /**
         * Converts values to a list of suppliers of values.
         * @param addresses Values.
         * @return Address suppliers.
         */
        public static List<SupplierWithException<InetAddress,IOException>> toSupplierList(List<StandardAddress> addresses) {
            return addresses.stream().map(StandardAddress::getAddressSupplier).toList();
        }
    }
}
