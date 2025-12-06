package com.yelstream.topp.standard.messaging.ladybug;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/*
// Usage (injected in a bean)
producer.publishAsync("Hello CDI Event!");
 */

@ApplicationScoped
class EventProducer {
    @Inject
    private Event<MyEvent> event;  // Injected Event<T>

    public void publishSync(String data) {
        event.fire(new MyEvent(data));  // Sync pub-sub
    }

    public void publishAsync(String data) {
        event.<MyEvent>fireAsync(new MyEvent(data));  // Async via CompletableFuture
    }
}

