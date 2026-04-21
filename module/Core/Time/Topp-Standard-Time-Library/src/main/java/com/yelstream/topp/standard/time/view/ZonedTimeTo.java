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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 *
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class ZonedTimeTo {
    /**
     * Human/calendar time.
     */
    private final ZonedTime zonedTime;

    public ZonedDateTime zonedDateTime() {
        return zonedTime.toZonedDateTime();
    }

    public OffsetDateTime offsetDateTime() {
        return zonedTime.toOffsetDateTime();
    }

    public LocalDate localDate() {
        return zonedTime.toLocalDate();
    }

    public LocalDateTime localDateTime() {
        return zonedTime.toLocalDateTime();
    }

    public LocalTime localTime() {
        return zonedTime.toLocalTime();
    }

    public Time time() {
        return zonedTime.toTime();
    }

    public Instant instant() {
        return zonedTime.toInstant();
    }

    public Date date() {
        return zonedTime.toDate();
    }
}
