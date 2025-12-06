package com.yelstream.topp.standard.messaging.ladybug.impl.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class VertxEventBus implements EventBus {
    private final EventBus vertxBus;

    public VertxEventBus(Vertx vertx) {
        this.vertxBus = vertx.eventBus();
    }

    @Override
    public FluentPublisher publish() {
        return new VertxPublisher(vertxBus);
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

class VertxPublisher implements FluentPublisher {
    private final EventBus vertxBus;
    private String address;
    private Object payload;

    VertxPublisher(EventBus vertxBus) {
        this.vertxBus = vertxBus;
    }

    @Override
    public FluentPublisher to(String address) {
        this.address = address;
        return this;
    }

    @Override
    public FluentPublisher message(Object payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public void send() {
        if (address == null || payload == null) {
            throw new IllegalStateException("Address and message must be set");
        }
        vertxBus.publish(address, payload instanceof String ? payload : new JsonObject().put("data", payload));
    }
}

class VertxSubscriber implements FluentSubscriber {
    private final EventBus vertxBus;
    private String address;
    private Consumer<Object> handler;

    VertxSubscriber(EventBus vertxBus) {
        this.vertxBus = vertxBus;
    }

    @Override
    public FluentSubscriber from(String address) {
        this.address = address;
        return this;
    }

    @Override
    public FluentSubscriber handler(Consumer<Object> handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public void start() {
        if (address == null || handler == null) {
            throw new IllegalStateException("Address and handler must be set");
        }
        vertxBus.consumer(address, msg -> handler.accept(msg.body()));
    }
}
