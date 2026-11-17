package com.yelstream.topp.standard.ai.mcp;

import com.yelstream.topp.standard.mcp.spec.McpSchemas;
import io.javalin.Javalin;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import org.eclipse.jetty.ee10.servlet.ServletHolder;

public class MinimalMcpServer {
    private static final String MCP_ENDPOINT = "/mcp";
    public static void main(String[] args) {
        // 1. Streamable-HTTP transport; it is a jakarta.servlet.http.HttpServlet.
        HttpServletStreamableServerTransportProvider transport =
                HttpServletStreamableServerTransportProvider.builder()
                        .mcpEndpoint(MCP_ENDPOINT)
                        .build();

        // 2. MCP server with tools, bound to the transport.
        McpSyncServer mcpServer = McpServer.sync(transport)
                .serverInfo("Minimal MCP Server", "0.1.0")
                .toolCall(McpSchemas.addNumbersTool(), McpSchemas::addNumbers)
                .toolCall(McpSchemas.greetPersonTool(), McpSchemas::greetPerson)
                .build();

        // 3. Javalin 7: mount the servlet in Jetty, define routes in config.
        Javalin app = Javalin.create(config -> {
            config.jetty.modifyServletContextHandler(handler ->
                    handler.addServlet(new ServletHolder(transport), MCP_ENDPOINT));
            config.routes.get("/", ctx ->
                    ctx.result("MCP server running - connect clients to http://localhost:8080" + MCP_ENDPOINT));
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            mcpServer.closeGracefully();
            app.stop();
        }));

        app.start(8080);
        System.out.println("Minimal MCP server started at http://localhost:8080" + MCP_ENDPOINT);
    }
}
