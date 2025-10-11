package com.yelstream.topp.standard.messaging.ladybug;

public interface Consumer<A,M,S extends Consumer<A,M,S>> {

    S from(A address);

    S handler(java.util.function.Consumer<M> handler);

    void start();

    Consumer from(String address);
    Consumer handler(Consumer<Object> handler); // Lambda for message processing
    Multi<Object> start(); // Returns Multi for message stream


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
