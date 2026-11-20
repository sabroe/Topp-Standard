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
 * Topp Standard MCP Server library.
 * <p>
 * Utilities on top of the server side of the MCP Java SDK.
 * The SDK is required transitively, so users of this module can use the SDK types directly.
 * </p>
 * <p>
 * Packages:
 * </p>
 * <ul>
 *     <li>{@link com.yelstream.topp.standard.mcp.server}:
 *         definitions of MCP servers independent of any transport, and rewriting of transport error bodies.</li>
 *     <li>{@link com.yelstream.topp.standard.mcp.spec}:
 *         utilities addressing the MCP schema, e.g. demo tools.</li>
 * </ul>
 * <p>
 * Independent of any HTTP server; hosting of servlet transports is done elsewhere, e.g. with Javalin.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-17
 */
module com.yelstream.topp.standard.mcp {
    requires static lombok;
    requires org.slf4j;
    requires transitive io.modelcontextprotocol.sdk.mcp;
    exports com.yelstream.topp.standard.mcp.server;
    exports com.yelstream.topp.standard.mcp.spec;
}
