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

import com.yelstream.topp.standard.time.codec.TemporalCodec;
import com.yelstream.topp.standard.time.codec.TimeCodec;
import com.yelstream.topp.standard.time.format.TemporalParse;
import com.yelstream.topp.standard.time.format.TimeParse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Objects;

/**
 * Capability view on creating {@link Time} instances.
 * <p>
 *     This is a fluent helper.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class TimeCreate {  //TO-DO: Rename to "TimeFrom", as opposite to "TimeTo"?

    public Time from(Instant instant) {
        Objects.requireNonNull(instant,"instant");
        return Time.of(instant);
    }

    public Time from(InstantSource instantSource) {
        Objects.requireNonNull(instantSource, "instantSource");
        return Time.of(instantSource.instant());
    }

    public Time from(Temporal temporal) {
        Objects.requireNonNull(temporal, "temporal");
        return Time.of(Instant.from(temporal));
    }

    public Time from(Date date) {
        Objects.requireNonNull(date, "date");
        return Time.of(date.toInstant());
    }

    public Time from(Time time) {
        Objects.requireNonNull(time,"time");
        return Time.of(time.toInstant());
    }

    public Time now() {
        return Time.of(Instant.now());
    }

    public Time now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return Time.of(clock.instant());
    }

    public Time parse(CharSequence text,
                      DateTimeFormatter formatter) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(formatter);
        Instant instant = formatter.parse(text, Instant::from);
        return Time.of(instant);
    }

    public Time parse(CharSequence text,
                      TimeParse parse) {
        Objects.requireNonNull(parse, "parse");
        return Time.of(parse.parse(text));
    }

    public Time parse(CharSequence text,
                      TemporalParse<Instant> parse) {
        Objects.requireNonNull(parse, "parse");
        return Time.of(parse.parse(text));
    }

    public Time parse(CharSequence text,
                      TimeCodec codec) {
        Objects.requireNonNull(codec, "codec");
        return Time.of(codec.parse(text));
    }

    public Time parse(CharSequence text,
                      TemporalCodec<Instant> codec) {
        Objects.requireNonNull(codec, "codec");
        return Time.of(codec.parse(text));
    }
}
