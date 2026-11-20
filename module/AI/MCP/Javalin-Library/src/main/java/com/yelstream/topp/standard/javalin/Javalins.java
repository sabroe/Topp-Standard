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

package com.yelstream.topp.standard.javalin;

import io.javalin.Javalin;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServlet;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlet.FilterHolder;
import org.eclipse.jetty.ee10.servlet.ServletHolder;

import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;

/**
 * Utility addressing instances of {@link Javalin}.
 * <p>
 * Hosts a servlet transport, e.g. an MCP Streamable-HTTP transport, in Javalin,
 * and manages the lifecycle of the server through a shutdown handle:
 * </p>
 * <ol>
 *     <li>{@link #create(HttpServlet, String, ErrorBodyRewriteFilter.ErrorBodyRewriter)}
 *         mounts the transport at an endpoint;</li>
 *     <li>{@link #start(Javalin, int)} starts the server and returns the shutdown handle;</li>
 *     <li>{@link #stopOnShutdown(CompletableFuture, Runnable)} completes the handle on JVM shutdown.</li>
 * </ol>
 * <p>
 * Usage:
 * </p>
 * <pre>{@code
 * Javalin app = Javalins.create(transport, "/mcp");
 * CompletableFuture<Void> running =
 *         Javalins.stopOnShutdown(Javalins.start(app, 8080), mcpServer::closeGracefully);
 * running.join();
 * }</pre>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-18
 */
@UtilityClass
@Slf4j
public class Javalins {
    /**
     * Creates a Javalin instance that mounts a servlet transport at an endpoint.
     * <p>
     * Same as {@link #create(HttpServlet, String, ErrorBodyRewriteFilter.ErrorBodyRewriter)}
     * without rewriting of error bodies.
     * </p>
     * @param transport Servlet transport.
     * @param endpoint Servlet path of the endpoint, e.g. {@code "/mcp"}.
     * @return Javalin instance, not started.
     * @throws NullPointerException If {@code transport} or {@code endpoint} is {@code null}.
     */
    public static Javalin create(@NonNull HttpServlet transport,
                                 @NonNull String endpoint) {
        return create(transport, endpoint, null);
    }

    /**
     * Creates a Javalin instance that mounts a servlet transport at an endpoint,
     * optionally rewriting the bodies of error responses produced by the transport.
     * <p>
     * The transport is added as a servlet to Jetty's servlet context, outside Javalin's own routing.
     * If given, the rewriter is applied by a filter in front of the transport, for requests to the endpoint.
     * In addition, {@code GET /} answers with a plain-text hint naming the endpoint.
     * </p>
     * @param transport Servlet transport.
     * @param endpoint Servlet path of the endpoint, e.g. {@code "/mcp"}.
     * @param errorBodyRewriter Rewriter of error bodies; {@code null} for none.
     * @return Javalin instance, not started.
     * @throws NullPointerException If {@code transport} or {@code endpoint} is {@code null}.
     */
    public static Javalin create(@NonNull HttpServlet transport,
                                 @NonNull String endpoint,
                                 ErrorBodyRewriteFilter.ErrorBodyRewriter errorBodyRewriter) {
        return Javalin.create(config -> {
            config.jetty.modifyServletContextHandler(handler -> {
                if (errorBodyRewriter != null) {
                    handler.addFilter(new FilterHolder(new ErrorBodyRewriteFilter(errorBodyRewriter)),
                                      endpoint,
                                      EnumSet.of(DispatcherType.REQUEST));
                }
                handler.addServlet(new ServletHolder(transport), endpoint);
            });
            config.routes.get("/",
                              ctx -> ctx.result("Server running - connect clients to " + endpoint));
        });
    }

    /**
     * Starts a Javalin instance.
     * <p>
     * The returned future acts as the shutdown handle:
     * Completing it, or cancelling it, stops the server.
     * Anyone waiting on it, e.g. via {@code join()}, is released when that happens.
     * </p>
     * <p>
     * Blocks until the server is started.
     * </p>
     * @param app Javalin instance, not started.
     * @param port Port; {@code 0} means any free port, available afterwards as {@link Javalin#port()}.
     * @return Shutdown handle.
     * @throws NullPointerException If {@code app} is {@code null}.
     */
    public static CompletableFuture<Void> start(@NonNull Javalin app,
                                                int port) {
        CompletableFuture<Void> handle = new CompletableFuture<>();
        handle.whenComplete((ignored, ex) -> app.stop());
        app.start(port);
        return handle;
    }

    /**
     * Registers a JVM shutdown hook that runs an action and then completes a shutdown handle.
     * <p>
     * When the hook stops the server, the action is guaranteed to run before the server stops,
     * and the server is stopped even if the action fails.
     * </p>
     * <p>
     * The hook runs in a thread named {@code javalin-shutdown}.
     * Once the handle completes, by the hook or otherwise, e.g. on a normal stop, the hook is removed;
     * the action then no longer runs on JVM shutdown.
     * </p>
     * @param handle Shutdown handle, as returned by {@link #start(Javalin, int)}.
     * @param beforeStop Action to run before the server stops, e.g. closing an MCP server.
     * @return The same shutdown handle.
     * @throws NullPointerException If {@code handle} or {@code beforeStop} is {@code null}.
     */
    public static CompletableFuture<Void> stopOnShutdown(@NonNull CompletableFuture<Void> handle,
                                                         @NonNull Runnable beforeStop) {
        Thread hook = new Thread(() -> {
            try {
                beforeStop.run();
            } finally {
                handle.complete(null);
            }
        }, "javalin-shutdown");
        Runtime.getRuntime().addShutdownHook(hook);
        handle.whenComplete((ignored, ex) -> {
            try {
                Runtime.getRuntime().removeShutdownHook(hook);
            } catch (IllegalStateException ignoredWhileShuttingDown) {
                // JVM shutdown in progress; hooks can no longer be removed, nor need to be.
            }
        });
        return handle;
    }
}
