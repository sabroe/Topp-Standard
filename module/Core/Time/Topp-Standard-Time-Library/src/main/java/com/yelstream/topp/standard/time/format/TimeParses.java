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

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 * Utilities addressing instances of {@link TimeParse}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
@UtilityClass
public final class TimeParses {

    public static TimeParse from(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter);
        return text -> formatter.parse(text, Instant::from);
    }

    public static final TimeParse ISO = from(DateTimeFormatter.ISO_INSTANT);


    public static TimeParse withZone(DateTimeFormatter formatter,
                                     ZoneId zone) {  //TO-DO: Consider the presence of this!
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

    public static TimeParse smart(DateTimeFormatter formatter,
                                  ZoneId defaultZone) {  //TO-DO: Consider the presence of this!
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
}
