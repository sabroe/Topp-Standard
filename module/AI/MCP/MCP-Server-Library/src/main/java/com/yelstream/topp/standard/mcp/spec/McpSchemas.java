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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Utilities addressing instances of {@link McpSchema}.
 * <p>
 * Offers two demo tools, each as a tool description, a call handler, and a specification combining the two:
 * </p>
 * <ul>
 *     <li>{@code add_numbers}: adds two integers;
 *         see {@link #addNumbersSpecification()}.</li>
 *     <li>{@code greet_person}: greets a person by name;
 *         see {@link #greetPersonSpecification()}.</li>
 * </ul>
 * <p>
 * The tools can be added to a server definition from their descriptions and call handlers, e.g.
 * {@code McpSyncServers.Definition.builder().toolSpecification(McpSchemas.addNumbersTool(), McpSchemas::addNumbers)},
 * or as specifications, e.g. {@code .tool(McpSchemas.addNumbersSpecification())}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-17
 */
@UtilityClass
public class McpSchemas {

    // Tool 1: Addition

    /**
     * Creates the description of the addition tool, {@code add_numbers}.
     * <p>
     * The input schema requires two integer arguments, {@code a} and {@code b}.
     * </p>
     * @return Created tool description.
     */
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

    /**
     * Handles a call of the addition tool.
     * <p>
     * Returns the sum of the arguments {@code a} and {@code b} as text.
     * Each argument must be an integer within the range of {@code long};
     * as in JSON Schema, a number with a zero fractional part, e.g. {@code 3.0}, counts as an integer.
     * </p>
     * <p>
     * Instead of throwing, returns an error result with the text {@code "Invalid parameters: ..."}
     * if an argument is missing, not a number, not an integer or out of range,
     * and with the text {@code "Sum overflows: ..."} if the sum is out of the range of {@code long}.
     * </p>
     * @param exchange Exchange with the client; not used.
     * @param request Tool call request.
     * @return Tool call result.
     */
    public static McpSchema.CallToolResult addNumbers(McpSyncServerExchange exchange,
                                                      McpSchema.CallToolRequest request) {
        long a;
        long b;
        try {
            Map<String, Object> arguments = request.arguments() == null ? Map.of() : request.arguments();
            a = integerArgument(arguments, "a");
            b = integerArgument(arguments, "b");
        } catch (IllegalArgumentException e) {
            return errorResult("Invalid parameters: " + e.getMessage());
        }
        try {
            return McpSchema.CallToolResult.builder().addTextContent(Long.toString(Math.addExact(a, b))).build();
        } catch (ArithmeticException e) {
            return errorResult("Sum overflows: " + a + " + " + b);
        }
    }

    /**
     * Reads an integer argument of a tool call.
     * @param arguments Arguments of the tool call.
     * @param name Name of the argument.
     * @return Value of the argument.
     * @throws IllegalArgumentException If the argument is missing, not a number, not an integer,
     *                                  or out of the range of {@code long}.
     */
    private static long integerArgument(Map<String, Object> arguments,
                                        String name) {
        Object value = arguments.get(name);
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' is missing");
        }
        if (!(value instanceof Number number)) {
            throw new IllegalArgumentException("'" + name + "' is not a number: " + value);
        }
        try {
            return new BigDecimal(number.toString()).longValueExact();
        } catch (NumberFormatException | ArithmeticException e) {
            throw new IllegalArgumentException("'" + name + "' is not an integer in range: " + value, e);
        }
    }

    /**
     * Creates an error result of a tool call.
     * @param message Error message.
     * @return Tool call result, flagged as an error.
     */
    private static McpSchema.CallToolResult errorResult(String message) {
        return McpSchema.CallToolResult.builder().addTextContent(message).isError(true).build();
    }

    // Tool 2: Greeting

    /**
     * Creates the description of the greeting tool, {@code greet_person}.
     * <p>
     * The input schema has one optional string argument, {@code name}.
     * </p>
     * @return Created tool description.
     */
    public static McpSchema.Tool greetPersonTool() {
        Map<String, Object> inputSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "name", Map.of("type", "string", "description", "Name to greet (optional)")));
        return McpSchema.Tool.builder("greet_person", inputSchema)
                .description("Gives a friendly greeting")
                .build();
    }

    /**
     * Handles a call of the greeting tool.
     * <p>
     * Returns the text {@code "Hello, <name>!"}, or {@code "Hello, stranger!"} if no name is given.
     * A name that is not a string is used in its string form.
     * Never returns an error result.
     * </p>
     * @param exchange Exchange with the client; not used.
     * @param request Tool call request.
     * @return Tool call result.
     */
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
