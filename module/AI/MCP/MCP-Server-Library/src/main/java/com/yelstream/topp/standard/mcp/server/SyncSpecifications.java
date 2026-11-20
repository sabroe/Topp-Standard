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

package com.yelstream.topp.standard.mcp.server;

import io.modelcontextprotocol.server.McpServer;
import lombok.experimental.UtilityClass;

/**
 * Utilities addressing instances of {@link McpServer.SyncSpecification}.
 * <p>
 * In the MCP Java SDK, a synchronous server specification is created by {@code McpServer.sync(transport)},
 * already bound to a transport. It collects the server information, features and settings,
 * and builds the {@link io.modelcontextprotocol.server.McpSyncServer}.
 * </p>
 * <p>
 * Related utilities:
 * </p>
 * <ul>
 *     <li>{@link McpSyncServers}: a definition of a synchronous server, independent of any transport,
 *         applied to a specification when bound to a transport.</li>
 *     <li>{@link McpServers}: utilities addressing {@link McpServer}, the entry point creating specifications.</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-17
 */
@UtilityClass
public class SyncSpecifications {
}
