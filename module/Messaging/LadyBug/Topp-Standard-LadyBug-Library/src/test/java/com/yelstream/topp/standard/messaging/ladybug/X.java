package com.yelstream.topp.standard.messaging.ladybug;

public class X {

    public static void main(String[] args) {

        Transport bus = Transport.builder()
                .backend(BackendType.VERTX)
                .config(vertx)  // Pass Vert.x instance or config
                .build();

        bus.publish()
                .to("greeting")
                .message("Hello")
                .send();

        bus.subscribe()
                .from("greeting")
                .handler(msg -> System.out.println("Received: " + msg.body()))
                .start();

    }

}
