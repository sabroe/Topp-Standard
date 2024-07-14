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

import com.yelstream.topp.standard.io.PrintBuffer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
     * Basic test of {@link SocketScanner#scan()}.
     * @throws IOException Thrown in case of I/O error.
     */
    @Test
    void scan() throws IOException {
        SocketScanner.Builder builder=SocketScanner.builder();
        builder.ports(()->IntStream.range(0,65535));
        builder.addressSuppliers(InetAddresses.distinct(Inet4Addresses.StandardAddress.getAllSuppliers()));
        SocketScanner scanner=builder.build();

        SocketScanner.Result result=scanner.scan();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getScans());
        Assertions.assertFalse(result.getScans().isEmpty());

        String resultText=PrintBuffer.print(result::print);
        System.out.print(resultText);  //TO-DO: Once done bragging, please use a proper logger!
    }
}
