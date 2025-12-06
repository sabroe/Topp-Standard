package com.yelstream.topp.standard.messaging.ladybug;

public interface Gatherer {

    void gather(String topic, String correlationId, java.util.function.Consumer<Object> responseHandler);

}
