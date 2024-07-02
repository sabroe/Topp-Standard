package com.yelstream.topp.standard.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class ServerSockets {



    @FunctionalInterface
    public interface BindOperation {
        void bind(ServerSocket serverSocket) throws IOException;
    }

    public static void testBind(BindOperation bind) throws IOException {
        try (ServerSocket serverSocket=new ServerSocket()) {
            bind.bind(serverSocket);
        }
    }

    public static void testBind(int port) throws IOException {
        testBind(serverSocket -> serverSocket.bind(new InetSocketAddress("localhost",port)));
    }





}
