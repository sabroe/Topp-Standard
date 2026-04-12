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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
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
@AllArgsConstructor(staticName = "of")
public final class Time {
    /**
     * Absolute time.
     */
    private final Instant instant;

    public Time map(UnaryOperator<Instant> operator) {
        return new Time(operator.apply(instant));
    }

    public Time plus(long amount,
                     TemporalUnit unit) {
        return map(i -> i.plus(amount, unit));
    }

    public Time plus(TemporalAmount amount) {
        return map(i -> i.plus(amount));
    }

    public Time minus(long amount,
                      TemporalUnit unit) {
        return map(i -> i.minus(amount, unit));
    }

    public Time minus(TemporalAmount amount) {
        return map(i -> i.minus(amount));
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

    public static Time of(Date date) {
        Objects.requireNonNull(date, "date");
        return new Time(date.toInstant());
    }

    public static Time of(InstantSource instantSource) {
        Objects.requireNonNull(instantSource, "instantSource");
        return new Time(instantSource.instant());
    }

    public static TimeCreate create() {
        return TimeCreate.of();
    }

    public TimeMap map() {
        return TimeMap.of(this);
    }

    public TimeAt at() {
        return TimeAt.of(this);
    }

    public TimeTo to() {
        return TimeTo.of(this);
    }
}
