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

package com.yelstream.topp.standard.net.tool;

import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-07
 */
public class ListNetworkInterfaces {
    public static void main(String[] args) {
        try {
            // Get all network interfaces
            Stream<NetworkInterface> interfaces =
//                NetworkInterface.networkInterfaces().filter(networkInterface -> { try {return networkInterface.isLoopback(); } catch (SocketException ex) { return false; } });
              NetworkInterface.networkInterfaces();

            // Iterate through the network interfaces
            interfaces.forEach(networkInterface -> {
                try {
                    System.out.println("Interface Name: " + networkInterface.getName());
                    System.out.println("Display Name:   " + networkInterface.getDisplayName());
                    System.out.println("Virtual:        " + networkInterface.isVirtual());
                    System.out.println("Loopback:       " + networkInterface.isLoopback());
                    System.out.println("Up:             " + networkInterface.isUp());


                    List<InterfaceAddress> addresses=networkInterface.getInterfaceAddresses();
                    System.out.println("Addresses: " + addresses.stream().map(InterfaceAddress::getAddress).toList());

/*
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        System.out.println("Address: " + address);
                    }
*/

                    System.out.println();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            });
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
