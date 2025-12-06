package com.yelstream.topp.standard.messaging.ladybug.impl.vertx;

class VertxPublisher implements FluentPublisher {
    private final EventBus vertxBus;
    private final Mode mode;
    private String address;
    private Object payload;

    VertxPublisher(EventBus vertxBus, Mode mode) {
        this.vertxBus = vertxBus;
        this.mode = mode;
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
        Object message = payload instanceof String ? payload : new JsonObject().put("data", payload);
        if (mode == Mode.SYNC) {
            // Simulate sync by using request-reply with blocking wait
            CompletableFuture<Object> future = new CompletableFuture<>();
            vertxBus.request(address, message, ar -> {
                if (ar.succeeded()) {
                    future.complete(ar.result().body());
                } else {
                    future.completeExceptionally(ar.cause());
                }
            });
            try {
                future.get(5, TimeUnit.SECONDS); // Block with timeout
            } catch (Exception e) {
                throw new RuntimeException("Send failed", e);
            }
        } else {
            // Async: fire and forget
            vertxBus.publish(address, message);
        }
    }
}
