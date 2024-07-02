package com.yelstream.topp.standard.net;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.Duration;

public class Sockets {


    @FunctionalInterface
    public interface ConnectOperation {  //SocketOperation?
        void bind(Socket socket) throws IOException;
    }


    public static void testConnectToSocket(SocketAddress socketAddress,
                                           Duration connectTimeout) throws IOException {
        try (Socket socket=new Socket()) {
            socket.connect(socketAddress,(int)connectTimeout.toMillis());
        }
    }

}
