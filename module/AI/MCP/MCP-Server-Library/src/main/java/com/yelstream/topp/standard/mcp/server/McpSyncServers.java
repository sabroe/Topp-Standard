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

/**
 * Utilities addressing instances of {@link McpSyncServer}.
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
     * @param serverInfo Server information; name and version.
     * @param instructions Instructions for clients on how to use the server.
     *                     This may be {@code null}.
     * @param capabilities Server capabilities.
     *                     This may be {@code null}, which keeps the default of the SDK.
     * @param tools Tools.
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
         * Binds this definition to a streamable transport, creating a server.
         * @param transport Streamable transport, e.g. the Servlet-based one.
         * @return Created server.
         */
        public McpSyncServer bind(McpStreamableServerTransportProvider transport) {
            return build(McpServer.sync(transport));
        }

        /**
         * Binds this definition to a single-session transport, creating a server.
         * @param transport Single-session transport, e.g. the stdio one.
         * @return Created server.
         */
        public McpSyncServer bind(McpServerTransportProvider transport) {
            return build(McpServer.sync(transport));
        }

        /**
         * Applies this definition to a server specification and builds the server.
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
            if (strictToolNameValidation!=null) {
                specification.strictToolNameValidation(strictToolNameValidation);
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
