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

package com.yelstream.topp.standard.time.legacy.util;

import com.yelstream.topp.standard.time.view.Time;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Utility addressing instances of {@link java.util.Date}.
 * <p>
 *     Note that a legacy {@link java.util.Date} is mutable!
 * </p>
 * <p>
 *     Functional bridge, strict core:
 * </p>
 * <ol>
 *     <li>
 *         {@link #get(Supplier)}
 *     </li>
 *     <li>
 *         {@link #consume(Date, Consumer)}
 *     </li>
 *     <li>
 *         {@link #map(Date, Function)}
 *     </li>
 *     <li>
 *         {@link #from(Date, Function)}
 *     </li>
 * </ol>
 * <p>
 *     Functional bridge, null-safe:
 * </p>
 * <ol>
 *     <li>
 *         {@link #getOptional(Supplier)}
 *     </li>
 *     <li>
 *         {@link #consumeNullable(Date, Consumer)}
 *     </li>
 *     <li>
 *         {@link #mapNullable(Date, Function)}
 *     </li>
 *     <li>
 *         {@link #fromNullable(Date, Function)}
 *     </li>
 * </ol>
 * <p>
 *     Arithmetic / transformation:
 * </p>
 * <ol>
 *     <li>
 *         {@link #plus(Date, long, TemporalUnit)}, {@link #plus(Date, TemporalAmount)}
 *     </li>
 *     <li>
 *         {@link #minus(Date, long, TemporalUnit)}, {@link #minus(Date, TemporalAmount)}
 *     </li>
 *     <li>
 *         {@link #truncatedTo(Date, TemporalUnit)}
 *     </li>
 *     <li>
 *         {@link #with(Date, TemporalField, long)}, {@link #with(Date, TemporalAdjuster)}
 *     </li>
 *     <li>
 *         {@link #mapZoned(Date, ZoneId, UnaryOperator)}
 *     </li>
 * </ol>
 * <p>
 *     Mutation (explicitly separate!):
 * </p>
 * <ol>
 *     <li>
 *         {@link #mutate(Date, UnaryOperator)}
 *     </li>
 *     <li>
 *         {@link #mutateNullable(Date, UnaryOperator)}
 *     </li>
 *     <li>
 *         {@link #copy(Date)}
 *     </li>
 * </ol>
 * <p>
 *     Comparison / utilities:
 * </p>
 * <ol>
 *     <li>
 *         {@link #min(Date, Date)}
 *     </li>
 *     <li>
 *         {@link #max(Date, Date)}
 *     </li>
 *     <li>
 *         {@link #isBefore(Date, Date)}
 *     </li>
 *     <li>
 *         {@link #isAfter(Date, Date)}
 *     </li>
 *     <li>
 *         {@link #isEqual(Date, Date)}
 *     </li>
 * </ol>
 * <p>
 *     Conversion (very important group):
 * </p>
 * <ol>
 *     <li>
 *         {@link #fromEpochMilli(long)}
 *     </li>
 *     <li>
 *         {@link #toEpochMilli(Date)}
 *     </li>
 *     <li>
 *         {@link #toDate(long)}
 *     </li>
 *     <li>
 *         {@link #toDate(Instant)}
 *     </li>
 *     <li>
 *         {@link #fromInstant(Instant)}
 *     </li>
 *     <li>
 *         {@link #toInstant(Date)}
 *     </li>
 *     <li>
 *         {@link #zoned(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromLocalDate(LocalDate, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #toLocalDate(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromLocalDateTime(LocalDateTime, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #toLocalDateTime(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromOffsetDateTime(OffsetDateTime)}
 *     </li>
 *     <li>
 *         {@link #toOffsetDateTime(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromZonedDateTime(ZonedDateTime)}
 *     </li>
 *     <li>
 *         {@link #toZonedDateTime(Date, ZoneId)}
 *     </li>
 * </ol>
 * <p>
 *     Convenience:
 * </p>
 * <ol>
 *     <li>
 *         {@link #toLocalDateUTC(Date)}
 *     </li>
 *     <li>
 *         {@link #toLocalDateTimeUTC(Date)}
 *     </li>
 *     <li>
 *         {@link #toLocalDateSystem(Date)}
 *     </li>
 *     <li>
 *         {@link #toLocalDateTimeSystem(Date)}
 *     </li>
 *     <li>
 *         {@link #now(Clock)}
 *     </li>
 *     <li>
 *         {@link #now(InstantSource)}
 *     </li>
 * </ol>
 * <p>
 *     Fluent view:
 * </p>
 * <ol>
 *     <li>
 *         {@link #time(Date)}
 *     </li>
 * </ol>
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
     * Consumes a legacy date into a modern computation/operation.
     * @param date Legacy date.
     * @param operation Operation.
     */
    public static void consume(Date date,
                               Consumer<Instant> operation) {
        operation.accept(date.toInstant());
    }

    /**
     * Transforms a legacy date by sending it through a modern computation/operation.
     * @param date Legacy date.
     * @param operation Operation.
     * @return Transformed date.
     * @param <T> Type of temporal returned by the operation.
     */
    public static <T extends TemporalAccessor> Date map(Date date,
                                                        Function<Instant,T> operation) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(operation, "operation");
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
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(operation, "operation");
        return operation.apply(date.toInstant());
    }

    /**
     *
     * @param operation
     * @return
     * @param <T>
     */
    public static <T extends TemporalAccessor> Optional<Date> getOptional(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation");
        return Optional.ofNullable(operation.get()).map(Instant::from).map(Date::from);
    }

    /**
     *
     * @param date
     * @param operation
     */
    public static void consumeNullable(Date date,
                                       Consumer<Instant> operation) {
        Objects.requireNonNull(operation, "operation");
        if (date != null) {
            operation.accept(date.toInstant());
        }
    }

    /**
     *
     * @param date
     * @param operation
     * @return
     * @param <T>
     */
    public static <T extends TemporalAccessor> Optional<Date> mapNullable(Date date,
                                                                          Function<Instant,T> operation) {
        return Optional.ofNullable(date).map(Date::toInstant).map(operation).map(Instant::from).map(Date::from);
    }

    /**
     *
     * @param date
     * @param operation
     * @return
     * @param <V>
     */
    public static <V> Optional<V> fromNullable(Date date,
                                               Function<Instant,V> operation) {
        return Optional.ofNullable(date).map(Date::toInstant).map(operation);
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
        Objects.requireNonNull(date, "date");
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
        Objects.requireNonNull(date, "date");
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
        Objects.requireNonNull(date, "date");
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
        Objects.requireNonNull(date, "date");
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
        Objects.requireNonNull(date, "date");
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
        Objects.requireNonNull(date, "date");
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
        Objects.requireNonNull(date, "date");
        return map(date, instant -> instant.with(field,value));
    }

    /**
     *
     * @param date
     * @param zone
     * @param operator
     * @return
     */
    public static Date mapZoned(Date date,
                                ZoneId zone,
                                UnaryOperator<ZonedDateTime> operator) {
        return Date.from(operator.apply(zoned(date, zone)).toInstant());
    }

    /**
     * Mutates the contents of a legacy date.
     * @param date Legacy date.
     *             This must be non-{@code null}.
     * @param operation Operation.
     * @return Mutated date.
     *         The object-reference if identical to the input.
     */
    public static Date mutate(Date date,
                              UnaryOperator<Instant> operation) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(operation, "operation");
        Instant result = operation.apply(date.toInstant());
        date.setTime(result.toEpochMilli());
        return date;
    }

    public static Date mutateNullable(Date date,
                                      UnaryOperator<Instant> operation) {
        return date==null?null:mutate(date,operation);
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

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isBefore(Date a,
                                   Date b) {
        return a != null && b != null && a.before(b);
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isAfter(Date a,
                                  Date b) {
        return a != null && b != null && a.after(b);
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqual(Date a,
                                  Date b) {
        return Objects.equals(a, b);
    }

    /**
     *
     * @param date
     * @return
     */
    public static long toEpochMilli(Date date) {
        return date.getTime();
    }

    /**
     *
     * @param millis
     * @return
     */
    public static Date fromEpochMilli(long millis) {
        return new Date(millis);
    }

    /**
     *
     * @param time
     * @return
     */
    public static Date toDate(long time) {
        return new Date(time);
    }

    /**
     *
     * @param instant
     * @return
     */
    public static Date toDate(Instant instant) {
        return Date.from(instant);
    }

    /**
     *
     * @param instant
     * @return
     */
    public static Date fromInstant(Instant instant) {
        return Date.from(instant);
    }

    /**
     *
     * @param date Legacy date.
     * @return
     */
    public static Instant toInstant(Date date) {
        return date.toInstant();
    }

    /**
     *
     * @param date Legacy date.
     * @param zone
     * @return
     */
    public static ZonedDateTime zoned(Date date,
                                      ZoneId zone) {  // Allows e.g. 'Dates.zoned(date, zone).withDayOfMonth(1).plusMonths(1)'
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(zone, "zone");
        return date.toInstant().atZone(zone);
    }

    /**
     *
     * @param date Legacy date.
     * @param zone
     * @return
     */
    public static Date fromLocalDate(LocalDate date,
                                     ZoneId zone) {
        return Date.from(date.atStartOfDay(zone).toInstant());
    }

    /**
     *
     * @param date Legacy date.
     * @param zone
     * @return
     */
    public static LocalDate toLocalDate(Date date,
                                        ZoneId zone) {
        return zoned(date, zone).toLocalDate();
    }

    /**
     *
     * @param dateTime
     * @param zone
     * @return
     */
    public static Date fromLocalDateTime(LocalDateTime dateTime,
                                         ZoneId zone) {
        return Date.from(dateTime.atZone(zone).toInstant());
    }

    /**
     *
     * @param date Legacy date.
     * @param zone
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date,
                                                ZoneId zone) {
        return zoned(date, zone).toLocalDateTime();
    }

    /**
     *
     * @param date Legacy date.
     * @param zone
     * @return
     */
    public static OffsetDateTime toOffsetDateTime(Date date,
                                                  ZoneId zone) {
        return zoned(date, zone).toOffsetDateTime();
    }

    /**
     *
     * @param dateTime
     * @return
     */
    public static Date fromOffsetDateTime(OffsetDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    /**
     *
     * @param dateTime
     * @return
     */
    public static Date fromZonedDateTime(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    /**
     *
     * @param date Legacy date.
     * @param zone
     * @return
     */
    public static ZonedDateTime toZonedDateTime(Date date,
                                                ZoneId zone) {
        return zoned(date,zone);
    }

    /**
     *
     * @param date Legacy date.
     * @return
     */
    public static LocalDate toLocalDateUTC(Date date) {
        return toLocalDate(date,ZoneOffset.UTC);
    }

    /**
     *
     * @param date Legacy date.
     * @return
     */
    public static LocalDateTime toLocalDateTimeUTC(Date date) {
        return toLocalDateTime(date,ZoneOffset.UTC);
    }

    /**
     *
     * @param date Legacy date.
     * @return
     */
    public static LocalDate toLocalDateSystem(Date date) {
        return toLocalDate(date,ZoneId.systemDefault());
    }

    /**
     *
     * @param date Legacy date.
     * @return
     */
    public static LocalDateTime toLocalDateTimeSystem(Date date) {
        return toLocalDateTime(date,ZoneId.systemDefault());
    }

    /**
     *
     * @param clock
     * @return
     */
    public static Date now(Clock clock) {
        return Date.from(clock.instant());
    }

    /**
     *
     * @param instantSource
     * @return
     */
    public static Date now(InstantSource instantSource) {
        return Date.from(instantSource.instant());
    }

    /**
     * Creates a fluent view of absolute time.
     * @param date Legacy date.
     * @return Fluent view of absolute time.
     */
    public static Time time(Date date) {
        return Time.of(date);
    }
}
