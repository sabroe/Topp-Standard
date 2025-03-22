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

    static Stream<Arguments> argumentsForFormattingUsingRFC3339MillisecondOffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForFormattingUsingRFC3339MillisecondOffsetDateTimeFormatter")
    void formattingUsingRFC3339MillisecondOffsetDateTimeFormatter(String input,
                                                                  String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_MILLISECOND_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingRFC3339OptionalMillisecondOffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForFormattingUsingRFC3339OptionalMillisecondOffsetDateTimeFormatter")
    void formattingUsingRFC3339OptionalMillisecondOffsetDateTimeFormatter(String input,
                                                                          String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_OPTIONAL_MILLISECOND_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingRFC3339NoFractionOffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.123456789+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00-02:00"),
            Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00+03:00"),
            Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForFormattingUsingRFC3339NoFractionOffsetDateTimeFormatter")
    void formattingUsingRFC3339NoFractionRFC3339OffsetDateTimeFormatter(String input,
                                                                        String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_NO_FRACTION_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingRFC3339MicrosecondOffsetDateTimeFormatter() {
        return Stream.of(
                Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000000Z"),
                Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.100000Z"),
                Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.120000Z"),
                Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123000Z"),
                Arguments.of("2024-03-17T15:30:00.123456789+00:00","2024-03-17T15:30:00.123456Z"),
                Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00.000000+01:00"),
                Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.100000-02:00"),
                Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.120000+03:00"),
                Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123000-04:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForFormattingUsingRFC3339MicrosecondOffsetDateTimeFormatter")
    void formattingUsingRFC3339MicrosecondOffsetDateTimeFormatter(String input,
                                                                  String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_MICROSECOND_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingRFC3339OptionalMicrosecondOffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.100000+00:00","2024-03-17T15:30:00.1Z"),
            Arguments.of("2024-03-17T15:30:00.120000+00:00","2024-03-17T15:30:00.12Z"),
            Arguments.of("2024-03-17T15:30:00.123000+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.123400+00:00","2024-03-17T15:30:00.1234Z"),
            Arguments.of("2024-03-17T15:30:00.123450+00:00","2024-03-17T15:30:00.12345Z"),
            Arguments.of("2024-03-17T15:30:00.123456+00:00","2024-03-17T15:30:00.123456Z"),
            Arguments.of("2024-03-17T15:30:00.123456789+00:00","2024-03-17T15:30:00.123456Z"),
            Arguments.of("2024-03-17T15:30:00.000000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of("2024-03-17T15:30:00.100000-02:00","2024-03-17T15:30:00.1-02:00"),
            Arguments.of("2024-03-17T15:30:00.120000+03:00","2024-03-17T15:30:00.12+03:00"),
            Arguments.of("2024-03-17T15:30:00.123000-04:00","2024-03-17T15:30:00.123-04:00"),
            Arguments.of("2024-03-17T15:30:00.123400+05:00","2024-03-17T15:30:00.1234+05:00"),
            Arguments.of("2024-03-17T15:30:00.123450-06:00","2024-03-17T15:30:00.12345-06:00"),
            Arguments.of("2024-03-17T15:30:00.123456+07:00","2024-03-17T15:30:00.123456+07:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForFormattingUsingRFC3339OptionalMicrosecondOffsetDateTimeFormatter")
    void formattingUsingRFC3339OptionalMicrosecondOffsetDateTimeFormatter(String input,
                                                                          String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_OPTIONAL_MICROSECOND_OFFSET_DATE_TIME_FORMATTER);
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    static Stream<Arguments> argumentsForFormattingUsingRFC3339CompactOffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000000+00:00","20240317T153000Z"),
            Arguments.of("2024-03-17T15:30:00.100000+00:00","20240317T153000Z"),
            Arguments.of("2024-03-17T15:30:00.120000+00:00","20240317T153000Z"),
            Arguments.of("2024-03-17T15:30:00.123000+00:00","20240317T153000Z"),
            Arguments.of("2024-03-17T15:30:00.123456789+00:00","20240317T153000Z"),
            Arguments.of("2024-03-17T15:30:00.000000+01:00","20240317T153000+0100"),
            Arguments.of("2024-03-17T15:30:00.100000-02:00","20240317T153000-0200"),
            Arguments.of("2024-03-17T15:30:00.120000+03:00","20240317T153000+0300"),
            Arguments.of("2024-03-17T15:30:00.123000-04:00","20240317T153000-0400")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForFormattingUsingRFC3339CompactOffsetDateTimeFormatter")
    void formattingUsingRFC3339CompactOffsetDateTimeFormatter(String input,
                                                              String expectedOutput) {
        OffsetDateTime baseTime=OffsetDateTime.parse(input);
        String actualOutput=baseTime.format(DateTimeFormatters.RFC_3339_COMPACT_OFFSET_DATE_TIME_FORMATTER);
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

    static Stream<Arguments> argumentsForParsingUsingRFC3339MillisecondOffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForParsingUsingRFC3339MillisecondOffsetDateTimeFormatter")
    void parsingUsingRFC3339MillisecondOffsetDateTimeFormatter(String expectedValue,
                                                               String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_MILLISECOND_OFFSET_DATE_TIME_FORMATTER;
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

    static Stream<Arguments> argumentsForParsingUsingRFC3339OptionalMillisecondOffsetDateTimeFormatter() {
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
    @MethodSource("argumentsForParsingUsingRFC3339OptionalMillisecondOffsetDateTimeFormatter")
    void parsingUsingRFC3339OptionalMillisecondOffsetDateTimeFormatter(String expectedValue,
                                                                       String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_OPTIONAL_MILLISECOND_OFFSET_DATE_TIME_FORMATTER;
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

    static Stream<Arguments> argumentsForParsingUsingRFC3339NoFractionOffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of(null                           ,"2024-03-17T15:30:00.000+00:00"),  //DateTimeParseException
            Arguments.of(null                           ,"2024-03-17T15:30:00.000Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00+00:00"),
            Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of(null                           ,"2024-03-17T15:30:00.1Z"),  //DateTimeParseException
            Arguments.of(null                           ,"2024-03-17T15:30:00.12Z"),  //DateTimeParseException
            Arguments.of(null                           ,"2024-03-17T15:30:00.123Z"),  //DateTimeParseException
            Arguments.of(null                           ,"2024-03-17T15:30:00.123456789Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of(null                           ,"2024-03-17T15:30:00.1-02:00"),  //DateTimeParseException
            Arguments.of(null                           ,"2024-03-17T15:30:00.12+03:00"),  //DateTimeParseException
            Arguments.of(null                           ,"2024-03-17T15:30:00.123-04:00")  //DateTimeParseException
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForParsingUsingRFC3339NoFractionOffsetDateTimeFormatter")
    void parsingUsingRFC3339NoFractionOffsetDateTimeFormatter(String expectedValue,
                                                              String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_NO_FRACTION_OFFSET_DATE_TIME_FORMATTER;
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

    static Stream<Arguments> argumentsForParsingUsingRFC3339MicrosecondOffsetDateTimeFormatter() {
        return Stream.of(
                Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000000+00:00"),
                Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000000Z"),
                Arguments.of(null                           ,"2024-03-17T15:30:00+00:00"),  //DateTimeParseException
                Arguments.of("2024-03-17T15:30:00.000+00:00","2024-03-17T15:30:00.000000Z"),
                Arguments.of("2024-03-17T15:30:00.100+00:00","2024-03-17T15:30:00.100000Z"),
                Arguments.of("2024-03-17T15:30:00.120+00:00","2024-03-17T15:30:00.120000Z"),
                Arguments.of("2024-03-17T15:30:00.123+00:00","2024-03-17T15:30:00.123000Z"),
                Arguments.of("2024-03-17T15:30:00.123456+00:00","2024-03-17T15:30:00.123456Z"),
                Arguments.of(null                           ,"2024-03-17T15:30:00.123456789Z"),  //DateTimeParseException
                Arguments.of("2024-03-17T15:30:00.000+01:00","2024-03-17T15:30:00.000000+01:00"),
                Arguments.of("2024-03-17T15:30:00.100-02:00","2024-03-17T15:30:00.100000-02:00"),
                Arguments.of("2024-03-17T15:30:00.120+03:00","2024-03-17T15:30:00.120000+03:00"),
                Arguments.of("2024-03-17T15:30:00.123-04:00","2024-03-17T15:30:00.123000-04:00"),
                Arguments.of("2024-03-17T15:30:00.123456-05:00","2024-03-17T15:30:00.123456-05:00"),
                Arguments.of(null                           ,"2024-03-17T15:30:00.123456768-05:00")  //DateTimeParseException
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForParsingUsingRFC3339MicrosecondOffsetDateTimeFormatter")
    void parsingUsingRFC3339MicrosecondOffsetDateTimeFormatter(String expectedValue,
                                                               String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_MICROSECOND_OFFSET_DATE_TIME_FORMATTER;
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

    static Stream<Arguments> argumentsForParsingUsingRFC3339OptionalMicrosecondOffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of("2024-03-17T15:30:00.000000+00:00","2024-03-17T15:30:00.000+00:00"),
            Arguments.of("2024-03-17T15:30:00.000000+00:00","2024-03-17T15:30:00.000Z"),
            Arguments.of("2024-03-17T15:30:00.000000+00:00","2024-03-17T15:30:00+00:00"),
            Arguments.of("2024-03-17T15:30:00.000000+00:00","2024-03-17T15:30:00Z"),
            Arguments.of("2024-03-17T15:30:00.100000+00:00","2024-03-17T15:30:00.1Z"),
            Arguments.of("2024-03-17T15:30:00.120000+00:00","2024-03-17T15:30:00.12Z"),
            Arguments.of("2024-03-17T15:30:00.123000+00:00","2024-03-17T15:30:00.123Z"),
            Arguments.of("2024-03-17T15:30:00.123400+00:00","2024-03-17T15:30:00.1234Z"),
            Arguments.of("2024-03-17T15:30:00.123450+00:00","2024-03-17T15:30:00.12345Z"),
            Arguments.of("2024-03-17T15:30:00.123456+00:00","2024-03-17T15:30:00.123456Z"),
            Arguments.of(null                           ,"2024-03-17T15:30:00.123456789Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000000+01:00","2024-03-17T15:30:00+01:00"),
            Arguments.of("2024-03-17T15:30:00.100000-02:00","2024-03-17T15:30:00.1-02:00"),
            Arguments.of("2024-03-17T15:30:00.120000+03:00","2024-03-17T15:30:00.12+03:00"),
            Arguments.of("2024-03-17T15:30:00.123000-04:00","2024-03-17T15:30:00.123-04:00"),
            Arguments.of("2024-03-17T15:30:00.123400-05:00","2024-03-17T15:30:00.1234-05:00"),
            Arguments.of("2024-03-17T15:30:00.123450-06:00","2024-03-17T15:30:00.12345-06:00"),
            Arguments.of("2024-03-17T15:30:00.123456-07:00","2024-03-17T15:30:00.123456-07:00")
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForParsingUsingRFC3339OptionalMicrosecondOffsetDateTimeFormatter")
    void parsingUsingRFC3339OptionalMicrosecondOffsetDateTimeFormatter(String expectedValue,
                                                                       String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_OPTIONAL_MICROSECOND_OFFSET_DATE_TIME_FORMATTER;
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

    static Stream<Arguments> argumentsForParsingUsingRFC3339CompactOffsetDateTimeFormatter() {
        return Stream.of(
            Arguments.of(null                              ,"2024-03-17T15:30:00.000+00:00"),  //DateTimeParseException
            Arguments.of(null                              ,"2024-03-17T15:30:00.000Z"),  //DateTimeParseException
            Arguments.of(null                              ,"20240317T153000.000+00:00"),  //DateTimeParseException
            Arguments.of(null                              ,"20240317T153000.000Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000000+00:00","20240317T153000+0000"),
            Arguments.of("2024-03-17T15:30:00.000000+00:00","20240317T153000Z"),
            Arguments.of(null                              ,"20240317T153000.1Z"),  //DateTimeParseException
            Arguments.of(null                              ,"20240317T153000.12Z"),  //DateTimeParseException
            Arguments.of(null                              ,"20240317T153000.123Z"),  //DateTimeParseException
            Arguments.of(null                              ,"2024-03-17T15:30:00.123456789Z"),  //DateTimeParseException
            Arguments.of("2024-03-17T15:30:00.000000+01:00","20240317T153000+0100"),
            Arguments.of(null                              ,"20240317T153000.1-0200"),  //DateTimeParseException
            Arguments.of(null                              ,"20240317T153000.12+0300"),  //DateTimeParseException
            Arguments.of(null                              ,"20240317T153000.123-0400")  //DateTimeParseException
        );
    }
    @ParameterizedTest
    @MethodSource("argumentsForParsingUsingRFC3339CompactOffsetDateTimeFormatter")
    void parsingUsingRFC3339CompactOffsetDateTimeFormatter(String expectedValue,
                                                           String formattedInput) {
        DateTimeFormatter formatter=DateTimeFormatters.RFC_3339_COMPACT_OFFSET_DATE_TIME_FORMATTER;
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
