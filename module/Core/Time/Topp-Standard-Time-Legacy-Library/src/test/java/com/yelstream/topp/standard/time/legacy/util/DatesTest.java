package com.yelstream.topp.standard.time.legacy.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;

/**
 * Test of {@link Dates}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-12
 */
@Slf4j
public class DatesTest {


    @Test
    void get_shouldConvertTemporalToDate() {
        Instant now = Instant.now();
        Date result = Dates.get(() -> now);
        Assertions.assertEquals(Date.from(now), result);
    }

    @Test
    void get_shouldAcceptOtherTemporalAccessor() {
        ZonedDateTime zdt = ZonedDateTime.now();
        Date result = Dates.get(() -> zdt);
        Assertions.assertEquals(Date.from(zdt.toInstant()), result);
    }

    @Test
    void get_shouldThrowOnNullOperation() {
        Assertions.assertThrows(NullPointerException.class, () -> Dates.get(null));
    }

    @Test
    void consume_shouldPassInstantToOperation() {
        Instant now = Instant.now();
        Date date = Date.from(now);
        AtomicReference<Instant> captured = new AtomicReference<>();
        Dates.consume(date, captured::set);
        Assertions.assertEquals(now.truncatedTo(ChronoUnit.MILLIS), captured.get());
    }

    @Test
    void consume_shouldThrowOnNullDate() {
        Assertions.assertThrows(NullPointerException.class,() -> Dates.consume(null, instant -> {}));
    }

    @Test
    void consume_shouldThrowOnNullOperation() {
        Date date = new Date();
        Assertions.assertThrows(NullPointerException.class,() -> Dates.consume(date, null));
    }

    @Test
    void map_shouldTransformDate() {
        Instant now = Instant.now();
        Date date = Date.from(now);
        Date result = Dates.map(date, instant -> instant.plusSeconds(60));
        Assertions.assertEquals(Date.from(now.plusSeconds(60)), result);
    }

    @Test
    void map_shouldAcceptDifferentTemporalReturnType() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Date date = Date.from(now);
        Date result = Dates.map(date, instant ->instant.plusSeconds(120));
        Assertions.assertEquals(Date.from(now.plusSeconds(120)), result);
    }

    @Test
    void map_shouldAcceptZonedDateTime() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Date date = Date.from(now);
        Date result = Dates.map(date, instant ->ZonedDateTime.ofInstant(instant.plusSeconds(120),ZoneOffset.UTC).toInstant());
        Assertions.assertEquals(Date.from(now.plusSeconds(120)), result);
    }

    @Test
    void map_shouldThrowOnNullDate() {
        Assertions.assertThrows(NullPointerException.class,() -> Dates.map(null, instant -> instant));
    }

    @Test
    void map_shouldThrowOnNullOperation() {
        Date date = new Date();
        Assertions.assertThrows(NullPointerException.class, () -> Dates.map(date, null));
    }

    @Test
    void from_shouldExtractValue() {
        Instant now = Instant.now();
        Date date = Date.from(now);
        long epochMilli = Dates.from(date, Instant::toEpochMilli);
        Assertions.assertEquals(now.toEpochMilli(), epochMilli);
    }

    @Test
    void from_shouldAllowCustomExtraction() {
        Instant now = Instant.now();
        Date date = Date.from(now);
        int year = Dates.from(date, instant -> ZonedDateTime.ofInstant(instant, ZoneOffset.UTC).getYear());
        Assertions.assertEquals(ZonedDateTime.ofInstant(now, ZoneOffset.UTC).getYear(),year);
    }

    @Test
    void from_shouldThrowOnNullDate() {
        Assertions.assertThrows(NullPointerException.class,() -> Dates.from(null, instant -> instant));
    }

    @Test
    void from_shouldThrowOnNullOperation() {
        Date date = new Date();
        Assertions.assertThrows(NullPointerException.class,() -> Dates.from(date, null));
    }

    @Test
    void map_shouldPropagateExceptionFromOperation() {
        Date date = new Date();
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> Dates.map(date, instant -> {
                    throw new RuntimeException("boom");
                }));

        Assertions.assertEquals("boom", ex.getMessage());
    }

    @Test
    void consumeNullAware_shouldPassInstantWhenDatePresent() {
        Date now = new Date();
        Instant expected = now.toInstant();
        AtomicReference<Instant> captured = new AtomicReference<>();
        Dates.consumeNullAware(now, captured::set);
        Assertions.assertEquals(expected, captured.get());
    }

    @Test
    void consumeNullAware_shouldPassNullWhenDateIsNull() {
        AtomicReference<Instant> captured = new AtomicReference<>(Instant.EPOCH);
        Dates.consumeNullAware(null, captured::set);
        Assertions.assertNull(captured.get());
    }

    @Test
    void consumeNullAware_shouldAlwaysInvokeOperation() {
        AtomicBoolean called = new AtomicBoolean(false);
        Dates.consumeNullAware(null, i -> called.set(true));
        Assertions.assertTrue(called.get());
    }

    @Test
    void mapNullAware_shouldTransformWhenDatePresent() {
        Date now = new Date();
        Date result = Dates.mapNullAware(now, instant -> instant.plusSeconds(60));
        Assertions.assertEquals(Date.from(now.toInstant().plusSeconds(60)), result);
    }

    @Test
    void mapNullAware_shouldCallOperationWithNullWhenDateIsNull() {
        AtomicReference<Instant> captured = new AtomicReference<>();
        Dates.mapNullAware(null, instant -> {
            captured.set(instant);
            return Instant.EPOCH;
        });
        Assertions.assertNull(captured.get());
    }

    @Test
    void mapNullAware_shouldReturnNullWhenOperationReturnsNull() {
        Date now = new Date();
        Date result = Dates.mapNullAware(now, instant -> null);
        Assertions.assertNull(result);
    }

    @Test
    void mapNullAware_shouldHandleNullInputAndProduceValue() {
        Date result = Dates.mapNullAware(null, instant -> {
            Assertions.assertNull(instant);
            return Instant.EPOCH;
        });
        Assertions.assertEquals(Date.from(Instant.EPOCH), result);
    }

    @Test
    void mapNullAware_shouldThrowOnNullOperation() {
        Assertions.assertThrows(NullPointerException.class, () -> Dates.mapNullAware(new Date(), null));
    }

    @Test
    void fromNullAware_shouldExtractValueWhenDatePresent() {
        Date now = new Date();
        long result = Dates.fromNullAware(now, Instant::toEpochMilli);
        Assertions.assertEquals(now.toInstant().toEpochMilli(), result);
    }

    @Test
    void fromNullAware_shouldCallOperationWithNullWhenDateIsNull() {
        AtomicReference<Instant> captured = new AtomicReference<>();
        Dates.fromNullAware(null, instant -> {
            captured.set(instant);
            return 42;
        });
        Assertions.assertNull(captured.get());
    }

    @Test
    void fromNullAware_shouldReturnNullWhenOperationReturnsNull() {
        Date now = new Date();
        String result = Dates.fromNullAware(now, instant -> null);
        Assertions.assertNull(result);
    }

    @Test
    void fromNullAware_shouldHandleNullInputAndReturnValue() {
        String result = Dates.fromNullAware(null, instant -> {
            Assertions.assertNull(instant);
            return "fallback";
        });
        Assertions.assertEquals("fallback", result);
    }
}
