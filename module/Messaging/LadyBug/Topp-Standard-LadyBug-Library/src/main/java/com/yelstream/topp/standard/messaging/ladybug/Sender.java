package com.yelstream.topp.standard.messaging.ladybug;

public interface Producer<A,M,P extends Producer<A,M,P>> {
    /**
     *
     */
    P to(A address);

    /**
     *
     */
    P message(M message);

    /**
     *
     */
    void send();


    Producer to(String address);
    Producer message(Object msg);
    Uni<Void> send(); // Returns Uni for async completion
    Producer onSend(Consumer<CompletionStage<Void>> callback); // Lambda for send callback

}
