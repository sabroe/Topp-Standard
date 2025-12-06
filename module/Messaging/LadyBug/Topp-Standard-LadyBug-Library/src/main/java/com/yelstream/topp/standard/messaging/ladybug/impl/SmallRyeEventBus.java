package com.yelstream.topp.standard.messaging.ladybug.impl;

@ApplicationScoped
public class SmallRyeEventBus implements EventBus {
    @Inject @Channel("out-channel") Emitter<String> emitter;  // For sending

    @Override
    public FluentPublisher publish() {
        return new SmallRyePublisher(emitter);
    }

    // For subscribe: Use @Incoming in a bean, or programmatic MessageProcessor
    // Bridge to Vert.x via Quarkus integration
}