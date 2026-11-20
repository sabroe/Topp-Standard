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
 * Topp Standard Javalin library.
 * <p>
 * Utilities for hosting servlet transports, e.g. MCP Streamable-HTTP transports, in Javalin.
 * Javalin is required transitively, so users of this module can use the Javalin types directly.
 * </p>
 * <p>
 * Package {@link com.yelstream.topp.standard.javalin}:
 * </p>
 * <ul>
 *     <li>{@link com.yelstream.topp.standard.javalin.Javalins}:
 *         creation of a Javalin instance mounting a servlet transport at an endpoint,
 *         and lifecycle of the server through a shutdown handle.</li>
 *     <li>{@link com.yelstream.topp.standard.javalin.ErrorBodyRewriteFilter}:
 *         servlet filter rewriting the bodies of error responses.</li>
 * </ul>
 * <p>
 * Independent of MCP; the transport is any {@code jakarta.servlet.http.HttpServlet}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-01-26
 */
module com.yelstream.topp.standard.javalin {
    requires static lombok;
    requires org.slf4j;
    requires transitive io.javalin;
    exports com.yelstream.topp.standard.javalin;
}
