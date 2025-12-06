package com.yelstream.topp.standard.messaging.ladybug.impl.smallrye;

class SmallRyePublisher implements FluentPublisher {
    private final Emitter<String> emitter;
    private final Mode mode;

    @Override
    public void send() {
        String json = new JSONObject().put("data", payload).toString();
        CompletableFuture<Void> future = emitter.send(json);
        if (mode == Mode.SYNC) {
            future.join(); // Blocks
        }
    }
}