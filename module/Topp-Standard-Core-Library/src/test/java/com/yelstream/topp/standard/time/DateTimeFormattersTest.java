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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

/**
 * Test of {@link DateTimeFormatters}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-17
 */
class DateTimeFormattersTest {
    static Stream<Arguments> argumentsForFormattingUsingRFC3339OffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForFormattingUsingRFC3339OffsetDateTimeFormatter")
    void formattingUsingRFC3339OffsetDateTimeFormatter(String input,
                                                       String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingFixedFractionRFC3339OffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForFormattingUsingFixedFractionRFC3339OffsetDateTimeFormatter")
    void formattingUsingFixedFractionRFC3339OffsetDateTimeFormatter(String input,
                                                                    String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.FIXED_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingOptionalFractionRFC3339OffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForFormattingUsingOptionalFractionRFC3339OffsetDateTimeFormatter")
    void formattingUsingOptionalFractionRFC3339OffsetDateTimeFormatter(String input,
                                                                       String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.OPTIONAL_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForParsingUsingRFC3339OffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000+00:00"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000Z"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00+00:00"),
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
    @MethodSource("argumentsForParsingUsingRFC3339OffsetDateTimeFormatter")
    void parsingUsingRFC3339OffsetDateTimeFormatter(String expectedValue,
                                                    String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_OFFSET_DATE_TIME_FORMATTER;
        if (expectedValue==null) {
            Assertions.assertThrows(DateTimeParseException.class,()->{
                OffsetDateTime.parse(formattedInput,formatter);
            });
        } else {
            OffsetDateTime actual=OffsetDateTime.parse(formattedInput,formatter);
            OffsetDateTime expected=OffsetDateTime.parse(expectedValue);
            Assertions.assertEquals(expected,actual);
        }
    }

    static Stream<Arguments> argumentsForParsingUsingFixedFractionRFC3339OffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000+00:00"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000Z"),
            Arguments.of(null                           ,"2024-03-17T15:30:00+00:00"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000Z"),
            Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.100Z"),
            Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.120Z"),
            Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of(null                           ,"2024-03-17T15:30:00.123456789Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00.000+01:00"),
            Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.100-02:00"),
            Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.120+03:00"),
            Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForParsingUsingFixedFractionRFC3339OffsetDateTimeFormatter")
    void parsingUsingFixedFractionRFC3339OffsetDateTimeFormatter(String expectedValue,
                                                                 String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.FIXED_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER;
        if (expectedValue==null) {
            Assertions.assertThrows(DateTimeParseException.class,()->{
                OffsetDateTime.parse(formattedInput,formatter);
            });
        } else {
            OffsetDateTime actual=OffsetDateTime.parse(formattedInput,formatter);
            OffsetDateTime expected=OffsetDateTime.parse(expectedValue);
            Assertions.assertEquals(expected,actual);
        }
    }

    static Stream<Arguments> argumentsForParsingUsingOptionalFractionRFC3339OffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000+00:00"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000Z"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00+00:00"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.1Z"),
            Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.12Z"),
            Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of(null                           ,"2024-03-17T15:30:00.123456789Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.1-02:00"),
            Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.12+03:00"),
            Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForParsingUsingOptionalFractionRFC3339OffsetDateTimeFormatter")
    void parsingUsingOptionalFractionRFC3339OffsetDateTimeFormatter(String expectedValue,
                                                                    String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.OPTIONAL_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER;
        if (expectedValue==null) {
            Assertions.assertThrows(DateTimeParseException.class,()->{
                OffsetDateTime.parse(formattedInput,formatter);
            });
        } else {
            OffsetDateTime actual=OffsetDateTime.parse(formattedInput,formatter);
            OffsetDateTime expected=OffsetDateTime.parse(expectedValue);
            Assertions.assertEquals(expected,actual);
        }
    }
}
