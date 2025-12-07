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

import com.yelstream.topp.standard.io.IOExceptions;
import com.yelstream.topp.standard.util.function.ex.SupplierWithException;
import com.yelstream.topp.standard.util.function.ex.SupplierWithExceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

/**
 * Utility addressing instances of {@link InetAddresses}.
 * Provides methods to resolve, distinct, and transform lists of {@link SupplierWithException}.
 * <p>
 *     Note that this will adapt to common Java network setting and preferences e.g.
 *     the system property {@code java.net.preferIPv6Addresses} being set to {@code true}/{@code false}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-11
 */
@UtilityClass
public class InetAddresses {
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
        LoopBack(InetAddress::getLoopbackAddress),

        /**
         * The localhost address.
         */
        LocalHost(InetAddress::getLocalHost),

        /**
         * The "unspecified" address by name.
         */
        Unspecified(getUnspecifiedAddress());

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

    /**
     * Gets the unspecified address.
     * @return Unspecified address.
     */
    private static SupplierWithException<InetAddress,IOException> getUnspecifiedAddress() {
        InetAddress address=InetAddress.getLoopbackAddress();  //Address used to suggest the type IPv4 vs. IPv6!
        return switch (address) {
            case Inet4Address ignore -> Inet4Addresses.StandardAddress.Unspecified.getAddressSupplier();
            case Inet6Address ignore -> Inet6Addresses.StandardAddress.Unspecified.getAddressSupplier();
            default -> throw new IllegalStateException(String.format("Failure to get the unspecified address; source address used as type is %s!",address));
        };
    }

    /**
     * Resolves a list of suppliers to a list of InetAddress instances, handling IOExceptions.
     * @param elements The list of suppliers.
     * @return A list of resolved InetAddress instances.
     * @throws IOException If an exception occurs during resolution.
     */
    public static List<InetAddress> resolve(List<SupplierWithException<InetAddress, IOException>> elements) throws IOException {
        return SupplierWithExceptions.resolve(elements,IOExceptions::create);
    }

    /**
     * Resolves a list of suppliers to a distinct list of InetAddress instances, handling IOExceptions.
     * @param elements The list of suppliers.
     * @return A distinct list of resolved InetAddress instances.
     * @throws IOException If an exception occurs during resolution.
     */
    public static List<InetAddress> resolveDistinct(List<SupplierWithException<InetAddress,IOException>> elements) throws IOException {
        return SupplierWithExceptions.resolveDistinct(elements,IOExceptions::create);
    }

    /**
     * Converts a list of InetAddress instances to a list of suppliers that return these addresses.
     * @param addresses The list of InetAddress instances.
     * @return A list of suppliers that return the InetAddress instances.
     */
    public static List<SupplierWithException<InetAddress,IOException>> fromList(List<InetAddress> addresses) {
        return SupplierWithExceptions.fromList(addresses);
    }

    /**
     * Returns a distinct list of suppliers, handling IOExceptions.
     * @param elements The list of suppliers.
     * @return A distinct list of suppliers.
     * @throws IOException If an exception occurs during resolution.
     */
    public static List<SupplierWithException<InetAddress,IOException>> distinct(List<SupplierWithException<InetAddress,IOException>> elements) throws IOException {
        return SupplierWithExceptions.distinct(elements,IOExceptions::create);
    }
}
