package com.yelstream.topp.standard.log.slf4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterExample {  //TO-DO: Remove!

    private static AtomicInteger suppressedCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ZERO)
                .limitRefreshPeriod(Duration.ofSeconds(5))
                .limitForPeriod(1)
                .build();

        RateLimiter rateLimiter = RateLimiter.of("logRateLimiter", config);

        for (int i = 0; i < 10; i++) {
            if (rateLimiter.acquirePermission()) {
                logStatement();
                suppressedCounter.set(0);
            } else {
                suppressedCounter.incrementAndGet();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void logStatement() {
        int suppressed = suppressedCounter.get();
        System.out.println("Log statement executed at " + System.currentTimeMillis() + ". Suppressed executions: " + suppressed);
    }
}