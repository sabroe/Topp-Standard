package com.yelstream.topp.standard.messaging.ladybug.impl.vertx;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // Async mode (non-blocking)
        EventBus asyncBus = EventBus.builder()
                .backend(BackendType.VERTX)
                .config(vertx)
                .mode(Mode.ASYNC)
                .build();

        asyncBus.subscribe()
                .from("greeting")
                .handler(msg -> System.out.println("Received (async): " + msg))
                .start();

        asyncBus.publish()
                .to("greeting")
                .message("Hello Async")
                .send();

        // Sync mode (blocking)
        EventBus syncBus = EventBus.builder()
                .backend(BackendType.VERTX)
                .config(vertx)
                .mode(Mode.SYNC)
                .build();

        syncBus.subscribe()
                .from("greeting")
                .handler(msg -> System.out.println("Received (sync): " + msg))
                .start();

        syncBus.publish()
                .to("greeting")
                .message("Hello Sync")
                .send();
    }

    public static void main(String[] args) {
        LadyBug bus = LadyBug.builder()
                .backend(BackendType.VERTX)
                .config(vertx)
                .build();
        bus.publish().to("greeting").message("Hello").send();
        bus.subscribe().from("greeting").handler(msg -> System.out.println("Received: " + msg)).start();
    }
}