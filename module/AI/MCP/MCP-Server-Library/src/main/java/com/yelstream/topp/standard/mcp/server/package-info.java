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
 * Utilities addressing MCP servers of the MCP Java SDK.
 * <p>
 * Contents:
 * </p>
 * <ul>
 *     <li>{@link com.yelstream.topp.standard.mcp.server.McpSyncServers}:
 *         definition of a synchronous MCP server, independent of any transport, bound to a transport later.</li>
 *     <li>{@link com.yelstream.topp.standard.mcp.server.McpAsyncServers}:
 *         the same for asynchronous MCP servers.</li>
 *     <li>{@link com.yelstream.topp.standard.mcp.server.McpErrorBodies}:
 *         rewriting of error bodies written by MCP servlet transports into JSON-RPC error responses;
 *         meant as the rewriter of a servlet filter in front of the transport,
 *         e.g. {@code ErrorBodyRewriteFilter} via
 *         {@code Javalins.create(transport, "/mcp", McpErrorBodies::toJsonRpcError)}.</li>
 *     <li>{@link com.yelstream.topp.standard.mcp.server.McpServers} and
 *         {@link com.yelstream.topp.standard.mcp.server.SyncSpecifications}:
 *         utilities addressing server builders and specifications of the SDK.</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-19
 */
package com.yelstream.topp.standard.mcp.server;
