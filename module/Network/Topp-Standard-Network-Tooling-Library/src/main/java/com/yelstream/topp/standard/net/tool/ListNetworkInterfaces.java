package com.yelstream.topp.standard.net.tool;

import java.net.*;
import java.util.Enumeration;

public class ListNetworkInterfaces {
    public static void main(String[] args) {
        try {
            // Get all network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            // Iterate through the network interfaces
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // Display interface details
                System.out.println("Interface Name: " + networkInterface.getName());
                System.out.println("Display Name:   " + networkInterface.getDisplayName());
                System.out.println("Virtual:        " + networkInterface.isVirtual());
                System.out.println("Loopback:       " + networkInterface.isLoopback());
                System.out.println("Up:             " + networkInterface.isUp());

                // Get and display the interface's addresses
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    System.out.println("Address: " + address);
                }

                System.out.println();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
