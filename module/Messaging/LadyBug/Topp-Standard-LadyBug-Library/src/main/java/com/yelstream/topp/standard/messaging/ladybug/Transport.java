package com.yelstream.topp.standard.messaging.ladybug;

public interface Transport<A,M,S extends Sender<A,M,S>,R extends Receiver<A,M,R>,T extends Transport<A,M,S,R,T>> {

    P produce();

    S consume();

    void close();


    Sender produce();
    Receiver consume();
    void close();



}
