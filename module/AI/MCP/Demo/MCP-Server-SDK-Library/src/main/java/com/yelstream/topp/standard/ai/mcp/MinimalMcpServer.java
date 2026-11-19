package com.yelstream.topp.standard.ai.mcp;

import com.yelstream.topp.standard.javalin.Javalins;
import com.yelstream.topp.standard.mcp.server.McpSyncServers;
import com.yelstream.topp.standard.mcp.spec.McpSchemas;
import io.javalin.Javalin;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.concurrent.CompletableFuture;

public class MinimalMcpServer {
    private static final String MCP_ENDPOINT = "/mcp";
    public static void main(String[] args) {
        // 1. Definition of the MCP server; independent of any transport.
        McpSyncServers.Definition definition = McpSyncServers.Definition.builder()
                .serverInfo(McpSchema.Implementation.builder("Minimal MCP Server", "0.1.0").build())
                .tool(McpSchemas.addNumbersSpecification())
                .tool(McpSchemas.greetPersonSpecification())
                .build();

        // 2. Streamable-HTTP transport; it is a jakarta.servlet.http.HttpServlet.
        HttpServletStreamableServerTransportProvider transport =
                HttpServletStreamableServerTransportProvider.builder()
                        .mcpEndpoint(MCP_ENDPOINT)
                        .build();

        // 3. MCP server, created by binding the definition to the transport.
        McpSyncServer mcpServer = definition.bind(transport);

        // 4. Javalin: mount the servlet and start the server.
        Javalin app = Javalins.create(transport, MCP_ENDPOINT);
        CompletableFuture<Void> running =
                Javalins.stopOnShutdown(Javalins.start(app, 8080), mcpServer::closeGracefully);

        System.out.printf("Minimal MCP server started at http://localhost:%d%s%n", app.port(), MCP_ENDPOINT);
        running.join();
    }
}
