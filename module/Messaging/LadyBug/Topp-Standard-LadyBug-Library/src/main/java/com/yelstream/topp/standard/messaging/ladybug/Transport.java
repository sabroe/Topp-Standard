package com.yelstream.topp.standard.messaging.ladybug;

public interface Broker<A,M,P extends Producer<A,M,P>,S extends Consumer<A,M,S>,E extends Broker<A,M,P,S,E>> {

    P produce();

    S consume();

    void close();


    Producer produce();
    Consumer consume();
    void close();



}
