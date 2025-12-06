package com.yelstream.topp.standard.messaging.ladybug;

import java.util.function.BiConsumer;

public interface StreamConsumer {

    void consumeStream(String streamId, BiConsumer<Long, Object> handler);

}
