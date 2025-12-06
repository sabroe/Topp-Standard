package com.yelstream.topp.standard.messaging.ladybug.impl;

import com.yelstream.topp.standard.messaging.ladybug.Transport;
import com.yelstream.topp.standard.messaging.ladybug.FluentPublisher;
import com.yelstream.topp.standard.messaging.ladybug.FluentSubscriber;

public class ZeroMqEventBus implements Transport {
    private ZContext context;
    private ZMQ.Socket pubSocket;
    private ZMQ.Socket subSocket;

    public ZeroMqEventBus(String connectUrl) {
        context = new ZContext();
        pubSocket = context.createSocket(SocketType.PUSH);
        pubSocket.connect(connectUrl);
        subSocket = context.createSocket(SocketType.PULL);
        subSocket.bind("tcp://*:5556");  // Or use config
    }

    @Override
    public FluentPublisher publish() {
        return new ZeroMqPublisher(pubSocket);
    }

    @Override
    public FluentSubscriber subscribe() {
        return new ZeroMqSubscriber(subSocket);
    }

    // Inner classes for FluentPublisher/Subscriber with chaining, sending JSON strings
}
