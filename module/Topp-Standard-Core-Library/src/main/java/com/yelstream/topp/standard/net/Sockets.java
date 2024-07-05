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
import lombok.RequiredArgsConstructor;
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
    public static void initiateTestConnect(ConnectOperation operation) throws IOException {
        try (Socket socket=new Socket()) {
            operation.connect(socket);
        }
    }

    /**
     * Parameters to connect a socket.
     */
    @Getter
    @ToString
    @AllArgsConstructor(staticName="of")
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class ConnectParameter {
        /**
         * Endpoint to connect to.
         */
        private final SocketAddress endpoint;

        /**
         * Connect timeout.
         */
        @lombok.Builder.Default
        private final Duration connectTimeout=null;
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
        public static ConnectOperation create(SocketAddress endpoint) {
            return socket -> socket.connect(endpoint);
        }

        /**
         * Creates a socket connect operation.
         * @param parameter Connect parameters.
         */
        public static ConnectOperation create(ConnectParameter parameter) {
            SocketAddress endpoint=parameter.endpoint;
            Duration connectTimeout=parameter.connectTimeout;
            if (connectTimeout==null) {
                return create(endpoint);
            } else {
                return create(endpoint,connectTimeout);
            }
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
            return Sockets::initiateTestConnect;
        }
    }

    /**
     * Initiates a test connect.
     * @param resultSupplier Result supplier.
     * @param executor Executor.
     * @return Result.
     * @param <R> Type of result.
     */
    private static <R> CompletableFuture<R> initiateTestConnect(Supplier<R> resultSupplier,
                                                                Executor executor) {
        return CompletableFuture.supplyAsync(resultSupplier,executor);
    }

    /**
     * Decoration of a test connect.
     * @param <R> Type of result.
     */
    public interface ConnectDecoration<R> {
        /**
         * Performs a test connect.
         * @param probe Connect probe.
         * @param parameter Connect parameters.
         * @return Result.
         */
        R apply(ConnectProbe probe,
                ConnectParameter parameter);
    }

    /**
     * Creates a result supplier for a connect test.
     * @param decoration Connect decoration.
     * @param probe Connect probe.
     * @param parameter Connect parameters.
     * @return Created supplier.
     * @param <R> Type of result.
     */
    private static <R> Supplier<R> createResultSupplier(ConnectDecoration<R> decoration,
                                                        ConnectProbe probe,
                                                        ConnectParameter parameter) {
        return ()->decoration.apply(probe,parameter);
    }

    /**
     * Initiates a test connect.
     * @param decoration Connect decoration.
     * @param probe Connect probe.
     * @param parameter Connect parameters.
     * @param executor Executor.
     * @param <R> Type of result.
     */
    public static <R> CompletableFuture<R> initiateTestConnect(ConnectDecoration<R> decoration,
                                                               ConnectProbe probe,
                                                               ConnectParameter parameter,
                                                               Executor executor) {
        Supplier<R> resultSupplier=createResultSupplier(decoration,probe,parameter);
        return initiateTestConnect(resultSupplier,executor);
    }

    /**
     * Initiates a test connect.
     * @param decoration Connect decoration.
     * @param parameter Connect parameters.
     * @param executor Executor.
     * @param <R> Type of result.
     */
    public static <R> CompletableFuture<R> initiateTestConnect(ConnectDecoration<R> decoration,
                                                               ConnectParameter parameter,
                                                               Executor executor) {
        ConnectProbe probe=ConnectProbes.create();
        return initiateTestConnect(decoration,probe,parameter,executor);
    }

    /**
     * Decorates a connect probe to return an indication of success.
     * @param probe Connect probe.
     * @param parameter Connect parameter.
     * @return Result.
     *         Indicates, if socket was connected.
     */
    private static Boolean decorateWithBoolean(ConnectProbe probe,
                                               ConnectParameter parameter) {
        ConnectOperation operation=ConnectOperations.create(parameter);
        AtomicReference<Boolean> connected=new AtomicReference<>(null);
        try {
            probe.test(socket->{
                operation.connect(socket);
                connected.set(Boolean.TRUE);
            });
        } catch (IOException ex) {
            log.atTrace().setMessage("Failure to connect socket; parameters are {}!").addArgument(parameter).setCause(ex).log();
            connected.set(Boolean.FALSE);
        }
        return connected.get();
    }

    /**
     * Result of executing a test connect with simple, plain information.
     */
    @Getter
    @ToString
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class SimpleConnectResult {
        /**
         * Connect parameters.
         */
        private final ConnectParameter connectParameter;

        /**
         * Indicates, if the test connect has succeeded.
         */
        private final Boolean connected;

        /**
         * Indicates, if the test connect has failed.
         * @return Indicates, if connect has failed.
         */
        public boolean failure() {
            return !success();
        }

        /**
         * Indicates, if the test connect has succeeded.
         * @return Indicates, if connect has succeeded.
         */
        public boolean success() {
            return connected==Boolean.TRUE;
        }
    }

    /**
     * Decorates a connect probe to return an indication of success.
     * @param probe Connect probe.
     * @param parameter Connect parameter.
     * @return Result.
     */
    private static SimpleConnectResult decorateWithSimpleConnectResult(ConnectProbe probe,
                                                                       ConnectParameter parameter) {
        ConnectOperation operation=ConnectOperations.create(parameter);
        SimpleConnectResult.Builder builder=SimpleConnectResult.builder().connectParameter(parameter);
        try {
            probe.test(socket->{
                operation.connect(socket);
                builder.connected(Boolean.TRUE);
            });
        } catch (IOException ex) {
            log.atTrace().setMessage("Failure to connect socket; parameters are {}!").addArgument(parameter).setCause(ex).log();
            builder.connected(Boolean.FALSE);
        }
        return builder.build();
    }

    /**
     * Result of executing a test connect with somewhat detailed information.
     */
    @Getter
    @ToString
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class DetailedConnectResult {
        /**
         * Connect parameters.
         */
        private final ConnectParameter connectParameter;

        /**
         * Remote socket address connected to.
         */
        private final SocketAddress remoteAddress;

        /**
         * Local socket address connected to.
         */
        private final SocketAddress localAddress;

        /**
         * Indicates, that an exception has occurred.
         */
        private final Exception exception;

        /**
         * Indicates, if the test connect has failed.
         * @return Indicates, if connect has failed.
         */
        public boolean failure() {
            return !success();
        }

        /**
         * Indicates, if the test connect has succeeded.
         * @return Indicates, if connect has succeeded.
         */
        public boolean success() {
            return exception==null;
        }
    }

    /**
     * Decorates a connect probe to return an indication of success.
     * @param probe Connect probe.
     * @param parameter Connect parameter.
     * @return Result.
     */
    private static DetailedConnectResult decorateWithDetailedConnectResult(ConnectProbe probe,
                                                                           ConnectParameter parameter) {
        ConnectOperation operation=ConnectOperations.create(parameter);
        DetailedConnectResult.Builder builder= DetailedConnectResult.builder().connectParameter(parameter);
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

    /**
     * Utilities addressing instances of {@link ConnectDecoration}.
     */
    @UtilityClass
    public static class ConnectDecorations {
        /**
         * Creates a decoration for a test connect with a basic result.
         * @return Created decoration.
         */
        public static ConnectDecoration<Boolean> createWithBoolean() {
            return Sockets::decorateWithBoolean;
        }

        /**
         * Creates a decoration for a test connect with a simple result.
         * @return Created decoration.
         */
        public static ConnectDecoration<SimpleConnectResult> createWithSimpleConnectResult() {
            return Sockets::decorateWithSimpleConnectResult;
        }

        /**
         * Creates a decoration for a test connect with a detailed result.
         * @return Created decoration.
         */
        public static ConnectDecoration<DetailedConnectResult> createWithDetailedConnectResult() {
            return Sockets::decorateWithDetailedConnectResult;
        }
    }

    /**
     * Utility addressing the initiation of test connects.
     */
    @UtilityClass
    public static class TestConnects {
        /**
         * Initiates a test connect with a basic result.
         * @param parameter Connect parameter.
         * @param executor Executor.
         * @return Result.
         */
        public static CompletableFuture<Boolean> withBoolean(ConnectParameter parameter,
                                                             Executor executor) {
            return initiateTestConnect(ConnectDecorations.createWithBoolean(),parameter,executor);
        }

        /**
         * Initiates a test connect with a simple result.
         * @param parameter Connect parameter.
         * @param executor Executor.
         * @return Result.
         */
        public static CompletableFuture<SimpleConnectResult> withSocketConnectivity(ConnectParameter parameter,
                                                                                    Executor executor) {
            return initiateTestConnect(ConnectDecorations.createWithSimpleConnectResult(),parameter,executor);
        }

        /**
         * Initiates a test connect with a detailed result.
         * @param parameter Connect parameter.
         * @param executor Executor.
         * @return Result.
         */
        public static CompletableFuture<DetailedConnectResult> withSocketConnectivity2(ConnectParameter parameter,
                                                                                       Executor executor) {
            return initiateTestConnect(ConnectDecorations.createWithDetailedConnectResult(),parameter,executor);
        }
    }
}
