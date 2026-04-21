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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Creates zoned time instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class ZonedTimeCreate {
    public ZonedTime from(ZonedDateTime zdt) {
        return ZonedTime.of(zdt);
    }

    public ZonedTime from(Instant instant,
                          ZoneId zone) {
        return ZonedTime.of(instant.atZone(zone));
    }

    public ZonedTime from(Date date,
                          ZoneId zone) {
        return ZonedTime.of(date.toInstant().atZone(zone));
    }

    public ZonedTime from(LocalDateTime localDateTime,
                          ZoneId zone) {
        return ZonedTime.of(localDateTime.atZone(zone));
    }

    public ZonedTime from(LocalDate localDate,
                          ZoneId zone) {
        return ZonedTime.of(localDate.atStartOfDay().atZone(zone));
    }

    public ZonedTime from(OffsetDateTime offsetDateTime) {
        return ZonedTime.of(offsetDateTime.toZonedDateTime());
    }

    public ZonedTime now() {
        return ZonedTime.of(ZonedDateTime.now());
    }

    public ZonedTime now(ZoneId zone) {
        return ZonedTime.of(ZonedDateTime.now(zone));
    }

    public ZonedTime now(Clock clock) {
        return ZonedTime.of(ZonedDateTime.now(clock));
    }
}
