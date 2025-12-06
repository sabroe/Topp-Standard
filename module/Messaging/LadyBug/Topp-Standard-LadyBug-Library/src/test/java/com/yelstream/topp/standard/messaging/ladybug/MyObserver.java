package com.yelstream.topp.standard.messaging.ladybug;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

@ApplicationScoped
class MyObserver {
    public void handleSync(@Observes MyEvent event) {  // Sync observer
        System.out.println("Sync received: " + event.getData());
    }

    public void handleAsync(@ObservesAsync MyEvent event) {  // Async observer
        System.out.println("Async received: " + event.getData());
    }
}