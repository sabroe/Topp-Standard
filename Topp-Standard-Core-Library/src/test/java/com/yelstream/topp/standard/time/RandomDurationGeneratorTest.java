package com.yelstream.topp.standard.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.random.RandomGenerator;

/**
 * Test of {@link RandomDurationGenerator}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-26
 */
@Slf4j
public class RandomDurationGeneratorTest {

    /**
     * Tests {@link RandomDurationGenerator#of(RandomGenerator,Duration,Duration)}
     */
    @Test
    void noArgsConstructor() {
        {
            RandomDurationGenerator g=RandomDurationGenerator.of(new SecureRandom(),Duration.ofSeconds(1L),Duration.ofSeconds(2L));

            Assertions.assertNotNull(g);
            Duration d=g.nextDuration();
            Assertions.assertNotNull(d);
            Assertions.assertTrue(Duration.ofSeconds(1L).compareTo(d)<=0);
            Assertions.assertTrue(d.compareTo(Duration.ofSeconds(2L))<=0);
        }
        {
            Assertions.assertThrows(IllegalArgumentException.class,()-> {
                RandomDurationGenerator g = RandomDurationGenerator.of(new SecureRandom(), Duration.ofSeconds(2L), Duration.ofSeconds(1L));
            });
        }
        {
            RandomDurationGenerator g=RandomDurationGenerator.of(new SecureRandom(),Duration.of(1000L*365L,ChronoUnit.DAYS),Duration.of(2000L*365L,ChronoUnit.DAYS));

            Assertions.assertNotNull(g);
            Duration d=g.nextDuration();
            Assertions.assertNotNull(d);
            Assertions.assertTrue(Duration.of(1000L*365L,ChronoUnit.DAYS).compareTo(d)<=0);
            Assertions.assertTrue(d.compareTo(Duration.of(2000L*365L,ChronoUnit.DAYS))<=0);
        }
    }
}
