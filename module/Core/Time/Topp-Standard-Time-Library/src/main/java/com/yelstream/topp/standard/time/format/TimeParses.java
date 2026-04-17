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

package com.yelstream.topp.standard.time.format;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public final class TimeParses {

    public static TimeParse of(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter);
        return text -> formatter.parse(text, Instant::from);
    }

    public static final TimeParse ISO =
            of(DateTimeFormatter.ISO_INSTANT);


    //Strict (requires zone)
    public static TimeParse strict(DateTimeFormatter formatter) {
        return text -> formatter.parse(text, Instant::from);
    }

    //With default zone (this is the important one)
    public static TimeParse withZone(DateTimeFormatter formatter, ZoneId zone) {
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(zone);

        return text -> {
            TemporalAccessor parsed = formatter.parse(text);

            // Try direct Instant
            if (parsed.isSupported(ChronoField.INSTANT_SECONDS)) {
                return Instant.from(parsed);
            }

            // Fall back to LocalDateTime → apply zone
            LocalDateTime ldt = LocalDateTime.from(parsed);
            return ldt.atZone(zone).toInstant();
        };
    }

    //Smart fallback (very nice)
    public static TimeParse smart(DateTimeFormatter formatter, ZoneId defaultZone) {
        return text -> {
            TemporalAccessor parsed = formatter.parse(text);

            if (parsed.isSupported(ChronoField.INSTANT_SECONDS)) {
                return Instant.from(parsed);
            }

            if (parsed.isSupported(ChronoField.OFFSET_SECONDS)) {
                return OffsetDateTime.from(parsed).toInstant();
            }

            return LocalDateTime.from(parsed)
                    .atZone(defaultZone)
                    .toInstant();
        };
    }





/*
    Usage:
TimeParse p = TimeParsers.ISO;
Time t = p.parseTime("2026-04-17T12:00:00Z");
 */

/*
    Strict usage:
  Time t = Time.create().parse(
    "2026-04-17T12:00:00Z",
    TimeParsers.strict(DateTimeFormatter.ISO_INSTANT)
);

Local date-time with zone:
Time t = Time.create().parse(
    input,
    TimeParsers.smart(formatter, ZoneOffset.UTC)
);


*/
}
