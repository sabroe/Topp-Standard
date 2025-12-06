package com.yelstream.topp.standard.messaging.ladybug;

public interface StreamProducer {

    void stream(String streamId, Object event, long offset);

}
