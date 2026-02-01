package com.yelstream.topp.standard.ai.mcp;

import io.javalin.Javalin;
/*
import io.modelcontextprotocol.sdk.Tool;
import io.modelcontextprotocol.sdk.ToolInvocation;
import io.modelcontextprotocol.sdk.ToolResult;
import io.modelcontextprotocol.sdk.ToolSpecification;
import io.modelcontextprotocol.sdk.transport.HttpMcpTransport;
import io.modelcontextprotocol.server.McpServer;
*/

public class MinimalMcpServer {

/*
    public static void main(String[] args) {
        // 1. Build the MCP server instance with tools
        McpServer mcpServer = McpServer.builder()
                .tool(new SimpleAddTool())
                .tool(new GreetingTool())
                .serverName("Minimal MCP Server JDK 21")
                .serverVersion("0.1.0")
                .build();

        // 2. Create the standard HTTP transport (JSON-RPC + SSE support)
        HttpMcpTransport transport = new HttpMcpTransport(mcpServer);

        // 3. Start Javalin (embedded Jetty) – very lightweight
        Javalin app = Javalin.create(config -> {
            config.jetty.modifyServer(server -> {
                // Optional: tune if needed (threads, etc.)
            });
        });

        // Mount MCP endpoints – common convention is /mcp or /sse + /messages
        // In 0.17.x the transport usually expects to handle multiple paths:
        app.before("/mcp/*", ctx -> transport.handle(ctx.req(), ctx.res()));  // raw handle

        // Alternative (more explicit – depending on exact transport API in 0.17.2):
        // app.get("/mcp", ctx -> transport.handleSse(ctx.req(), ctx.res()));
        // app.post("/mcp", ctx -> transport.handlePost(ctx.req(), ctx.res()));

        // But in practice the single before-filter + handle often works best

        app.get("/", ctx -> ctx.result("MCP server running – connect clients to http://localhost:8080/mcp"));

        // Start server
        int port = 8080;
        app.start(port);

        System.out.println("Minimal MCP server started at http://localhost:" + port + "/mcp");
        System.out.println("Connect with Claude Desktop, Cursor, or any MCP client.");
        System.out.println("Press Ctrl+C to stop.");
    }

    // ────────────────────────────────────────────────
    // Tool 1: Addition
    // ────────────────────────────────────────────────
    static class SimpleAddTool implements Tool {

        @Override
        public ToolSpecification getSpecification() {
            return ToolSpecification.builder()
                    .name("add_numbers")
                    .description("Adds two integers and returns the sum")
                    .parameter("a", "integer", "First number", true)
                    .parameter("b", "integer", "Second number", true)
                    .build();
        }

        @Override
        public ToolResult invoke(ToolInvocation invocation) {
            try {
                int a = invocation.getRequiredParameter("a", Integer.class);
                int b = invocation.getRequiredParameter("b", Integer.class);
                return ToolResult.success(a + b);
            } catch (Exception e) {
                return ToolResult.error("Invalid parameters: " + e.getMessage());
            }
        }
    }

    // ────────────────────────────────────────────────
    // Tool 2: Greeting
    // ────────────────────────────────────────────────
    static class GreetingTool implements Tool {

        @Override
        public ToolSpecification getSpecification() {
            return ToolSpecification.builder()
                    .name("greet_person")
                    .description("Gives a friendly greeting")
                    .parameter("name", "string", "Name to greet (optional)", false)
                    .build();
        }

        @Override
        public ToolResult invoke(ToolInvocation invocation) {
            String name = invocation.getParameter("name", String.class, "stranger");
            String message = "Hello, " + name + "! This is MCP server v0.17.2 running on JDK 21 with Javalin.";
            return ToolResult.success(message);
        }
    }
*/
}
