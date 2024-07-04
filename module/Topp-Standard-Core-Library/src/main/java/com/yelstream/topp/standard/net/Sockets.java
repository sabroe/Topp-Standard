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
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Socket}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-02
 */
@Slf4j
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
         */
        public static ConnectOperation testConnect(SocketAddress endpoint) {
            return socket -> socket.connect(endpoint);
        }
    }

    /**
     * Utility handling instances of {@link ConnectProbe}.
     */
    @UtilityClass
    public static class ConnectProbes {
        /**
         * Creates a default connect-probe.
         */
        public static ConnectProbe create() {
            return Sockets::testConnect;
        }
    }










    private static <R> CompletableFuture<R> testConnect(Supplier<R> resultSupplier,
                                                        Executor executor) {
        return CompletableFuture.supplyAsync(resultSupplier,executor);
    }

    private static <R> Supplier<R> resultSupplier(ConnectDecoration<R> decoration,
                                                  ConnectProbe probe,
                                                  ConnectOperation operation) {
        return ()->decoration.apply(probe,operation);
    }

    public static <R> CompletableFuture<R> testConnect(ConnectDecoration<R> decoration,
                                                       ConnectProbe probe,
                                                       ConnectOperation operation,
                                                       Executor executor) {
        Supplier<R> resultSupplier=resultSupplier(decoration,probe,operation);
        return testConnect(resultSupplier,executor);
    }


    public static <R> CompletableFuture<R> testConnect(ConnectDecoration<R> decoration,
                                                       ConnectOperation operation,
                                                       Executor executor) {
        ConnectProbe probe=ConnectProbes.create();
        return testConnect(decoration,probe,operation,executor);
    }



    /**
     *
     */
    @AllArgsConstructor
    public static class ConnectParameter {
        private final SocketAddress endpoint;

        private final Duration connectTimeout;
    }




    private static Boolean decorateWithBoolean(ConnectProbe probe,
                                               ConnectOperation operation) {
        AtomicReference<Boolean> connected=new AtomicReference<>(null);
        try {
            probe.test(socket->{
                operation.connect(socket);
                connected.set(Boolean.TRUE);
            });
        } catch (IOException ex) {
            //TO-DO: Log!
            connected.set(Boolean.FALSE);
        }
        return connected.get();
    }




    @Getter
    @ToString
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class SocketConnectivity {

        private final SocketAddress endpoint;

        private final SocketAddress remoteAddress;
        private final SocketAddress localAddress;
        private final Exception exception;

        public boolean failure() {
            return !success();
        }

        public boolean success() {
            return exception==null;
        }
    }

    private static SocketConnectivity decorateWithSocketConnectivity(ConnectProbe probe,
                                                                     ConnectOperation operation) {
        SocketConnectivity.Builder builder=SocketConnectivity.builder();
        try {
            probe.test(socket->{
                operation.connect(socket);
                builder.remoteAddress(socket.getRemoteSocketAddress());
                builder.localAddress(socket.getLocalSocketAddress());
            });
        } catch (IOException ex) {
            builder.exception(ex);
        }
        return builder.build();
    }





    @Getter
    @ToString
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class SocketConnectivity2 {
        private final SocketAddress remoteAddress;
        private final SocketAddress localAddress;
        private final Exception exception;

        public boolean failure() {
            return !success();
        }

        public boolean success() {
            return exception==null;
        }
    }

    private static SocketConnectivity2 decorateWithSocketConnectivity2(ConnectProbe probe,
                                                                       ConnectOperation operation) {
        SocketConnectivity2.Builder builder=SocketConnectivity2.builder();
        try {
            probe.test(socket->{
                operation.connect(socket);
                builder.remoteAddress(socket.getRemoteSocketAddress());
                builder.localAddress(socket.getLocalSocketAddress());
            });
        } catch (IOException ex) {
            builder.exception(ex);
        }
        return builder.build();
    }





    public interface ConnectDecoration<R> {
        R apply(ConnectProbe probe,
                ConnectOperation operation);
    }

    /**
     *
     */
    @UtilityClass
    public static class ConnectDecorations {
        /**
         *
         * @return
         */
        public static ConnectDecoration<Boolean> createWithBoolean() {
            return Sockets::decorateWithBoolean;
        }

        /**
         *
         * @return
         */
        public static ConnectDecoration<SocketConnectivity> createWithSocketConnectivity() {
            return Sockets::decorateWithSocketConnectivity;
        }

        /**
         *
         * @return
         */
        public static ConnectDecoration<SocketConnectivity2> createWithSocketConnectivity2() {
            return Sockets::decorateWithSocketConnectivity2;
        }
    }



    public static CompletableFuture<Boolean> testConnectWithBoolean(ConnectOperation operation,
                                                                    Executor executor) {
        return testConnect(ConnectDecorations.createWithBoolean(),operation,executor);
    }

    public static CompletableFuture<SocketConnectivity> testConnectWithSocketConnectivity(ConnectOperation operation,
                                                                                          Executor executor) {
        return testConnect(ConnectDecorations.createWithSocketConnectivity(),operation,executor);
    }

    public static CompletableFuture<SocketConnectivity2> testConnectWithSocketConnectivity2(ConnectOperation operation,
                                                                                            Executor executor) {
        return testConnect(ConnectDecorations.createWithSocketConnectivity2(),operation,executor);
    }
}
