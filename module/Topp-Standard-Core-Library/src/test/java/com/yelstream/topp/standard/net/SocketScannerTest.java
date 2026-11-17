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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.stream.IntStream;

/**
 * Test of {@link SocketScanner}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-14
 */
@Slf4j
class SocketScannerTest {

    /**
     * Basic test of {@link SocketScanner#scan()}, scanning all ports of all standard addresses.
     * <p>
     *     This uses {@link SocketScanner.Probe#BIND} to be quick and to not exhaust the ephemeral ports.
     * </p>
     * @throws IOException Thrown in case of I/O error.
     */
    @Test
    void scan() throws IOException {
        SocketScanner.Builder builder=SocketScanner.builder();
        builder.ports(()->IntStream.range(0,65535));
        builder.addressSuppliers(InetAddresses.distinct(Inet4Addresses.StandardAddress.getAllSuppliers()));
        builder.probe(SocketScanner.Probe.BIND);
        SocketScanner scanner=builder.build();

        SocketScanner.Result result=scanner.scan();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getScans());
        Assertions.assertFalse(result.getScans().isEmpty());

        String resultText=result.capture();
        System.out.print(resultText);  //TO-DO: Once done bragging, please use a proper logger!
    }

    /**
     * Tests that {@link SocketScanner#scan()} reports a port known to be occupied.
     * @param probe Probe method.
     * @throws IOException Thrown in case of I/O error.
     */
    @ParameterizedTest
    @EnumSource(SocketScanner.Probe.class)
    void scanFindsOccupiedPort(SocketScanner.Probe probe) throws IOException {
        InetAddress address=InetAddress.getLoopbackAddress();
        try (ServerSocket serverSocket=new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(address,0));
            int port=serverSocket.getLocalPort();

            SocketScanner.Builder builder=SocketScanner.builder();
            builder.ports(()->IntStream.of(port));
            builder.addressSupplier(()->address);
            builder.probe(probe);
            SocketScanner scanner=builder.build();

            SocketScanner.Result result=scanner.scan();

            Assertions.assertEquals(1,result.getScans().size());
            SocketScanner.Result.Scan scan=result.getScans().getFirst();
            Assertions.assertEquals(1,scan.getPortStatuses().size());
            Assertions.assertEquals(port,scan.getPortStatuses().getFirst().getPort());
        }
    }
}
