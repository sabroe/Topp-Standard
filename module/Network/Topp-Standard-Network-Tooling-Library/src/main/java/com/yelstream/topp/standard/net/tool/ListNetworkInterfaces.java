package com.yelstream.topp.standard.net.tool;

import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

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
