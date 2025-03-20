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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test of {@link DateTimeFormatters}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-17
 */
class DateTimeFormattersTest {

    static Stream<Arguments> argumentsForRFC3339OffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.1Z"),
            Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.12Z"),
            Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.123456789+00:00","2024-03-17T15:30:00.123456789Z"),
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.1-02:00"),
            Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.12+03:00"),
            Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForRFC3339OffsetDateTimeFormatter")
    void formattingForRFC3339OffsetDateTimeFormatter(String input,
                                                     String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC3339_OFFSET_DATE_TIME_FORMATTER);
        assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFixedFractionRFC3339OffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000Z"),
            Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.100Z"),
            Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.120Z"),
            Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.123456789+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00.000+01:00"),
            Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.100-02:00"),
            Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.120+03:00"),
            Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForFixedFractionRFC3339OffsetDateTimeFormatter")
    void formattingForFixedFractionRFC3339OffsetDateTimeFormatter(String input,
                                                                  String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.FIXED_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER);
        assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForOptionalFractionRFC3339OffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.1Z"),
            Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.12Z"),
            Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.123456789+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.1-02:00"),
            Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.12+03:00"),
            Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForOptionalFractionRFC3339OffsetDateTimeFormatter")
    void formattingForOptionalFractionRFC3339OffsetDateTimeFormatter(String input,
                                                                     String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.OPTIONAL_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER);
        assertEquals(expectedOutput,actualOutput);
    }







    // Test parsing "+00:00" and "Z" handling for RFC3339_OFFSET_DATE_TIME_FORMATTER
    @Test
    void testParsingWithDifferentOffsets() {
        String dateTimeStr1 = "2024-03-17T15:30:00+00:00";
        String dateTimeStr2 = "2024-03-17T15:30:00Z";

        OffsetDateTime parsed1 = OffsetDateTime.parse(dateTimeStr1, DateTimeFormatters.RFC3339_OFFSET_DATE_TIME_FORMATTER);
        OffsetDateTime parsed2 = OffsetDateTime.parse(dateTimeStr2, DateTimeFormatters.RFC3339_OFFSET_DATE_TIME_FORMATTER);

        OffsetDateTime expectedTime = OffsetDateTime.parse("2024-03-17T15:30:00+00:00");

        assertEquals(expectedTime, parsed1);
        assertEquals(expectedTime, parsed2);
    }
}
