package com.yelstream.topp.standard.messaging.ladybug.impl.zeromq;

class ZeroMqPublisher implements FluentPublisher {
    private final ZMQ.Socket socket;
    private final Mode mode;

    @Override
    public void send() {
        String json = new JSONObject().put("data", payload).toString();
        if (mode == Mode.SYNC) {
            socket.send(json); // Blocks
        } else {
            CompletableFuture.runAsync(() -> socket.send(json)); // Non-blocking
        }
    }
}