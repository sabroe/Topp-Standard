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

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.schema.JsonSchemaValidator;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncListFilter;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpServerTransportProvider;
import io.modelcontextprotocol.spec.McpStreamableServerTransportProvider;
import io.modelcontextprotocol.util.McpUriTemplateManagerFactory;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Utilities addressing instances of {@link McpSyncServer}.
 * <p>
 * Separates what a server offers, a {@link Definition}, from how it is reached, a transport:
 * </p>
 * <pre>{@code
 * McpSyncServers.Definition definition = McpSyncServers.Definition.builder()
 *         .serverInfo("My MCP Server", "1.0.0")
 *         .toolSpecification(tool, callHandler)
 *         .build();
 * McpSyncServer mcpServer = definition.bind(transport);
 * }</pre>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-11-17
 */
@UtilityClass
public class McpSyncServers {
    /**
     * Definition of everything a synchronous MCP server offers, independent of any transport.
     * <p>
     *     The MCP Java SDK creates a {@link McpSyncServer} already bound to a transport.
     *     This definition holds the server information and features up front,
     *     and creates a server once a transport is at hand.
     * </p>
     * <p>
     *     Note that each binding creates a separate server instance.
     *     State shared by the handlers of the features must be safe for concurrent use.
     * </p>
     * <p>
     *     The definition itself is not validated when built.
     *     Validation is done by the SDK when binding, e.g. of the server information and of tool names.
     * </p>
     * <p>
     *     List components are never {@code null}; when not set, they are empty.
     *     The builder offers both a singular method adding one element, e.g. {@code tool(...)},
     *     and a plural method adding several, e.g. {@code tools(...)}.
     * </p>
     * @param serverInfo Server information; name and version.
     *                   Required; binding fails if this is {@code null}.
     * @param instructions Instructions for clients on how to use the server.
     *                     This may be {@code null}.
     * @param capabilities Server capabilities.
     *                     This may be {@code null}, which keeps the default of the SDK.
     * @param tools Tools.
     *              Tool names must be unique, and should be valid; binding fails on duplicates,
     *              and on invalid names when tool names are validated strictly.
     * @param resources Resources.
     * @param resourceTemplates Resource templates.
     * @param prompts Prompts.
     * @param completions Completions.
     * @param rootsChangeHandlers Handlers called when the roots of a client change.
     * @param toolFilters Filters deciding per request which tools are visible.
     * @param jsonMapper JSON mapper.
     *                   This may be {@code null}, which keeps the default of the SDK.
     * @param jsonSchemaValidator Validator of JSON schemas of tool inputs.
     *                            This may be {@code null}, which keeps the default of the SDK.
     * @param validateToolInputs Indicates whether tool inputs are validated against their schemas.
     *                           This may be {@code null}, which keeps the default of the SDK.
     * @param strictToolNameValidation Indicates whether tool names are validated strictly.
     *                                 This may be {@code null}, which keeps the default of the SDK.
     * @param requestTimeout Timeout of requests sent from the server to the client.
     *                       This may be {@code null}, which keeps the default of the SDK.
     * @param uriTemplateManagerFactory Factory of managers of URI templates of resource templates.
     *                                  This may be {@code null}, which keeps the default of the SDK.
     * @param immediateExecution Indicates whether handlers execute immediately on the calling thread.
     *                           This may be {@code null}, which keeps the default of the SDK.
     */
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public record Definition(McpSchema.Implementation serverInfo,
                             String instructions,
                             McpSchema.ServerCapabilities capabilities,
                             @lombok.Singular List<McpServerFeatures.SyncToolSpecification> tools,
                             @lombok.Singular List<McpServerFeatures.SyncResourceSpecification> resources,
                             @lombok.Singular List<McpServerFeatures.SyncResourceTemplateSpecification> resourceTemplates,
                             @lombok.Singular List<McpServerFeatures.SyncPromptSpecification> prompts,
                             @lombok.Singular List<McpServerFeatures.SyncCompletionSpecification> completions,
                             @lombok.Singular List<BiConsumer<McpSyncServerExchange,List<McpSchema.Root>>> rootsChangeHandlers,
                             @lombok.Singular List<McpSyncListFilter<McpSchema.Tool>> toolFilters,
                             McpJsonMapper jsonMapper,
                             JsonSchemaValidator jsonSchemaValidator,
                             Boolean validateToolInputs,
                             Boolean strictToolNameValidation,
                             Duration requestTimeout,
                             McpUriTemplateManagerFactory uriTemplateManagerFactory,
                             Boolean immediateExecution) {
        /**
         * Builder of a definition of a synchronous MCP server.
         * <p>
         * Generated by Lombok, completing the methods declared here.
         * Each component has a setter of the same name;
         * list components have a singular method adding one element, a plural method adding several,
         * and a method clearing the list, e.g. {@code tool(...)}, {@code tools(...)} and {@code clearTools()}.
         * </p>
         * <p>
         * In addition, the server information can be set from a name and a version,
         * {@link #serverInfo(String, String)},
         * and a tool can be added from its description and its call handler,
         * {@link #toolSpecification(McpSchema.Tool, BiFunction)}.
         * </p>
         */
        public static class Builder {
            /**
             * Sets the server information.
             * <p>
             * Declared here, since Lombok does not generate a setter
             * when a method of the same name exists, here {@link #serverInfo(String, String)}.
             * </p>
             * @param serverInfo Server information; name and version.
             * @return This builder.
             */
            public Builder serverInfo(McpSchema.Implementation serverInfo) {
                this.serverInfo = serverInfo;
                return this;
            }

            /**
             * Sets the server information from a name and a version.
             * @param name Name of the server.
             * @param version Version of the server.
             * @return This builder.
             */
            public Builder serverInfo(String name, String version) {
                return serverInfo(McpSchema.Implementation.builder(name, version).build());
            }

            /**
             * Adds a tool from its description and its call handler.
             * <p>
             * Shorthand for {@code tool(...)} with a tool specification combining the two.
             * </p>
             * @param tool Tool description; name, description and input schema.
             * @param callHandler Handler of calls of the tool.
             * @return This builder.
             */
            public Builder toolSpecification(McpSchema.Tool tool,
                                             BiFunction<McpSyncServerExchange, McpSchema.CallToolRequest, McpSchema.CallToolResult> callHandler) {
                return tool(McpServerFeatures.SyncToolSpecification.builder()
                                .tool(tool)
                                .callHandler(callHandler)
                                .build());
            }
        }

        /**
         * Creates a builder of a definition of a synchronous MCP server.
         * <p>
         * To derive a definition from an existing one, use {@code toBuilder()}.
         * </p>
         * @return Builder.
         */
        public static Builder builder() {
            return new Builder();
        }

        /**
         * Binds this definition to a streamable transport, creating a server.
         * <p>
         * Each call creates a separate server instance.
         * </p>
         * @param transport Streamable transport, e.g. the Servlet-based one.
         * @return Created server.
         * @throws IllegalArgumentException If the definition is rejected by the SDK,
         *                                  e.g. missing server information, duplicate tool names,
         *                                  or invalid tool names when validated strictly.
         */
        public McpSyncServer bind(McpStreamableServerTransportProvider transport) {
            return build(McpServer.sync(transport));
        }

        /**
         * Binds this definition to a single-session transport, creating a server.
         * <p>
         * Each call creates a separate server instance.
         * </p>
         * @param transport Single-session transport, e.g. the stdio one.
         * @return Created server.
         * @throws IllegalArgumentException If the definition is rejected by the SDK,
         *                                  e.g. missing server information, duplicate tool names,
         *                                  or invalid tool names when validated strictly.
         */
        public McpSyncServer bind(McpServerTransportProvider transport) {
            return build(McpServer.sync(transport));
        }

        /**
         * Applies this definition to a server specification and builds the server.
         * <p>
         * Components that are {@code null} are not applied, keeping the defaults of the SDK.
         * </p>
         * @param specification Server specification, already bound to a transport.
         * @return Created server.
         */
        private McpSyncServer build(McpServer.SyncSpecification<?> specification) {
            specification.serverInfo(serverInfo);
            if (instructions!=null) {
                specification.instructions(instructions);
            }
            if (capabilities!=null) {
                specification.capabilities(capabilities);
            }
            // Must precede the tools, since tool names are validated as the tools are added.
            if (strictToolNameValidation!=null) {
                specification.strictToolNameValidation(strictToolNameValidation);
            }
            specification.tools(tools);
            specification.resources(resources);
            specification.resourceTemplates(resourceTemplates);
            specification.prompts(prompts);
            specification.completions(completions);
            specification.rootsChangeHandlers(rootsChangeHandlers);
            toolFilters.forEach(specification::addToolFilter);
            if (jsonMapper!=null) {
                specification.jsonMapper(jsonMapper);
            }
            if (jsonSchemaValidator!=null) {
                specification.jsonSchemaValidator(jsonSchemaValidator);
            }
            if (validateToolInputs!=null) {
                specification.validateToolInputs(validateToolInputs);
            }
            if (requestTimeout!=null) {
                specification.requestTimeout(requestTimeout);
            }
            if (uriTemplateManagerFactory!=null) {
                specification.uriTemplateManagerFactory(uriTemplateManagerFactory);
            }
            if (immediateExecution!=null) {
                specification.immediateExecution(immediateExecution);
            }
            return specification.build();
        }
    }
}
