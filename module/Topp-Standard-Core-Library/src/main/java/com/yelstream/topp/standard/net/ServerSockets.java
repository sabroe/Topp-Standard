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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link ServerSocket}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-02
 */
@Slf4j
@UtilityClass
@SuppressWarnings("java:S1192")
public class ServerSockets {
    /**
     * Abstract operation to be applied upon a server-socket.
     */
    @FunctionalInterface
    public interface ServerSocketOperation {
        /**
         * Applies the operation upon a server-socket.
         * @param serverSocket Server-socket.
         * @throws IOException Thrown in case of I/O error.
         */
        void apply(ServerSocket serverSocket) throws IOException;
    }

    /**
     * Bind-specific operation.
     */
    @FunctionalInterface
    public interface BindOperation extends ServerSocketOperation {
        /**
         * Binds a server-socket.
         * @param serverSocket Server-socket.
         * @throws IOException Thrown in case of I/O error.
         */
        void bind(ServerSocket serverSocket) throws IOException;

        @Override
        default void apply(ServerSocket serverSocket) throws IOException {
            bind(serverSocket);
        }
    }

    /**
     * Probe for binding of a server-socket.
     */
    @FunctionalInterface
    public interface BindProbe {
        /**
         * Tests binding of a server-socket.
         * @param operation Bind-operation.
         * @throws IOException Thrown in case of I/O error.
         */
        void test(BindOperation operation) throws IOException;
    }

    /**
     * Tests if a socket can be bound.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param operation Bind-operation.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be bound.
     */
    public static void testBind(BindOperation operation) throws IOException {
        try (ServerSocket serverSocket=new ServerSocket()) {
            operation.bind(serverSocket);
        }
    }

    /**
     * Parameters to bind a server-socket.
     */
    @Getter
    @ToString
    @AllArgsConstructor(staticName="of")
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class BindParameter {
        /**
         * Endpoint to connect to.
         */
        private final SocketAddress endpoint;

        /**
         * Requested maximum length of the queue of incoming connections.
         */
        @lombok.Builder.Default
        private final Integer backlog=null;
    }

    /**
     * Utility handling instances of {@link BindOperation}.
     */
    @UtilityClass
    public static class BindOperations {
        /**
         * Creates a server-socket bind-operation.
         * @param endpoint Endpoint to connect to.
         * @param backlog Requested maximum length of the queue of incoming connections.
         */
        public static BindOperation create(SocketAddress endpoint,
                                           int backlog) {
            return serverSocket -> serverSocket.bind(endpoint,backlog);
        }

        /**
         * Creates a server-socket bind-operation.
         * @param endpoint Endpoint to connect to.
         */
        public static BindOperation create(SocketAddress endpoint) {
            return serverSocket -> serverSocket.bind(endpoint);
        }

        /**
         * Creates a socket connect operation.
         * @param parameter Connect parameters.
         */
        public static BindOperation create(BindParameter parameter) {
            SocketAddress endpoint=parameter.endpoint;
            Integer backlog=parameter.backlog;
            if (backlog==null) {
                return create(endpoint);
            } else {
                return create(endpoint,backlog);
            }
        }
    }

    /**
     * Utility handling instances of {@link BindProbe}.
     */
    @UtilityClass
    public static class BindProbes {
        /**
         * Creates a default bind-probe.
         */
        public static BindProbe create() {
            return ServerSockets::testBind;
        }
    }

    /**
     * Initiates a test connect.
     * @param resultSupplier Result supplier.
     * @param executor Executor.
     * @return Result.
     * @param <R> Type of result.
     */
    private static <R> CompletableFuture<R> initiateTestBind(Supplier<R> resultSupplier,
                                                             Executor executor) {
        return CompletableFuture.supplyAsync(resultSupplier,executor);
    }

    /**
     * Decoration of a test bind.
     * @param <R> Type of result.
     */
    public interface BindDecoration<R> {
        /**
         * Performs a test bind.
         * @param probe Bind probe.
         * @param parameter Bind parameters.
         * @return Result.
         */
        R apply(BindProbe probe,
                BindParameter parameter);
    }

    /**
     * Creates a result supplier for a bind test.
     * @param decoration Bind decoration.
     * @param probe Bind probe.
     * @param parameter Bind parameters.
     * @return Created supplier.
     * @param <R> Type of result.
     */
    private static <R> Supplier<R> createResultSupplier(BindDecoration<R> decoration,
                                                        BindProbe probe,
                                                        BindParameter parameter) {
        return ()->decoration.apply(probe,parameter);
    }

    /**
     * Initiates a test bind.
     * @param decoration Bind decoration.
     * @param probe Bind probe.
     * @param parameter Bind parameters.
     * @param executor Executor.
     * @param <R> Type of result.
     */
    public static <R> CompletableFuture<R> initiateTestBind(BindDecoration<R> decoration,
                                                            BindProbe probe,
                                                            BindParameter parameter,
                                                            Executor executor) {
        Supplier<R> resultSupplier=createResultSupplier(decoration,probe,parameter);
        return initiateTestBind(resultSupplier,executor);
    }

    /**
     * Initiates a test bind.
     * @param decoration Bind decoration.
     * @param parameter Bind parameters.
     * @param executor Executor.
     * @param <R> Type of result.
     */
    public static <R> CompletableFuture<R> initiateTestBind(BindDecoration<R> decoration,
                                                            BindParameter parameter,
                                                            Executor executor) {
        BindProbe probe=BindProbes.create();
        return initiateTestBind(decoration,probe,parameter,executor);
    }

    /**
     * Decorates a bind probe to return an indication of success.
     * @param probe Bind probe.
     * @param parameter Bind parameter.
     * @return Result.
     *         Indicates, if server-socket was bound.
     */
    private static Boolean decorateWithBoolean(BindProbe probe,
                                               BindParameter parameter) {
        BindOperation operation=ServerSockets.BindOperations.create(parameter);
        AtomicReference<Boolean> bound=new AtomicReference<>(null);
        try {
            probe.test(serverSocket->{
                operation.bind(serverSocket);
                bound.set(Boolean.TRUE);
            });
        } catch (IOException ex) {
            log.atTrace().setMessage("Failure to bind server-socket; parameters are {}!").addArgument(parameter).setCause(ex).log();
            bound.set(Boolean.FALSE);
        }
        return bound.get();
    }

    /**
     * Result of executing a test bind with simple, plain information.
     */
    @Getter
    @ToString
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class SimpleBindResult {
        /**
         * Bind parameters.
         */
        private final BindParameter bindParameter;

        /**
         * Indicates, if the test bind has succeeded.
         */
        private final Boolean bound;

        /**
         * Indicates, if the test bind has failed.
         * @return Indicates, if bind has failed.
         */
        public boolean failure() {
            return !success();
        }

        /**
         * Indicates, if the test bind has succeeded.
         * @return Indicates, if bind has succeeded.
         */
        public boolean success() {
            return bound==Boolean.TRUE;
        }
    }

    /**
     * Decorates a bind probe to return an indication of success.
     * @param probe Bind probe.
     * @param parameter Bind parameter.
     * @return Result.
     */
    private static SimpleBindResult decorateWithSimpleBindResult(BindProbe probe,
                                                                 BindParameter parameter) {
        BindOperation operation=BindOperations.create(parameter);
        SimpleBindResult.Builder builder=SimpleBindResult.builder().bindParameter(parameter);
        try {
            probe.test(serverSocket->{
                operation.bind(serverSocket);
                builder.bound(Boolean.TRUE);
            });
        } catch (IOException ex) {
            log.atTrace().setMessage("Failure to bind server-socket; parameters are {}!").addArgument(parameter).setCause(ex).log();
            builder.bound(Boolean.FALSE);
        }
        return builder.build();
    }

    /**
     * Result of executing a test bind with somewhat detailed information.
     */
    @Getter
    @ToString
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class DetailedBindResult {
        /**
         * Bind parameters.
         */
        private final BindParameter bindParameter;

        /**
         * Local socket address bound to.
         */
        private final SocketAddress localAddress;

        /**
         * Indicates, that an exception has occurred.
         */
        private final Exception exception;

        /**
         * Indicates, if the test bind has failed.
         * @return Indicates, if bind has failed.
         */
        public boolean failure() {
            return !success();
        }

        /**
         * Indicates, if the test bind has succeeded.
         * @return Indicates, if bind has succeeded.
         */
        public boolean success() {
            return exception==null;
        }
    }

    /**
     * Decorates a bind probe to return an indication of success.
     * @param probe Bind probe.
     * @param parameter Bind parameter.
     * @return Result.
     */
    private static DetailedBindResult decorateWithDetailedBindResult(BindProbe probe,
                                                                     BindParameter parameter) {
        BindOperation operation=BindOperations.create(parameter);
        DetailedBindResult.Builder builder=DetailedBindResult.builder().bindParameter(parameter);
        try {
            probe.test(serverSocket->{
                operation.bind(serverSocket);
                builder.localAddress(serverSocket.getLocalSocketAddress());
            });
        } catch (IOException ex) {
            log.atTrace().setMessage("Failure to bind server-socket; parameters are {}!").addArgument(parameter).setCause(ex).log();
            builder.exception(ex);
        }
        return builder.build();
    }

    /**
     * Utilities addressing instances of {@link BindDecoration}.
     */
    @UtilityClass
    public static class BindDecorations {
        /**
         * Creates a decoration for a test bind with a basic result.
         * @return Created decoration.
         */
        public static BindDecoration<Boolean> createWithBoolean() {
            return ServerSockets::decorateWithBoolean;
        }

        /**
         * Creates a decoration for a test bind with a simple result.
         * @return Created decoration.
         */
        public static BindDecoration<SimpleBindResult> createWithSimpleBindResult() {
            return ServerSockets::decorateWithSimpleBindResult;
        }

        /**
         * Creates a decoration for a test connect with a detailed result.
         * @return Created decoration.
         */
        public static BindDecoration<DetailedBindResult> createWithDetailedBindResult() {
            return ServerSockets::decorateWithDetailedBindResult;
        }
    }

    /**
     * Utility addressing the initiation of test binds.
     */
    @UtilityClass
    public static class TestBinds {
        /**
         * Initiates a test connect with a basic result.
         * @param parameter Connect parameter.
         * @param executor Executor.
         * @return Result.
         */
        public static CompletableFuture<Boolean> withBoolean(BindParameter parameter,
                                                             Executor executor) {
            return initiateTestBind(BindDecorations.createWithBoolean(),parameter,executor);
        }

        /**
         * Initiates a test bind with a simple result.
         * @param parameter Bind parameter.
         * @param executor Executor.
         * @return Result.
         */
        public static CompletableFuture<SimpleBindResult> withWithSimpleBindResult(BindParameter parameter,
                                                                                      Executor executor) {
            return initiateTestBind(BindDecorations.createWithSimpleBindResult(),parameter,executor);
        }

        /**
         * Initiates a test bind with a detailed result.
         * @param parameter Bind parameter.
         * @param executor Executor.
         * @return Result.
         */
        public static CompletableFuture<DetailedBindResult> withDetailedBindResult(BindParameter parameter,
                                                                                   Executor executor) {
            return initiateTestBind(BindDecorations.createWithDetailedBindResult(),parameter,executor);
        }
    }
}
