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

package com.yelstream.topp.standard.ai.mcp;

import com.yelstream.topp.standard.javalin.Javalins;
import com.yelstream.topp.standard.mcp.server.McpErrorBodies;
import com.yelstream.topp.standard.mcp.server.McpSyncServers;
import io.javalin.Javalin;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * Running MCP server, exposed over Streamable-HTTP by Javalin.
 * <p>
 * Created and started from an MCP server definition by {@link #builder()} and {@link Builder#start()},
 * or by the shorthand {@link #start(McpSyncServers.Definition, Integer, String)}.
 * Starting binds the definition to a Streamable-HTTP transport, mounts the transport in Javalin
 * at the MCP endpoint and starts Javalin.
 * </p>
 * <p>
 * The server stops when the shutdown handle {@link #running()} completes:
 * </p>
 * <ul>
 *     <li>by {@link #close()}, e.g. at the end of a try-with-resources statement;</li>
 *     <li>by the JVM shutdown hook registered when starting, e.g. on Ctrl+C;</li>
 *     <li>by completing or cancelling the handle directly.</li>
 * </ul>
 * <p>
 * In each case the MCP server is closed gracefully and Javalin is stopped.
 * Starting and stopping are logged.
 * </p>
 * <p>
 * Usage:
 * </p>
 * <pre>{@code
 * try (McpHttpServer server = McpHttpServer.builder()
 *         .definition(definition)
 *         .port(8080)
 *         .mcpEndpoint("/mcp")
 *         .start()) {
 *     server.join();
 * }
 * }</pre>
 * @param port Actual port listened on;
 *             differs from the requested port when started with port {@code 0}.
 * @param mcpEndpoint MCP endpoint, e.g. {@code "/mcp"};
 *                    the servlet path at which the transport is mounted.
 * @param transport Streamable-HTTP transport;
 *                  a servlet that handles the MCP requests and holds the MCP sessions.
 * @param mcpServer MCP server, bound to the transport;
 *                  closed gracefully by {@link #close()} and by the JVM shutdown hook.
 * @param app Javalin instance, started; hosts the transport.
 * @param running Shutdown handle; completed when the server stops.
 *                Completing it, or cancelling it, stops Javalin;
 *                see {@link #join()} and {@link #close()}.
 */
@Slf4j
public record McpHttpServer(int port,
                            String mcpEndpoint,
                            HttpServletStreamableServerTransportProvider transport,
                            McpSyncServer mcpServer,
                            Javalin app,
                            CompletableFuture<Void> running) implements AutoCloseable {
    /**
     * Default port.
     * <p>
     * Used when no port is given, i.e. when the port is {@code null}.
     * </p>
     */
    public static final int DEFAULT_PORT = 8080;

    /**
     * Default MCP endpoint.
     * <p>
     * Used when no MCP endpoint is given, i.e. when the MCP endpoint is {@code null}.
     * </p>
     */
    public static final String DEFAULT_MCP_ENDPOINT = "/mcp";

    /**
     * Creates and starts an MCP server.
     * <p>
     * Binds the definition to a Streamable-HTTP transport, mounts the transport in Javalin at the MCP endpoint,
     * starts Javalin and registers a JVM shutdown hook that closes the MCP server and stops Javalin.
     * Blocks until the server is started.
     * If Javalin fails to start, the MCP server is closed and the failure is rethrown.
     * </p>
     * <p>
     * Not called directly; the target of the Lombok-generated {@link Builder},
     * whose {@link Builder#start()} invokes it.
     * </p>
     * @param definition MCP server definition.
     * @param port Port; {@code null} means {@link #DEFAULT_PORT}, {@code 0} means any free port.
     * @param mcpEndpoint MCP endpoint; {@code null} means {@link #DEFAULT_MCP_ENDPOINT}.
     * @return Running MCP server.
     * @throws NullPointerException If {@code definition} is {@code null}.
     */
    @lombok.Builder(builderClassName = "Builder", buildMethodName = "start")
    private static McpHttpServer launch(@NonNull McpSyncServers.Definition definition,
                                        Integer port,
                                        String mcpEndpoint) {
        int requestedPort = port == null ? DEFAULT_PORT : port;
        String endpoint = mcpEndpoint == null ? DEFAULT_MCP_ENDPOINT : mcpEndpoint;

        HttpServletStreamableServerTransportProvider transport =
            HttpServletStreamableServerTransportProvider.builder()
                .mcpEndpoint(endpoint)
                .build();
        McpSyncServer mcpServer = definition.bind(transport);
        try {
            Javalin app = Javalins.create(transport, endpoint, McpErrorBodies::toJsonRpcError);
            CompletableFuture<Void> running =
                Javalins.stopOnShutdown(Javalins.start(app, requestedPort), mcpServer::closeGracefully);
            McpHttpServer server = new McpHttpServer(app.port(), endpoint, transport, mcpServer, app, running);
            log.info("Started MCP server at {}", server.url());
            running.whenComplete((ignored, failure) -> log.info("Stopped MCP server at {}", server.url()));
            return server;
        } catch (RuntimeException ex) {
            mcpServer.closeGracefully();
            throw ex;
        }
    }

    /**
     * Builder of an MCP server; {@link #start()} creates and starts it.
     * <p>
     * Settable properties:
     * </p>
     * <ul>
     *     <li>{@code definition}: MCP server definition; required,
     *         {@link #start()} throws {@link NullPointerException} if it is not set.</li>
     *     <li>{@code port}: Port; unset means {@link McpHttpServer#DEFAULT_PORT}, {@code 0} means any free port.</li>
     *     <li>{@code mcpEndpoint}: MCP endpoint; unset means {@link McpHttpServer#DEFAULT_MCP_ENDPOINT}.</li>
     * </ul>
     * <p>
     * Generated by Lombok from the private method {@code launch};
     * declared here only to carry this documentation.
     * </p>
     */
    public static class Builder {
    }

    /**
     * Creates a builder of an MCP server.
     * @return Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates and starts an MCP server.
     * <p>
     * Shorthand for {@link #builder()} followed by {@link Builder#start()}.
     * Blocks until the server is started.
     * </p>
     * @param definition MCP server definition, e.g. server information and tools;
     *                   independent of any transport. Required.
     * @param port Port to listen on;
     *             {@code null} means {@link #DEFAULT_PORT}, {@code 0} means any free port.
     *             The port actually listened on is available as {@link #port()}.
     * @param endpoint MCP endpoint, e.g. {@code "/mcp"}; the servlet path at which the transport is mounted.
     *                 {@code null} means {@link #DEFAULT_MCP_ENDPOINT}.
     * @return Running MCP server.
     * @throws NullPointerException If {@code definition} is {@code null}.
     */
    public static McpHttpServer start(@NonNull McpSyncServers.Definition definition,
                                      @NonNull Integer port,
                                      @NonNull String endpoint) {
        return McpHttpServer.builder()
            .definition(definition)
            .port(port)
            .mcpEndpoint(endpoint)
            .start();
    }

    public static McpHttpServer start(@NonNull McpSyncServers.Definition definition) {
        return McpHttpServer.builder()
            .definition(definition)
            .start();
    }

    /**
     * Gets the local URL of the MCP endpoint.
     * <p>
     * Uses the port actually listened on, so it is correct also when started with port {@code 0}.
     * </p>
     * @return URL.
     */
    public String url() {
        return "http://localhost:" + port + mcpEndpoint;
    }

    /**
     * Waits until the server stops.
     * <p>
     * Returns when the shutdown handle {@link #running()} completes,
     * whether by {@link #close()}, by the JVM shutdown hook, or by completing the handle directly.
     * If the handle is completed exceptionally or cancelled,
     * this throws {@link java.util.concurrent.CompletionException} or
     * {@link java.util.concurrent.CancellationException} instead of returning.
     * </p>
     */
    public void join() {
        running.join();
    }

    /**
     * Stops the server.
     * <p>
     * Closes the MCP server gracefully, closing its sessions,
     * and then completes the shutdown handle {@link #running()}, which stops Javalin.
     * Javalin is stopped even if closing the MCP server fails.
     * </p>
     * <p>
     * Safe to call more than once, also together with the JVM shutdown hook;
     * repeated calls have no further effect.
     * </p>
     */
    @Override
    public void close() {
        try {
            mcpServer.closeGracefully();
        } finally {
            running.complete(null);
        }
    }
}
