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

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Socket}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-02
 */
@UtilityClass
public class Sockets {
    /**
     * Abstract operation to be applied upon a socket.
     */
    @FunctionalInterface
    public interface SocketOperation {
        /**
         * Applies the operation upon a socket.
         * @param socket Socket.
         * @throws IOException Thrown in case of I/O error.
         */
        void apply(Socket socket) throws IOException;
    }

    /**
     * Connect-specific operation.
     */
    @FunctionalInterface
    public interface ConnectOperation extends SocketOperation {
        /**
         * Connects a socket.
         * @param socket Socket.
         * @throws IOException Thrown in case of I/O error.
         */
        void connect(Socket socket) throws IOException;

        @Override
        default void apply(Socket socket) throws IOException {
            connect(socket);
        }
    }

    /**
     * Tests if a socket can be connected.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param operation Connect-operation.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be connected.
     */
    public static void testConnect(ConnectOperation operation) throws IOException {
        try (Socket socket=new Socket()) {
            operation.connect(socket);
        }
    }

    /**
     * Tests if a socket can be connected.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param endpoint Endpoint to connect to.
     * @param connectTimeout Connect timeout.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be connected.
     */
    public static void testConnect(SocketAddress endpoint,
                                   Duration connectTimeout) throws IOException {
        testConnect(socket -> socket.connect(endpoint,(int)connectTimeout.toMillis()));
    }

    /**
     * Tests if a socket can be connected.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param endpoint Endpoint to connect to.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be connected.
     */
    public static void testConnect(SocketAddress endpoint) throws IOException {
        testConnect(socket -> socket.connect(endpoint));
    }



    public static <R> CompletableFuture<R> testConnect(Supplier<R> operation,
                                                       Executor executor) {
        return CompletableFuture.supplyAsync(operation,executor);
    }





/*
    @lombok.Builder(builderClassName = "Builder")
    @AllArgsConstructor
    public static class SocketConnectivity {
        private final SocketAddress socketAddress;
        private final Duration connectTimeout;
        private final Exception exception;
    }

    public static SocketConnectivity canConnectToSocket(ConnectOperation operation) {
        SocketConnectivity.Builder builder=SocketConnectivity.builder();
        builder.socketAddress(socketAddress);
        builder.connectTimeout(connectTimeout);
        try {
            operation.apply();
        } catch (IOException ex) {
            builder.exception(ex);
        }
        return builder.build();
    }




    public static CompletableFuture<SocketConnectivity> testConnect(ConnectOperation operation,
                                                       Executor executor) {
        return CompletableFuture.supplyAsync(operation,executor);
    }
*/


}
