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

package com.yelstream.topp.standard.time.legacy;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link java.util.Date}.
 * <p>
 *     Note that {@link java.util.Date} is mutable!
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-06
 */
@UtilityClass
public class Dates {
    /**
     * Produces a legacy date from a modern computation/operation.
     * @param operation Operation.
     * @return Legacy date.
     * @param <T> Type of temporal returned by the operation.
     */
    public static <T extends TemporalAccessor> Date get(Supplier<T> operation) {
        return Date.from(Instant.from(operation.get()));
    }

    /**
     * Passes a legacy date into a modern computation/operation.
     * @param date Legacy date.
     * @param operation Operation.
     */
    public static void set(Date date,
                           Consumer<Instant> operation) {
        operation.accept(date.toInstant());
    }

    /**
     * Transforms a legacy date by sending it through a modern computation/operation.
     * @param date Legacy date.
     * @param operation Operation
     * @return Transformed date.
     * @param <T> Type of temporal returned by the operation.
     */
    public static <T extends TemporalAccessor> Date map(Date date,
                                                        Function<Instant,T> operation) {
        return Date.from(Instant.from(operation.apply(date.toInstant())));
    }

    /**
     * Extracts a value from a legacy date by sending it through a modern computation/operation.
     * @param date Legacy date.
     * @param operation Operation.
     * @return Value.
     * @param <V> Type of value returned.
     */
    public static <V> V from(Date date,
                             Function<Instant,V> operation) {
        return operation.apply(date.toInstant());
    }

    /**
     * Adds a specified amount of a given unit to a legacy date.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param amount Amount to add.
     * @param unit Temporal unit.
     * @return New date with the added amount.
     */
    public static Date plus(Date date,
                            long amount,
                            TemporalUnit unit) {
        return map(date, instant -> instant.plus(amount,unit));
    }

    /**
     * Adds a specified temporal amount to a legacy date.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param amount Temporal amount.
     * @return New date with the added amount.
     */
    public static Date plus(Date date,
                            TemporalAmount amount) {
        return map(date, instant -> instant.plus(amount));
    }

    /**
     * Subtracts a specified amount of a given unit from a legacy date.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param amount Amount to add.
     * @param unit Temporal unit.
     * @return New date with the subtracted amount.
     */
    public static Date minus(Date date,
                             long amount,
                             TemporalUnit unit) {
        return map(date, instant -> instant.minus(amount,unit));
    }

    /**
     * Subtracts a specified temporal amount from a legacy date.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param amount Temporal amount.
     * @return New date with the subtracted amount.
     */
    public static Date minus(Date date,
                             TemporalAmount amount) {
        return map(date, instant -> instant.minus(amount));
    }

    /**
     * Truncates a legacy date to a specified unit.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param unit Temporal unit to truncate to.
     * @return New truncated date.
     */
    public static Date truncatedTo(Date date,
                                   TemporalUnit unit) {
        return map(date, instant -> instant.truncatedTo(unit));
    }

    /**
     * Applies a {@link TemporalAdjuster} to a legacy date.
     * <p>
     *     Note:
     *     </br>
     *         {@link Instant} only supports a limited set of {@link TemporalField} and {@link TemporalAdjuster} values.
     *     </br>
     *     </br>
     *         Zone-sensitive adjustments require converting to {@link java.time.ZonedDateTime} first,
     *         inside the operation lambda.
     * * </p>
     * @param date Legacy date.
     * @param adjuster Temporal adjuster.
     *                 This will be applied upon {@link Instant}.
     * @return Transformed date.
     */
    public static Date with(Date date,
                            TemporalAdjuster adjuster) {
        return map(date, instant -> instant.with(adjuster));
    }

    /**
     * Sets the specified {@link TemporalField} to a new value on a legacy date.
     * <p>
     *     Note:
     *     </br>
     *         {@link Instant} only supports a limited set of {@link TemporalField} and {@link TemporalAdjuster} values.
     *     </br>
     *     </br>
     *         Zone-sensitive adjustments require converting to {@link java.time.ZonedDateTime} first,
     *         inside the operation lambda.
     * * </p>
     * @param date Legacy date.
     * @param field Temporal field.
     *              This will be applied upon {@link Instant}.
     * @param value Temporal value.
     * @return Transformed date.
     */
    public static Date with(Date date,
                            TemporalField field,
                            long value) {
        return map(date, instant -> instant.with(field,value));
    }

    /**
     * Copies a legacy date.
     * @param date Legacy date.
     *             This may be {@code null}.
     * @return Date copy.
     */
    public static Date copy(Date date) {
        return date == null ? null: new Date(date.getTime());
    }

    /**
     * Finds the minimum of two dates.
     * <p>
     *     If the dates are equal, the first argument is returned (stable).
     * </p>
     * <p>
     *     Note that this does not copy the mutable dates by default.
     *     If a copy is needed, use {@link #copy(Date)}.
     * </p>
     * @param a First date.
     *          May be {@code null}.
     * @param b Second date.
     *          May be {@code null}.
     * @return The earlier date, or {@code a} if equal. Returns {@code null} only if both are {@code null}.
     */
    public static Date min(Date a,
                           Date b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * Finds the maximum of two dates.
     * <p>
     *     If the dates are equal, the first argument is returned (stable).
     * </p>
     * <p>
     *     Note that this does not copy the mutable dates by default.
     *     If a copy is needed, use {@link #copy(Date)}.
     * </p>
     * @param a First date. May be {@code null}.
     * @param b Second date. May be {@code null}.
     * @return The later date, or {@code a} if equal. Returns {@code null} only if both are {@code null}.
     */
    public static Date max(Date a,
                           Date b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) >= 0 ? a : b;
    }
}
