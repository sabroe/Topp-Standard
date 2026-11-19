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
import jakarta.servlet.http.HttpServlet;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlet.ServletHolder;

import java.util.concurrent.CompletableFuture;

/**
 * Utility addressing instances of {@link Javalin}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-18
 */
@UtilityClass
@Slf4j
public class Javalins {
    /**
     * Creates a Javalin instance that mounts a servlet transport at an endpoint.
     * @param transport Servlet transport.
     * @param endpoint Servlet path of the endpoint, e.g. {@code "/mcp"}.
     * @return Javalin instance, not started.
     */
    public static Javalin create(@NonNull HttpServlet transport,
                                 @NonNull String endpoint) {
        return Javalin.create(config -> {
            config.jetty.modifyServletContextHandler(handler -> handler.addServlet(new ServletHolder(transport), endpoint));
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
     * @param app Javalin instance.
     * @param port Port.
     * @return Shutdown handle.
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
     * The action is guaranteed to run before the server stops,
     * and the server is stopped even if the action fails.
     * </p>
     * @param handle Shutdown handle, as returned by {@link #start(Javalin, int)}.
     * @param beforeStop Action to run before the server stops, e.g. closing an MCP server.
     * @return The same shutdown handle.
     */
    public static CompletableFuture<Void> stopOnShutdown(@NonNull CompletableFuture<Void> handle,
                                                         @NonNull Runnable beforeStop) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                beforeStop.run();
            } finally {
                handle.complete(null);
            }
        }, "javalin-shutdown"));
        return handle;
    }
}
