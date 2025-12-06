package com.yelstream.topp.standard.messaging.ladybug;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

class MyEvent {
    private String data;
    public MyEvent(String data) { this.data = data; }
    public String getData() { return data; }
}