package com.yelstream.topp.standard.messaging.ladybug;

import io.smallrye.mutiny.Multi;

import java.util.function.Consumer;

public interface Receiver<A,M,R extends Receiver<A,M,R>> {

    R from(A address);

    R handler(Consumer<M> handler);

    /**
     *
     */
    Multi<M> start();


/*

Broker broker = new DefaultBroker();
broker.produce()
    .to("topic")
    .message("Hello!")
    .onSend(stage -> stage.thenAccept(v -> System.out.println("Sent")))
    .send()
    .subscribe().with(System.out::println);

broker.consume()
    .from("topic")
    .handler(msg -> System.out.println("Received: " + msg))
    .start()
    .subscribe().with(System.out::println);

*/
}
