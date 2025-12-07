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

package com.yelstream.topp.standard.time;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.function.BiFunction;

/**
 * Utility addressing instances of date-time formatters.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-17
 */
@UtilityClass
public class DateTimeFormatters {
    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z".
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>Milliseconds are optional (0-3 digits).</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 2024-03-17T15:30:00Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.100Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.120Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.123Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)  //Format "YYYY-MM-DD".
            .appendLiteral('T')
            .append(DateTimeFormatter.ISO_LOCAL_TIME) //Format "HH:mm:ss[.SSS]".
            .appendOffset("+HH:MM", "Z")  //Forces "Z" for UTC.
            .toFormatter();

    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z", forcing milliseconds to three digits even for "000".
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>Milliseconds are always present as three decimal digits, as required by some APIs.</li>
     *     <li>Useful for APIs requiring fixed precision.</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 2024-03-17T15:30:00.000Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.100Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.120Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.123Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_MILLISECOND_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)  //Format "YYYY-MM-DD".
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE,2)
            .appendLiteral('.')
            .appendValue(ChronoField.MILLI_OF_SECOND,3)  //Always 3 digits.
            .appendOffset("+HH:MM", "Z")  //Forces "Z" for UTC.
            .toFormatter();

    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z", outputs milliseconds only if necessary.
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>Most flexible with milliseconds (0-3 digits, only shown if needed).</li>
     *     <li>Minimizes output length when possible.</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 2024-03-17T15:30:00Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.1Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.12Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.123Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_OPTIONAL_MILLISECOND_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)  //Format "YYYY-MM-DD".
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE,2)
            .optionalStart()
            .appendFraction(ChronoField.MILLI_OF_SECOND,0,3,true)  //Optional fraction.
            .optionalEnd()
            .appendOffset("+HH:MM", "Z")  //Forces "Z" for UTC.
            .toFormatter();

    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z", without fractional seconds.
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>No fractional seconds (truncated).</li>
     *     <li>Minimizes output length when possible.</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 2024-03-17T15:30:00Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_NO_FRACTION_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE,2)
            .appendOffset("+HH:MM","Z")
            .toFormatter();

    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z", forcing microseconds to six digits even for "000000".
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>Microseconds are always present as six decimal digits, as required by some APIs.</li>
     *     <li>Useful for APIs requiring fixed precision.</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 2024-03-17T15:30:00.000000Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.100000Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.120000Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.123000Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_MICROSECOND_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE,2)
            .appendLiteral('.')
            .appendValue(ChronoField.MICRO_OF_SECOND,6)  //Fixed six digits.
            .appendOffset("+HH:MM","Z")
            .toFormatter();

    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z", outputs microseconds only if necessary.
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>Most flexible with microseconds (0-6 digits, only shown if needed).</li>
     *     <li>Minimizes output length when possible.</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 2024-03-17T15:30:00Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.1Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.12Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.123Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.1234Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.12345Z}</li>
     *     <li>{@code 2024-03-17T15:30:00.123456Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_OPTIONAL_MICROSECOND_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE,2)
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND,0,6,true)  //Optional fraction.
            .optionalEnd()
            .appendOffset("+HH:MM","Z")
            .toFormatter();

    /**
     * Strict RFC 3339 format forcing "+00:00" to be "Z", compact format.
     * <p>
     *    Key features:
     * </p>
     * <ol>
     *     <li>Forces "+00:00" to be output as "Z".</li>
     *     <li>Compact format.</li>
     * </ol>
     * <p>
     *   Examples of formatted output:
     * </p>
     * <ul>
     *     <li>{@code 20240317T153000Z}</li>
     * </ul>
     */
    public static final DateTimeFormatter RFC_3339_COMPACT_OFFSET_DATE_TIME_FORMATTER=
        new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR,4)
            .appendValue(ChronoField.MONTH_OF_YEAR,2)
            .appendValue(ChronoField.DAY_OF_MONTH,2)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendValue(ChronoField.SECOND_OF_MINUTE,2)
            .appendOffset("+HHMM","Z")  // Compact offset
            .toFormatter();

    @Getter
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum Separator {
        Readable("-"," ",":",".",":"),
        Technical("-","T",":",".",":"),  //RFC3339-like!
        Compact("","T","","","");

        private final String dateSeparator;
        private final String dateTimeSeparator;
        private final String timeSeparator;
        private final String secondToFractionSeparator;
        private final String offsetSeparator;
    }

    @Getter
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum Fraction {
        None((builder,secondToFractionSeparator)->builder),
        MilliSeconds((builder,secondToFractionSeparator)->builder.appendLiteral(secondToFractionSeparator).appendValue(ChronoField.MILLI_OF_SECOND,3)),
        MicroSeconds((builder,secondToFractionSeparator)->builder.appendLiteral(secondToFractionSeparator).appendValue(ChronoField.MICRO_OF_SECOND,6)),
        NanoSeconds((builder,secondToFractionSeparator)->builder.appendLiteral(secondToFractionSeparator).appendValue(ChronoField.NANO_OF_SECOND,9)),
        OptionalMilliSeconds((builder,secondToFractionSeparator)->builder.optionalStart().appendLiteral(secondToFractionSeparator).appendFraction(ChronoField.NANO_OF_SECOND,0,3,true).optionalEnd()),
        OptionalMicroSeconds((builder,secondToFractionSeparator)->builder.optionalStart().appendLiteral(secondToFractionSeparator).appendFraction(ChronoField.NANO_OF_SECOND,0,6,true).optionalEnd()),
        OptionalNanoSeconds((builder,secondToFractionSeparator)->builder.optionalStart().appendLiteral(secondToFractionSeparator).appendFraction(ChronoField.NANO_OF_SECOND,0,9,true).optionalEnd());

        private final BiFunction<DateTimeFormatterBuilder,String,DateTimeFormatterBuilder> operator;
    }

    @Getter
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum Offset {
        Zulu((builder,offsetSeparator)->builder.appendLiteral('Z')),
        CompactHourMinute((builder,offsetSeparator)->builder.appendOffset(String.format("+HH%sMM",offsetSeparator),"Z")),
        HourMinute((builder,offsetSeparator)->builder.appendOffset(String.format("+HH%sMM",offsetSeparator),String.format("+00%s00",offsetSeparator)));

        private final BiFunction<DateTimeFormatterBuilder,String,DateTimeFormatterBuilder> operator;
    }

    public static DateTimeFormatter createDateTimeFormatter(Separator separator,
                                                            Fraction fraction,
                                                            Offset offset) {
        return builder().separator(separator).fraction(fraction).offset(offset).build();
    }

    @lombok.Builder(builderClassName="Builder")
    private static DateTimeFormatter createDateTimeFormatter(String dateSeparator,
                                                             String dateTimeSeparator,
                                                             String timeSeparator,
                                                             String secondToFractionSeparator,
                                                             String offsetSeparator,
                                                             BiFunction<DateTimeFormatterBuilder,String,DateTimeFormatterBuilder> fractionOperator,
                                                             BiFunction<DateTimeFormatterBuilder,String,DateTimeFormatterBuilder> offsetOperator) {
        DateTimeFormatterBuilder builder=new DateTimeFormatterBuilder();
        builder
            .appendValue(ChronoField.YEAR,4)
            .appendLiteral(dateSeparator)                     //E.g. { "-", "" }.
            .appendValue(ChronoField.MONTH_OF_YEAR,2)
            .appendLiteral(dateSeparator)                     //E.g. { "-", "" }.
            .appendValue(ChronoField.DAY_OF_MONTH,2)
            .appendLiteral(dateTimeSeparator)                 //E.g. { "T", " ", "" }.
            .appendValue(ChronoField.HOUR_OF_DAY,2)
            .appendLiteral(timeSeparator)                     //E.g. { ":", "" }.
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .appendLiteral(timeSeparator)                     //E.g. { ":", "" }.
            .appendValue(ChronoField.SECOND_OF_MINUTE,2);
        builder=fractionOperator.apply(builder,secondToFractionSeparator);
        builder=offsetOperator.apply(builder,offsetSeparator);
        return builder.toFormatter();
    }

    public static class Builder {
        public Builder separator(Separator separator) {
            return this
                .dateSeparator(separator.dateSeparator)
                .dateTimeSeparator(separator.dateTimeSeparator)
                .timeSeparator(separator.timeSeparator)
                .secondToFractionSeparator(separator.secondToFractionSeparator)
                .offsetSeparator(separator.offsetSeparator);
        }

        public Builder fraction(Fraction fraction) {
            return fractionOperator(fraction.operator);
        }

        public Builder offset(Offset offset) {
            return offsetOperator(offset.operator);
        }
    }
}
