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
     * Probe for connectivity of a socket.
     */
    @FunctionalInterface
    public interface ConnectProbe {
        /**
         * Tests connectivity of a socket.
         * @param operation Connect-operation.
         * @throws IOException Thrown in case of I/O error.
         */
        void test(ConnectOperation operation) throws IOException;
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
     * Utility handling instances of {@link ConnectOperation}.
     */
    @UtilityClass
    public static class ConnectOperations {
        /**
         * Creates a socket connect operation.
         * @param endpoint Endpoint to connect to.
         * @param connectTimeout Connect timeout.
         */
        public static ConnectOperation create(SocketAddress endpoint,
                                              Duration connectTimeout) {
            return socket -> socket.connect(endpoint,(int)connectTimeout.toMillis());
        }

        /**
         * Creates a socket connect operation.
         * @param endpoint Endpoint to connect to.
         * @param connectTimeout Connect timeout.
         */
        public static ConnectOperation testConnect(SocketAddress endpoint,
                                                   Duration connectTimeout) {
            return socket -> socket.connect(endpoint,(int)connectTimeout.toMillis());
        }

        /**
         * Creates a socket connect operation.
         * @param endpoint Endpoint to connect to.
         */
        public static ConnectOperation testConnect(SocketAddress endpoint) {
            return socket -> socket.connect(endpoint);
        }
    }


    public static <R> CompletableFuture<R> testConnect(Supplier<R> resultSupplier,
                                                       Executor executor) {
        return CompletableFuture.supplyAsync(resultSupplier,executor);
    }

    public static <R> CompletableFuture<R> testConnect(Function<ConnectOperation,R> decoration,
                                                       ConnectOperation operation,
                                                       Executor executor) {
        Supplier<R> resultSupplier=()->decoration.apply(operation);
        return testConnect(resultSupplier,executor);
    }


    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class SocketConnectivity {
        private final SocketAddress remoteAddress;
        private final SocketAddress localAddress;
        private final Exception exception;
    }

    public static SocketConnectivity testConnectMonitored(ConnectOperation operation) {
        SocketConnectivity.Builder builder=SocketConnectivity.builder();
        try {
            testConnect(socket->{
                operation.connect(socket);
                builder.remoteAddress(socket.getRemoteSocketAddress());
                builder.localAddress(socket.getLocalSocketAddress());
            });
        } catch (IOException ex) {
            builder.exception(ex);
        }
        return builder.build();
    }

    public static CompletableFuture<SocketConnectivity> testConnect(ConnectOperation operation,
                                                                    Executor executor) {
        Function<ConnectOperation,SocketConnectivity> decoration=Sockets::testConnectMonitored;
        return testConnect(decoration,operation,executor);
    }

    /*
     * Note about this class -- for YOU to learn new things:
     *     -- #testConnect(ConnectOperation) is reference from one place only.
     *     -- CompletableFuture#supplyAsync(Supplier,Executor) is reference from one place only.
     *     -- #testConnectMonitored(ConnectOperation) is reference from one place only.
     */
}
