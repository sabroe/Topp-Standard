/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.time.view;

import com.yelstream.topp.standard.time.format.TimeFormat;
import com.yelstream.topp.standard.time.format.TimeParse;
import com.yelstream.topp.standard.time.policy.StandardNullPolicy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Fluent view of absolute time.
 * <p>
 *     Internally, this is based on an {@code Instant}.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "ofNullable", access = AccessLevel.PUBLIC)
public final class Time {
    /**
     * Absolute time.
     */
    private final Instant instant;

    public static Time of(Instant instant) {
        Objects.requireNonNull(instant);
        return new Time(instant);
    }

    public Time map(UnaryOperator<Instant> operator) {
        return new Time(operator.apply(instant));
    }

    public ZonedTime at(ZoneId zone) {
        return ZonedTime.of(instant.atZone(zone));
    }

    public ZonedTime atZoneId(ZoneId zone) {
        return ZonedTime.of(instant.atZone(zone));
    }

    public ZonedTime atZoneUTC() {
        return atZoneId(ZoneOffset.UTC);
    }

    public ZonedTime atZoneSystem() {
        return atZoneId(ZoneId.systemDefault());
    }

    public Instant toInstant() {
        return instant;
    }

    public Date toDate() {
        return Date.from(instant);
    }

    public static TimeCreate create() {
        return TimeCreate.of();
    }

    public TimeMap map() {
        return TimeMap.of(this, StandardNullPolicy.Strict.getPolicy());
    }

    public TimePolicy policy() {
        return TimePolicy.of(this, StandardNullPolicy.Strict.getPolicy());
    }

    public TimeAt at() {
        return TimeAt.of(this);
    }

    public TimeTo to() {
        return TimeTo.of(this);
    }

    public ZonedTime toZonedTime(ZoneId zone) {
        return ZonedTime.of(instant.atZone(zone));
    }

    /*
time.to().format(format)
Time.create().parse(text, parser)

Keep TimeFormat and TimeParse as independent strategy types, and let TimeTo / TimeCreate delegate to them — that gives you both architectural purity and fluent usability.

Basic usage:
Time time = Time.of(Instant.now());
String s = time.to().format(TimeFormats.ISO);
Time parsed = Time.create().parse(s, TimeParsers.IS

Custom formatter (this is where DateTimeFormatter comes in):
DateTimeFormatter formatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                     .withZone(ZoneOffset.UTC);
TimeFormat format = TimeFormats.of(formatter)

Use:
String s = time.to().format(format);

Parsing:
TimeParse parser = TimeParsers.of(formatter);
Time t = Time.create().parse("2026-04-17 12:30:00", parser);

One-liner usage (no intermediate variables):
String s = time.to().format(
    TimeFormats.of(DateTimeFormatter.ISO_INSTANT)
);
Time t = Time.create().parse(
    s,
    TimeParsers.of(DateTimeFormatter.ISO_INSTANT)
)

Logging / serialization example:
TimeFormat logFormat = TimeFormats.ISO;
log.info("time={}", logFormat.format(time));


Protocol / round-trip example:
TimeCodec codec = TimeCodecs.ISO;
String wire = codec.format(time.toInstant());
Time restored = Time.of(codec.parse(wire));


Advanced: multiple formats:
TimeFormat shortFormat =
    TimeFormats.of(DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneOffset.UTC));
TimeFormat longFormat =
    TimeFormats.of(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC));
time.to().format(shortFormat);
time.to().format(longFormat);

Clean mental Model:
Time                = domain
Instant             = internal representation
DateTimeFormatter   = low-level engine
TimeFormat          = formatting strategy
TimeParse           = parsing strategy


     */


    public static Time strict(CharSequence text, TimeParse parser) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(parser, "parser");
        return Time.of(parser.parse(text));
    }


    public static Optional<Time> parseNullable(CharSequence text, TimeParse parser) {
        Objects.requireNonNull(parser, "parser");
        return Optional.ofNullable(text)
                .map(parser::parse)
                .map(Time::of);
    }

    public static Time parseNullAware(CharSequence text, TimeParse parser) {
        Objects.requireNonNull(parser, "parser");
        if (text == null) {
            return null;
        }
        return Time.of(parser.parse(text));
    }


    public static String format(Time time, TimeFormat format) {
        Objects.requireNonNull(time);
        return format.format(time.toInstant());
    }

    public static Optional<String> formatNullable(Time time, TimeFormat format) {
        return Optional.ofNullable(time)
                .map(Time::toInstant)
                .map(format::format);
    }


    public static String formatNullAware(Time time, TimeFormat format) {
        Instant instant = (time != null ? time.toInstant() : null);
        return format.format(instant); // must accept null if you allow this
    }

    /*
    Adapters to your domain:
    time.to().format(format) → format.format(time.toInstant())
    time.to().format(format) → format.format(time.toInstant())
     */

/*
Final architecture:

                TemporalFormat<T>
                TemporalParse<T>
                       ↑
        ┌──────────────┴──────────────┐
        │                             │
   Instant-based                 ZonedDateTime-based
   (Time)                        (ZonedTime)

        │                             │
   TimeFormat                    ZonedTimeFormat
   TimeParse                     ZonedTimeParse

        │                             │
   TimeTo / TimeCreate         ZonedTimeTo / ZonedTimeCreate
 */

}
