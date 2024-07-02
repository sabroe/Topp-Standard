package com.yelstream.topp.standard.net.tool;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@UtilityClass
public class NetTools {

    @lombok.Builder(builderClassName = "Builder")
    @AllArgsConstructor
    public static class SocketConnectivity {
        private final SocketAddress socketAddress;
        private final Duration connectTimeout;
        private final Exception exception;
    }

    public static void testConnectToSocket(SocketAddress socketAddress,
                                           Duration connectTimeout) throws IOException {
        try (Socket socket=new Socket()) {
            socket.connect(socketAddress,(int)connectTimeout.toMillis());
        }
    }

    public static SocketConnectivity canConnectToSocket(SocketAddress socketAddress,
                                                        Duration connectTimeout) {
        SocketConnectivity.Builder builder=SocketConnectivity.builder();
        builder.socketAddress(socketAddress);
        builder.connectTimeout(connectTimeout);
        try {
            testConnectToSocket(socketAddress,connectTimeout);
        } catch (IOException ex) {
            builder.exception(ex);
        }
        return builder.build();
    }

    public static CompletableFuture<SocketConnectivity> canConnectToSocketAsync(SocketAddress socketAddress,
                                                                                Duration connectTimeout,
                                                                                Executor executor) {
        return CompletableFuture.supplyAsync(()->canConnectToSocket(socketAddress,connectTimeout),executor);
    }






}
