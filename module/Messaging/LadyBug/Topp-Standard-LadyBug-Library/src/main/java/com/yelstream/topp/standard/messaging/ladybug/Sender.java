package com.yelstream.topp.standard.messaging.ladybug;

import io.smallrye.mutiny.Uni;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

public interface Sender<A,M,N,S extends Sender<A,M,N,S>> {
    /**
     *
     */
    S to(A address);

    /**
     *
     */
    S message(M message);

    /**
     *
     */
//    S onSend(Consumer<CompletionStage<Void>> callback);

    /**
     *
     */
    CompletionStage<N> send();
}
