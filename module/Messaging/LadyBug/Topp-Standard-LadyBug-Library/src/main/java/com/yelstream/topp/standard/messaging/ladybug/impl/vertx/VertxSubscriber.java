package com.yelstream.topp.standard.messaging.ladybug.impl.vertx;

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
