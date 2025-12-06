package com.yelstream.topp.standard.messaging.ladybug.impl;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class VertxEventBus implements EventBus {
    private final EventBus vertxBus;
    private final Mode mode;

    public VertxEventBus(Vertx vertx, Mode mode) {
        this.vertxBus = vertx.eventBus();
        this.mode = mode;
    }

    @Override
    public FluentPublisher publish() {
        return new VertxPublisher(vertxBus, mode);
    }

    @Override
    public FluentSubscriber subscribe() {
        return new VertxSubscriber(vertxBus);
    }

    @Override
    public void close() {
        // Vert.x instance typically managed externally
    }
}