package com.yelstream.topp.standard.text.test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        var begin = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0,100_000).forEach(i->executor.submit(()->{
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
        }
        var end=Instant.now();
        System.out.println(Duration.between(begin,end));
    }

}
