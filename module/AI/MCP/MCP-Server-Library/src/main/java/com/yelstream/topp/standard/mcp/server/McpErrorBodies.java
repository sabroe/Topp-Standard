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

import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utilities addressing error bodies written by MCP servlet transports.
 * <p>
 * The MCP Java SDK servlet transports serialize HTTP-level errors as the {@code McpError} exception itself,
 * exposing stack trace and other exception internals, and not forming a valid JSON-RPC error response.
 * </p>
 * <p>
 * {@link #toJsonRpcError(int, String)} rewrites such a body into a JSON-RPC error response.
 * It matches the signature of an error body rewriter, e.g. as applied by a servlet filter in front of the transport:
 * </p>
 * <pre>{@code
 * Javalin app = Javalins.create(transport, "/mcp", McpErrorBodies::toJsonRpcError);
 * }</pre>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-19
 */
@UtilityClass
public class McpErrorBodies {
    /**
     * Body used when an error body cannot be interpreted.
     * <p>
     * A JSON-RPC error response with code {@link McpSchema.ErrorCodes#INTERNAL_ERROR} and no {@code id}.
     * </p>
     */
    private static final String FALLBACK =
        "{\"jsonrpc\":\"2.0\",\"id\":null,\"error\":{\"code\":" + McpSchema.ErrorCodes.INTERNAL_ERROR + ",\"message\":\"Internal error\"}}";

    /**
     * Turns a serialized {@code McpError} into a JSON-RPC error response,
     * dropping stack trace and other exception internals.
     * <p>
     * Bodies that cannot be interpreted are replaced by a generic internal error.
     * </p>
     * <p>
     * From the serialized exception, only {@code jsonRpcError.code}, {@code jsonRpcError.message}
     * and, if present, {@code jsonRpcError.data} are kept.
     * The result has the form:
     * </p>
     * <pre>{@code
     * {"jsonrpc":"2.0","id":null,"error":{"code":-32602,"message":"...","data":...}}
     * }</pre>
     * <p>
     * The {@code id} is always {@code null}, since the id of the failed request is not known from the body.
     * The result is a generic internal error if the body is not JSON,
     * or lacks a numeric {@code code} or a string {@code message}.
     * Never throws.
     * </p>
     * @param status HTTP status code; not used, present to match the signature of an error body rewriter.
     * @param body Original body, as written by the transport.
     * @return JSON-RPC error response.
     */
    public static String toJsonRpcError(int status,
                                        String body) {
        McpJsonMapper mapper = McpJsonDefaults.getMapper();
        try {
            Map<?, ?> source = mapper.readValue(body, Map.class);
            if (!(source.get("jsonRpcError") instanceof Map<?, ?> sourceError)
                    || !(sourceError.get("code") instanceof Number code)
                    || !(sourceError.get("message") instanceof String message)) {
                return FALLBACK;
            }
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("code", code);
            error.put("message", message);
            if (sourceError.get("data") != null) {
                error.put("data", sourceError.get("data"));
            }
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("jsonrpc", McpSchema.JSONRPC_VERSION);
            response.put("id", null);
            response.put("error", error);
            return mapper.writeValueAsString(response);
        } catch (IOException | RuntimeException ex) {
            return FALLBACK;
        }
    }
}
