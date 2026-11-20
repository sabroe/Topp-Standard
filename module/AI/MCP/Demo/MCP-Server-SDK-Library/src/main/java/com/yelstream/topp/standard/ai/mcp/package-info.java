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

/**
 * Demo of an MCP server built on the MCP Java SDK, exposed over Streamable-HTTP by Javalin.
 * <p>
 * Contents:
 * </p>
 * <ul>
 *     <li>{@link com.yelstream.topp.standard.ai.mcp.McpHttpServer}:
 *         running MCP server, created and started from an MCP server definition,
 *         exposed over Streamable-HTTP by Javalin;
 *         e.g. {@code McpHttpServer.start(definition, port, endpoint)},
 *         after which {@code join()} waits until the server stops, and {@code close()} stops it.</li>
 *     <li>{@link com.yelstream.topp.standard.ai.mcp.MinimalMcpServer}:
 *         runnable minimal MCP server offering two demo tools.</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-19
 */
package com.yelstream.topp.standard.ai.mcp;
