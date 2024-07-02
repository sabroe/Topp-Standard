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

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

/**
 * Utility addressing instances of {@link ServerSocket}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-02
 */
@UtilityClass
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
     * Tests if a socket can be bound.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param port Local port.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be bound.
     */
    public static void testBind(int port) throws IOException {
        testBind(serverSocket -> serverSocket.bind(new InetSocketAddress("localhost",port)));
    }

    /**
     * Tests if a socket can be bound.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param endpoint Endpoint to bind to.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be bound.
     */
    public static void testBind(SocketAddress endpoint) throws IOException {
        testBind(serverSocket -> serverSocket.bind(endpoint));
    }

    /**
     * Tests if a socket can be bound.
     * <p>
     *     This is intended for probing an endpoint.
     * </p>
     * @param endpoint Endpoint to bind to.
     * @param backlog Requested maximum length of the queue of incoming connections.
     * @throws IOException Thrown in case of I/O error.
     *                     This is thrown if the socket cannot be bound.
     */
    public static void testBind(SocketAddress endpoint,
                                int backlog) throws IOException {
        testBind(serverSocket -> serverSocket.bind(endpoint,backlog));
    }
}
