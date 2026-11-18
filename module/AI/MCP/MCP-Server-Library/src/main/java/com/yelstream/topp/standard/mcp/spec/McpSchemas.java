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

package com.yelstream.topp.standard.mcp.spec;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;

/**
 * Utilities addressing instances of {@link McpSchema}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-17
 */
@UtilityClass
public class McpSchemas {

    // Tool 1: Addition
    public static McpSchema.Tool addNumbersTool() {
        Map<String, Object> inputSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "a", Map.of("type", "integer", "description", "First number"),
                        "b", Map.of("type", "integer", "description", "Second number")),
                "required", List.of("a", "b"));
        return McpSchema.Tool.builder("add_numbers", inputSchema)
                .description("Adds two integers and returns the sum")
                .build();
    }

    public static McpSchema.CallToolResult addNumbers(McpSyncServerExchange exchange,
                                                      McpSchema.CallToolRequest request) {
        try {
            long a = ((Number)request.arguments().get("a")).longValue();
            long b = ((Number)request.arguments().get("b")).longValue();
            return McpSchema.CallToolResult.builder().addTextContent(Long.toString(a + b)).build();
        } catch (RuntimeException e) {
            return McpSchema.CallToolResult.builder().addTextContent("Invalid parameters: " + e.getMessage()).isError(true).build();
        }
    }

    // Tool 2: Greeting
    public static McpSchema.Tool greetPersonTool() {
        Map<String, Object> inputSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "name", Map.of("type", "string", "description", "Name to greet (optional)")));
        return McpSchema.Tool.builder("greet_person", inputSchema)
                .description("Gives a friendly greeting")
                .build();
    }

    public static McpSchema.CallToolResult greetPerson(McpSyncServerExchange exchange,
                                                       McpSchema.CallToolRequest request) {
        Object name = request.arguments() == null ? null : request.arguments().get("name");
        String message = "Hello, " + (name == null ? "stranger" : name) + "!";
        return McpSchema.CallToolResult.builder().addTextContent(message).build();
    }

    /**
     * Creates the specification of the addition tool, combining {@link #addNumbersTool()} and {@link #addNumbers}.
     * @return Created tool specification.
     */
    public static McpServerFeatures.SyncToolSpecification addNumbersSpecification() {
        return McpServerFeatures.SyncToolSpecification.builder()
                .tool(addNumbersTool())
                .callHandler(McpSchemas::addNumbers)
                .build();
    }

    /**
     * Creates the specification of the greeting tool, combining {@link #greetPersonTool()} and {@link #greetPerson}.
     * @return Created tool specification.
     */
    public static McpServerFeatures.SyncToolSpecification greetPersonSpecification() {
        return McpServerFeatures.SyncToolSpecification.builder()
                .tool(greetPersonTool())
                .callHandler(McpSchemas::greetPerson)
                .build();
    }
}
