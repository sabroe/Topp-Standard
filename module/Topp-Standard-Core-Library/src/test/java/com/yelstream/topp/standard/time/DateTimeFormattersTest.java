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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test of {@link DateTimeFormatters}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-17
 */
class DateTimeFormattersTest {

    // Test Formatting
    @ParameterizedTest
    @ValueSource(strings = {
            "2024-03-17T15:30:00+00:00,000", // Format: date-time, milliseconds
            "2024-03-17T15:30:00+00:00,100",
            "2024-03-17T15:30:00+00:00,120",
            "2024-03-17T15:30:00+00:00,123"
    })
    void testFormatting(String input) {
        // Split the input into date-time and milliseconds
        String[] parts = input.split(",");
        String dateTimeStr = parts[0];
        String milliseconds = parts[1];

        // Parse the input string
        OffsetDateTime baseTime = OffsetDateTime.parse(dateTimeStr);

        // Expected formatted strings
        String expectedRFC3339 = "2024-03-17T15:30:00" + (milliseconds.equals("000") ? "" : "." + milliseconds.replaceAll("(?!^)0+$","")) + "Z";
        String expectedFixedFraction = "2024-03-17T15:30:00." + milliseconds + "Z";
        String expectedOptionalFraction = "2024-03-17T15:30:00" + (milliseconds.equals("000") ? "" : "." + milliseconds) + "Z";

        // Check that all formatters produce the correct string
        assertEquals(expectedRFC3339, baseTime.format(DateTimeFormatters.RFC3339_OFFSET_DATE_TIME_FORMATTER));
        assertEquals(expectedFixedFraction, baseTime.format(DateTimeFormatters.FIXED_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER));
        assertEquals(expectedOptionalFraction, baseTime.format(DateTimeFormatters.OPTIONAL_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER));
    }

    // Test Parsing
    @ParameterizedTest
    @ValueSource(strings = {
            "2024-03-17T15:30:00+00:00,000",
            "2024-03-17T15:30:00+00:00,100",
            "2024-03-17T15:30:00+00:00,120",
            "2024-03-17T15:30:00+00:00,123"
    })
    void testParsing(String input) {
        // Split the input into date-time and milliseconds
        String[] parts = input.split(",");
        String dateTimeStr = parts[0];
        String milliseconds = parts[1];

        // Parse with each formatter and compare the parsed value
        OffsetDateTime parsedRFC3339 = OffsetDateTime.parse(dateTimeStr, DateTimeFormatters.RFC3339_OFFSET_DATE_TIME_FORMATTER);
        OffsetDateTime parsedFixedFraction = OffsetDateTime.parse(dateTimeStr, DateTimeFormatters.FIXED_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER);
        OffsetDateTime parsedOptionalFraction = OffsetDateTime.parse(dateTimeStr, DateTimeFormatters.OPTIONAL_FRACTION_RFC3339_OFFSET_DATE_TIME_FORMATTER);

        OffsetDateTime expectedTime = OffsetDateTime.parse("2024-03-17T15:30:00" + (milliseconds.equals("000") ? "" : "." + milliseconds) + "Z");

        assertEquals(expectedTime, parsedRFC3339);
        assertEquals(expectedTime, parsedFixedFraction);
        assertEquals(expectedTime, parsedOptionalFraction);
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
