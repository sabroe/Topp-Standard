package com.yelstream.topp.standard.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.InstantSource;

/**
 * Test of {@link InstantSources}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-28
 */
@Slf4j
class InstantSourcesTest {
    /**
     * Test of {@link InstantSources#now()}.
     */
    @Test
    void now() {
        InstantSource x=InstantSources.now();
        Assertions.assertNotNull(x);
        Instant i1=x.instant();
        Instant i2=Instant.now();
        Duration d=Duration.between(i1,i2);
        Assertions.assertTrue(d.abs().compareTo(Duration.ofMillis(25L))<0);
    }

    /**
     * Test of {@link InstantSources#now(Clock)}.
     */
    @Test
    void nowClock() {
        Clock c=Clock.systemDefaultZone();
        InstantSource x=InstantSources.now(c);
        Assertions.assertNotNull(x);
        Instant i1=x.instant();
        Instant i2=Instant.now(c);
        Duration d=Duration.between(i1,i2);
        Assertions.assertTrue(d.abs().compareTo(Duration.ofMillis(25L))<0);
    }
}
