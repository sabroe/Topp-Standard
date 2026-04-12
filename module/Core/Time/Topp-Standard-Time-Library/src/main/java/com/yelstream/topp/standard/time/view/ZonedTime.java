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

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.function.UnaryOperator;

/**
 * Fluent view of human/calendar time.
 * <p>
 *     This view is zoned by being based on an {@code ZonedDateTime}.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of")
public final class ZonedTime implements InstantSource {  //TO-DO: Consider if this should be an instant-source?
    /**
     * Human/calendar time.
     */
    private final ZonedDateTime zdt;

    public ZonedTime map(UnaryOperator<ZonedDateTime> operator) {
        return new ZonedTime(operator.apply(zdt));
    }

    public ZonedTime plus(long amount,
                          TemporalUnit unit) {
        return map(i -> i.plus(amount, unit));
    }

    public ZonedTime plus(TemporalAmount amount) {
        return map(i -> i.plus(amount));
    }

    public ZonedTime minus(long amount,
                           TemporalUnit unit) {
        return map(i -> i.minus(amount, unit));
    }

    public ZonedTime minus(TemporalAmount amount) {
        return map(i -> i.minus(amount));
    }

    public ZonedDateTime toZonedDateTime() {
        return zdt;
    }

    public LocalDate toLocalDate() {
        return zdt.toLocalDate();
    }

    public LocalDateTime toLocalDateTime() {
        return zdt.toLocalDateTime();
    }

    public LocalTime toLocalTime() {
        return zdt.toLocalTime();
    }

    public Time toTime() {
        return Time.of(zdt.toInstant());
    }

    public Date toDate() {
        return Date.from(zdt.toInstant());
    }

    public static ZonedTimeCreate create() {
        return ZonedTimeCreate.of();
    }

    public ZonedTimeMap map() {
        return ZonedTimeMap.of(this);
    }

    public ZonedTimeTo to() {
        return ZonedTimeTo.of(this);
    }

    @Override
    public Instant instant() {
        return zdt.toInstant();
    }
}
