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

import com.yelstream.topp.standard.mcp.server.McpSyncServers;
import com.yelstream.topp.standard.mcp.spec.McpSchemas;

/**
 * Minimal MCP server, exposed over Streamable-HTTP.
 * <p>
 * Offers two demo tools, adding numbers and greeting a person,
 * on {@link McpHttpServer#DEFAULT_PORT} at {@link McpHttpServer#DEFAULT_MCP_ENDPOINT}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-18
 */
public class MinimalMcpServer {
    /**
     * Starts the MCP server and waits until it stops,
     * e.g. when the JVM is shut down by Ctrl+C.
     * @param args Command-line arguments; ignored.
     */
    public static void main(String[] args) {
        McpSyncServers.Definition definition = McpSyncServers.Definition.builder()
                .serverInfo("Minimal MCP Server", "0.1.0")
                .toolSpecification(McpSchemas.addNumbersTool(),McpSchemas::addNumbers)
                .toolSpecification(McpSchemas.greetPersonTool(),McpSchemas::greetPerson)
                .build();

        try (McpHttpServer server = McpHttpServer.start(definition)) {
            server.join();
        }
    }
}
