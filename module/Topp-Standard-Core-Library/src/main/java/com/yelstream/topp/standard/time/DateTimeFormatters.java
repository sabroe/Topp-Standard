/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

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
    public static final DateTimeFormatter RFC3339_OFFSET_DATE_TIME_FORMATTER=
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
    public static final DateTimeFormatter FIXED_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER=
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
    public static final DateTimeFormatter OPTIONAL_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER=
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
}
