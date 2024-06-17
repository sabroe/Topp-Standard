package com.yelstream.topp.standard.log.slf4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class LoggingDecorator {  //TO-DO: Remove!
    private static final Logger log = LoggerFactory.getLogger(LoggingDecorator.class);
    private static final RateLimiter rateLimiter;

    static {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(1))
                .limitRefreshPeriod(Duration.ofSeconds(5))
                .limitForPeriod(1)
                .build();
        rateLimiter = RateLimiter.of("logRateLimiter", config);
    }

    public static void logWithRateLimiter(Consumer<LoggingEventBuilder> loggingFunction) {
        LoggingEventBuilder builder = log.atDebug();

        if (builder != NOPLoggingEventBuilder.singleton() /*&& RateLimiter.isCallPermitted(rateLimiter)*/) {
            loggingFunction.accept(builder);
        }
    }

    public static void main(String[] args) {
        // Example usage
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            logWithRateLimiter(builder -> builder.log("Logging event number: {}", finalI));
            try {
                TimeUnit.SECONDS.sleep(1); // Sleep for demonstration purposes
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
