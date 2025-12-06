package com.yelstream.topp.standard.messaging.ladybug;

import java.util.concurrent.CompletionStage;

public interface Requester<A,M,N,R extends Requester<A,M,N,R>> extends Sender<A,M,N,R> {






    /**
     *
     */
    CompletionStage<N> request();



}
