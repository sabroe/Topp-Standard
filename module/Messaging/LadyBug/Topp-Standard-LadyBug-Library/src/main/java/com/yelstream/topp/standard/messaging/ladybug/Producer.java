package com.yelstream.topp.standard.messaging.ladybug;

import java.util.concurrent.CompletionStage;

public interface Producer<A,M,P extends Producer<A,M,P>> extends Sender<A,M,Void,P> {

    /**
     *
     */
    CompletionStage<Void> produce();


}
