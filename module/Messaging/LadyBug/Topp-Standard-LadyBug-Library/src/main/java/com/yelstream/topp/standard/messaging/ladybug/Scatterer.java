package com.yelstream.topp.standard.messaging.ladybug;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Scatterer {

    CompletableFuture<List<Object>> scatter(String topic, Object request);

}
