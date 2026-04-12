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
 * Utilities addressing instances of legacy {@link Date}.
 * <p>
 *     Provides a bridge between the legacy date/time API and the {@link java.time} API.
 * </p>
 * <p>
 *     The legacy {@link Date} is mutable and lacks clear time zone semantics.
 *     These utilities centralize conversions and operations using the immutable {@code java.time} API.
 * </p>
 * <h2>Method categories</h2>
 * <p>
 *     <u>Supplier-based:</u>
 * </p>
 * <ol>
 *     <li>
 *         {@link #get(Supplier)}
 *     </li>
 *     <li>
 *         {@link #getOptional(Supplier)}
 *     </li>
 * </ol>
 * <p>
 *     <u>Functional bridge, date-based:</u>
 * </p>
 * <ol>
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
 *     <u>Functional bridge, date-based with optional semantics:</u>
 * </p>
 * <ol>
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
 *     <u>Functional bridge, date-based with null-aware semantics:</u>
 * </p>
 * <ol>
 *     <li>
 *         {@link #consumeNullAware(Date, Consumer)}
 *     </li>
 *     <li>
 *         {@link #mapNullAware(Date, Function)}
 *     </li>
 *     <li>
 *         {@link #fromNullAware(Date, Function)}
 *     </li>
 * </ol>
 * <p>
 *     <u>Arithmetic / transformation:</u>
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
 *     <u>Mutation:</u>
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
 *     <u>Comparison:</u>
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
 *     <u>Creation:</u>
 * </p>
 * <ol>
 *     <li>
 *         {@link #fromEpochMilli(long)}
 *     </li>
 *     <li>
 *         {@link #fromInstant(Instant)}
 *     </li>
 *     <li>
 *         {@link #zoned(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromLocalDate(LocalDate, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromLocalDateTime(LocalDateTime, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #fromOffsetDateTime(OffsetDateTime)}
 *     </li>
 *     <li>
 *         {@link #fromZonedDateTime(ZonedDateTime)}
 *     </li>
 * </ol>
 * <p>
 *     <u>Extraction:</u>
 * </p>
 * <ol>
 *     <li>
 *         {@link #toEpochMilli(Date)}
 *     </li>
 *     <li>
 *         {@link #toInstant(Date)}
 *     </li>
 *     <li>
 *         {@link #toLocalDate(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #toLocalDateTime(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #toOffsetDateTime(Date, ZoneId)}
 *     </li>
 *     <li>
 *         {@link #toZonedDateTime(Date, ZoneId)}
 *     </li>
 * </ol>
 * <p>
 *     <u>Convenience:</u>
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
 *     <u>Fluent view:</u>
 * </p>
 * <ol>
 *     <li>
 *         {@link #time(Date)}
 *     </li>
 * </ol>
 * <h2>Null handling</h2>
 * <ul>
 *     <li>Methods without "Nullable" in the name do not accept {@code null}.</li>
 *     <li>Methods with "Nullable" in the name accept {@code null} and propagate it safely.</li>
 * </ul>
 * <h2>Immutability</h2>
 * <p>
 *     All methods return new {@link Date} instances and do not mutate the input,
 *     unless stated otherwise.
 * </p>
 * <h2>Time zones</h2>
 * <p>
 *     Time zone handling is explicit.
 *     Methods that depend on calendar fields require a {@link ZoneId}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-06
 */
@UtilityClass
public class Dates {
    /**
     * Obtains a legacy date from time computation/operation.
     * @param operation Operation.
     * @return Legacy date.
     * @param <T> Type of temporal returned by the operation.
     */
    public static <T extends TemporalAccessor> Date get(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation");
        return Date.from(Instant.from(operation.get()));
    }

    /**
     * Consumes a legacy date into a time computation/operation.
     * @param date Legacy date.
     * @param operation Operation.
     */
    public static void consume(Date date,
                               Consumer<Instant> operation) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(operation, "operation");
        operation.accept(date.toInstant());
    }

    /**
     * Transforms a legacy date by sending it through a time computation/operation.
     * @param date Legacy date.
     * @param operation Operation.
     * @return Transformed date.
     * @param <T> Type of temporal returned by the operation.
     *            Instances of this type must be convertible via {@link Instant#from(TemporalAccessor)}.
     */
    public static <T extends TemporalAccessor> Date map(Date date,
                                                        Function<Instant,T> operation) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(operation, "operation");
        return Date.from(Instant.from(operation.apply(date.toInstant())));
    }

    /**
     * Extracts a value from a legacy date by sending it through a time computation/operation.
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
     * Obtains a legacy date from time computation/operation.
     * @param operation Operation.
     *                  This may produce {@code null}.
     * @return Legacy date as an optional.
     *         This contained date may be {@code null}.
     * @param <T> Type of temporal returned by the operation.
     */
    public static <T extends TemporalAccessor> Optional<Date> getOptional(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation");
        return Optional.ofNullable(operation.get()).map(Instant::from).map(Date::from);
    }

    /**
     * Consumes a legacy date into a time computation/operation.
     * <p>
     *     Note that this is <i>null-safe</i> implying that the operation is never called with {@code null}!
     * </p>
     * @param date Legacy date.
     *             This may be {@code null}.
     * @param operation Operation.
     *                  This is never called with {@code null}.
     */
    public static void consumeNullable(Date date,
                                       Consumer<Instant> operation) {
        Objects.requireNonNull(operation, "operation");
        Optional.ofNullable(date).map(Date::toInstant).ifPresent(operation);
    }

    /**
     * Transforms a legacy date by sending it through a time computation/operation.
     * <p>
     *     Note that this is <i>null-safe</i> implying that the operation is never called with {@code null}!
     * </p>
     * @param date Legacy date.
     *             This may be {@code null}.
     * @param operation Operation.
     *                  This is never called with {@code null}.
     * @return Transformed date.
     */
    public static Optional<Date> mapNullable(Date date,
                                             Function<Instant,Instant> operation) {
        Objects.requireNonNull(operation, "operation");
        return Optional.ofNullable(date).map(Date::toInstant).map(operation).map(Instant::from).map(Date::from);
    }

    /**
     * Extracts a value from a legacy date by sending it through a time computation/operation.
     * <p>
     *     Note that this is <i>null-safe</i> implying that the operation is never called with {@code null}!
     * </p>
     * @param date Legacy date.
     *             This may be {@code null}.
     * @param operation Operation.
     *                  This is never called with {@code null}.
     * @return Value.
     * @param <V> Type of value returned.
     */
    public static <V> Optional<V> fromNullable(Date date,
                                               Function<Instant,V> operation) {
        Objects.requireNonNull(operation, "operation");
        return Optional.ofNullable(date).map(Date::toInstant).map(operation);
    }

    /**
     * Consumes a legacy date into a time computation/operation.
     * <p>
     *     Note that this is <i>null-aware</i> implying that the operation may be called with {@code null}.
     * </p>
     * @param date Legacy date.
     *             This may be {@code null}.
     * @param operation Operation.
     *                  This may be called with {@code null}.
     */
    public static void consumeNullAware(Date date,
                                        Consumer<Instant> operation) {
        Objects.requireNonNull(operation, "operation");
        Instant instant = (date != null ? date.toInstant() : null);
        operation.accept(instant);
    }

    /**
     * Transforms a legacy date by sending it through a time computation/operation.
     * <p>
     *     Note that this is <i>null-aware</i> implying that the operation may be called with {@code null}.
     * </p>
     * @param date Legacy date.
     *             This may be {@code null}.
     * @param operation Operation.
     *                  This may be called with {@code null}.
     * @return Transformed date.
     */
    public static Date mapNullAware(Date date,
                                    Function<Instant, Instant> operation) {
        Objects.requireNonNull(operation, "operation");
        Instant input = (date != null ? date.toInstant() : null);
        Instant result = operation.apply(input);
        return (result != null ? Date.from(result) : null);
    }

    /**
     * Extracts a value from a legacy date by sending it through a time computation/operation.
     * <p>
     *     Note that this is <i>null-aware</i> implying that the operation may be called with {@code null}.
     * </p>
     * @param date Legacy date.
     *             This may be {@code null}.
     * @param operation Operation.
     *                  This may be called with {@code null}.
     * @return Value.
     * @param <V> Type of value returned.
     */
    public static <V> V fromNullAware(Date date,
                                      Function<Instant, V> operation) {
        Objects.requireNonNull(operation, "operation");
        Instant input = (date != null ? date.toInstant() : null);
        return operation.apply(input);
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
        Objects.requireNonNull(adjuster, "adjuster");
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
        Objects.requireNonNull(field, "field");
        return map(date, instant -> instant.with(field,value));
    }

    /**
     * Transforms a legacy date by first converting it to a {@link ZonedDateTime},
     * applying a zone-aware operation,
     * and converting the result back to a {@link Date}.
     * <p>
     *     This is the preferred entry point for operations that depend on calendar fields
     *     such as day-of-month, month, or daylight saving time rules.
     * </p>
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param zone Time zone used for conversion.
     *             Must not be {@code null}.
     * @param operator Operation applied to the zoned date-time.
     *                 Must not be {@code null}.
     * @return Transformed date.
     */
    public static Date mapZoned(Date date,
                                ZoneId zone,
                                UnaryOperator<ZonedDateTime> operator) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(zone, "zone");
        Objects.requireNonNull(operator, "operator");
        return Date.from(operator.apply(zoned(date, zone)).toInstant());
    }

    /**
     * Applies a transformation to the underlying {@link Instant} of a legacy date.
     * <p>
     *     This method performs zone-independent transformations and is suitable for
     *     operations that do not depend on calendar systems or time zones.
     * </p>
     *
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param operator Transformation applied to the instant.
     *                 Must not be {@code null}.
     * @return Transformed date.
     */
    public static Date mutate(Date date,
                              UnaryOperator<Instant> operator) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(operator, "operator");
        Instant result = operator.apply(date.toInstant());
        date.setTime(result.toEpochMilli());
        return date;
    }

    /**
     * Mutates the contents of a legacy date if non-{@code null}.
     * <p>
     *     If {@code date} is {@code null}, {@code null} is returned and the operation is not invoked.
     * </p>
     * @param date Legacy date.
     *             May be {@code null}.
     * @param operation Mutation operation applied to the underlying {@link Instant}.
     *                  Must not be {@code null}.
     * @return Mutated date, or {@code null} if the input was {@code null}.
     */
    public static Date mutateNullable(Date date,
                                      UnaryOperator<Instant> operation) {  //TO-DO: Consider semantics; if date is null then do call operation?
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
     * @return The earlier date, or {@code a} if equal.
     *         Is {@code null} only if both are {@code null}.
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
     * @return The later date, or {@code a} if equal.
     *         Is {@code null} only if both are {@code null}.
     */
    public static Date max(Date a,
                           Date b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * Tests if one date is strictly before another.
     * <p>
     *     Returns {@code false} if either argument is {@code null}.
     * </p>
     * @param a First date.
     * @param b Second date.
     * @return Indicates, if {@code a} is before {@code b}.
     */
    public static boolean isBefore(Date a,
                                   Date b) {
        return a != null && b != null && a.before(b);
    }

    /**
     * Tests if one date is strictly after another.
     * <p>
     *     Returns {@code false} if either argument is {@code null}.
     * </p>
     * @param a First date.
     * @param b Second date.
     * @return Indicates, if {@code a} is after {@code b}.
     */
    public static boolean isAfter(Date a,
                                  Date b) {
        return a != null && b != null && a.after(b);
    }

    /**
     * Tests if two dates are equal.
     * <p>
     *     This method is null-safe and delegates to {@link Objects#equals(Object, Object)}.
     * </p>
     * @param a First date.
     * @param b Second date.
     * @return Indicates, if both are equal.
     */
    public static boolean isEqual(Date a,
                                  Date b) {
        return Objects.equals(a, b);
    }

    /**
     * Converts a legacy date to epoch milliseconds.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @return Number of milliseconds since the epoch (1970-01-01T00:00:00Z).
     */
    public static long toEpochMilli(Date date) {
        return date.getTime();
    }

    /**
     * Creates a legacy date from epoch milliseconds.
     * @param millis Number of milliseconds since the epoch (1970-01-01T00:00:00Z).
     * @return New date instance.
     */
    public static Date fromEpochMilli(long millis) {
        return new Date(millis);
    }

    /**
     * Converts an {@link Instant} to a legacy date.
     * @param instant Instant.
     *                Must not be {@code null}.
     * @return Date representing the same point in time.
     */
    public static Date fromInstant(Instant instant) {
        return Date.from(instant);
    }

    /**
     * Converts a legacy date to an {@link Instant}.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @return Instant representing the same point in time.
     */
    public static Instant toInstant(Date date) {
        return date.toInstant();
    }

    /**
     * Converts a legacy date to a {@link ZonedDateTime} using a specific time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Zoned date-time representing the same instant in the given zone.
     */
    public static ZonedDateTime zoned(Date date,
                                      ZoneId zone) {  // Allows e.g. 'Dates.zoned(date, zone).withDayOfMonth(1).plusMonths(1)'
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(zone, "zone");
        return date.toInstant().atZone(zone);
    }

    /**
     * Converts a {@link LocalDate} to a legacy date using the start of day in a specific time zone.
     * @param date Local date.
     *             Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Corresponding date at start of day in the given zone.
     */
    public static Date fromLocalDate(LocalDate date,
                                     ZoneId zone) {
        return Date.from(date.atStartOfDay(zone).toInstant());
    }

    /**
     * Converts a legacy date to a {@link LocalDate} in a specific time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Local date in the given zone.
     */
    public static LocalDate toLocalDate(Date date,
                                        ZoneId zone) {
        return zoned(date, zone).toLocalDate();
    }

    /**
     * Converts a {@link LocalDateTime} to a legacy date using a specific time zone.
     * @param dateTime Local date-time.
     *                 Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Corresponding date in the given zone.
     */
    public static Date fromLocalDateTime(LocalDateTime dateTime,
                                         ZoneId zone) {
        return Date.from(dateTime.atZone(zone).toInstant());
    }

    /**
     * Converts a legacy date to a {@link LocalDateTime} in a specific time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Local date-time in the given zone.
     */
    public static LocalDateTime toLocalDateTime(Date date,
                                                ZoneId zone) {
        return zoned(date, zone).toLocalDateTime();
    }

    /**
     * Converts a legacy date to an {@link OffsetDateTime} in a specific time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Offset date-time in the given zone.
     */
    public static OffsetDateTime toOffsetDateTime(Date date,
                                                  ZoneId zone) {
        return zoned(date, zone).toOffsetDateTime();
    }

    /**
     * Converts an {@link OffsetDateTime} to a legacy date.
     * @param dateTime Offset date-time.
     *                 Must not be {@code null}.
     * @return Date representing the same point in time.
     */
    public static Date fromOffsetDateTime(OffsetDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    /**
     * Converts a {@link ZonedDateTime} to a legacy date.
     * @param dateTime Zoned date-time.
     *                 Must not be {@code null}.
     * @return Date representing the same point in time.
     */
    public static Date fromZonedDateTime(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    /**
     * Converts a legacy date to a {@link ZonedDateTime} in a specific time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @param zone Time zone.
     *             Must not be {@code null}.
     * @return Zoned date-time in the given zone.
     */
    public static ZonedDateTime toZonedDateTime(Date date,
                                                ZoneId zone) {
        return zoned(date,zone);
    }

    /**
     * Converts a legacy date to a {@link LocalDate} using {@link ZoneOffset#UTC}.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @return Local date in UTC.
     */
    public static LocalDate toLocalDateUTC(Date date) {
        return toLocalDate(date,ZoneOffset.UTC);
    }

    /**
     * Converts a legacy date to a {@link LocalDateTime} using {@link ZoneOffset#UTC}.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @return Local date-time in UTC.
     */
    public static LocalDateTime toLocalDateTimeUTC(Date date) {
        return toLocalDateTime(date,ZoneOffset.UTC);
    }

    /**
     * Converts a legacy date to a {@link LocalDate} using the system default time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @return Local date in the system default zone.
     */
    public static LocalDate toLocalDateSystem(Date date) {
        return toLocalDate(date,ZoneId.systemDefault());
    }

    /**
     * Converts a legacy date to a {@link LocalDateTime} using the system default time zone.
     * @param date Legacy date.
     *             Must not be {@code null}.
     * @return Local date-time in the system default zone.
     */
    public static LocalDateTime toLocalDateTimeSystem(Date date) {
        return toLocalDateTime(date,ZoneId.systemDefault());
    }

    /**
     * Obtains the current date using a specific clock.
     * @param clock Clock providing the current instant.
     *              Must not be {@code null}.
     * @return Current date.
     */
    public static Date now(Clock clock) {
        return Date.from(clock.instant());
    }

    /**
     * Obtains the current date using a specific instant source.
     * @param instantSource Source of instants.
     *                      Must not be {@code null}.
     * @return Current date.
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
