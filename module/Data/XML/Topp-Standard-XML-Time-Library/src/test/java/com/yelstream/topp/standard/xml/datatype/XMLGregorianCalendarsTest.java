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

package com.yelstream.topp.standard.xml.datatype;

import com.yelstream.topp.standard.xml.datatype.XMLGregorianCalendars;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

/**
 * Unit tests for {@link XMLGregorianCalendars} builder methods.
 */
class XMLGregorianCalendarsTest {
    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendar()}.
     */
    @Test
    void createGregorianCalendar() {
        XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendar();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
        Assertions.assertNull(calendar.getFractionalSecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendar(GregorianCalendar)}.
     */
    @Test
    void createGregorianCalendarFromGregorianCalendar() {
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45+01:00");
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendar(gregorianCalendar);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(2025, calendar.getYear());
            Assertions.assertEquals(4, calendar.getMonth());
            Assertions.assertEquals(21, calendar.getDay());
            Assertions.assertEquals(15, calendar.getHour());
            Assertions.assertEquals(30, calendar.getMinute());
            Assertions.assertEquals(45, calendar.getSecond());
            Assertions.assertEquals(60, calendar.getTimezone());
        }
        {
            Assertions.assertThrows(NullPointerException.class, () -> {
                XMLGregorianCalendars.createGregorianCalendar((GregorianCalendar) null);
            }, "Should throw NullPointerException for null input");
        }
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendar(ZonedDateTime)}.
     */
    @Test
    void createGregorianCalendarFromZonedDateTime() {
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45.123+01:00");

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendar(zonedDateTime);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(2025, calendar.getYear());
            Assertions.assertEquals(4, calendar.getMonth());
            Assertions.assertEquals(21, calendar.getDay());
            Assertions.assertEquals(15, calendar.getHour());
            Assertions.assertEquals(30, calendar.getMinute());
            Assertions.assertEquals(45, calendar.getSecond());
            Assertions.assertEquals(new java.math.BigDecimal("0.123"), calendar.getFractionalSecond());
            Assertions.assertEquals(60, calendar.getTimezone());
        }
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45.123456789+01:00");
            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendar(zonedDateTime);
            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(new java.math.BigDecimal("0.123"), calendar.getFractionalSecond());
        }
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45+01:00");
            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendar(zonedDateTime);
            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(45, calendar.getSecond());
            Assertions.assertEquals(new java.math.BigDecimal("0.000"), calendar.getFractionalSecond());
        }
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendarWithNanoseconds(ZonedDateTime)}.
     */
    @Test
    void createGregorianCalendarFromZonedDateWithNanoseconds() {
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45.123456789+01:00");
            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarWithNanoseconds(zonedDateTime);
            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(new java.math.BigDecimal("0.123456789"), calendar.getFractionalSecond());
        }
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45.000+01:00");
            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarWithNanoseconds(zonedDateTime);
            Assertions.assertNotNull(calendar);
            Assertions.assertNull(calendar.getFractionalSecond());
        }
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45+01:00");
            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarWithNanoseconds(zonedDateTime);
            Assertions.assertNotNull(calendar);
            Assertions.assertNull(calendar.getFractionalSecond());
        }
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendar(ZonedDateTime, DateTimeFormatter)}.
     */
    @Test
    void createGregorianCalendarFromZonedDateTimeWithFormatter() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-04-21T15:30:45.123456789+01:00");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendar(zonedDateTime, formatter);

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(21, calendar.getDay());
        Assertions.assertEquals(15, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(new java.math.BigDecimal("0.123456789"), calendar.getFractionalSecond());
        Assertions.assertEquals(60, calendar.getTimezone());
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendarDate(LocalDate)}.
     */
    @Test
    void createGregorianCalendarDate() {
        LocalDate date = LocalDate.of(2025, 4, 21);

        XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarDate(date);

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(21, calendar.getDay());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
        Assertions.assertNull(calendar.getFractionalSecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendarTimeWithMilliseconds(LocalTime)}.
     */
    @Test
    void createGregorianCalendarTimeWithMilliseconds() {
        {
            LocalTime time = LocalTime.of(15, 30, 45, 123_456_789);

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarTimeWithMilliseconds(time);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
            Assertions.assertEquals(15, calendar.getHour());
            Assertions.assertEquals(30, calendar.getMinute());
            Assertions.assertEquals(45, calendar.getSecond());
            Assertions.assertEquals(123, calendar.getMillisecond());
            Assertions.assertEquals(new java.math.BigDecimal("0.123"), calendar.getFractionalSecond());
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
        }
        {
            LocalTime time = LocalTime.of(15, 30, 45);

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarTimeWithMilliseconds(time);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(new java.math.BigDecimal("0.000"), calendar.getFractionalSecond());
        }
    }

    /**
     * Tests {@link XMLGregorianCalendars#createGregorianCalendarTimeWithNanoseconds(LocalTime)}.
     */
    @Test
    void createGregorianCalendarTimeWithNanoseconds() {
        {
            LocalTime time = LocalTime.of(15, 30, 45, 123_456_789);

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarTimeWithNanoseconds(time);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
            Assertions.assertEquals(15, calendar.getHour());
            Assertions.assertEquals(30, calendar.getMinute());
            Assertions.assertEquals(45, calendar.getSecond());
            Assertions.assertEquals(new BigDecimal("0.123456789"), calendar.getFractionalSecond());
            Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
        }
        {
            LocalTime time = LocalTime.of(15, 30, 45);

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarTimeWithNanoseconds(time);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(new BigDecimal("0.000000000"), calendar.getFractionalSecond());
        }
        {
            LocalTime time = LocalTime.of(15, 30, 45, 123_000_000);

            XMLGregorianCalendar calendar = XMLGregorianCalendars.createGregorianCalendarTimeWithNanoseconds(time);

            Assertions.assertNotNull(calendar);
            Assertions.assertEquals(new BigDecimal("0.123000000"), calendar.getFractionalSecond());
        }
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder creates empty calendar when no fields are set")
    void fullGregorianCalendarBuilder_empty() {
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .gregorianCalendarBuilder()
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder creates calendar from ZonedDateTime")
    void fullGregorianCalendarBuilder_zonedDateTime() {
        // Test with nanosecond precision
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2025, 4, 14, 14, 30, 45, 123_456_789, ZoneId.of("UTC"));
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .gregorianCalendarBuilder()
                .zonedDateTime(zonedDateTime)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(14, calendar.getDay());
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(BigDecimal.valueOf(123_456_789, 9), calendar.getFractionalSecond());
        Assertions.assertEquals(0, calendar.getTimezone());

        // Test with zero nanoseconds
        ZonedDateTime noNanoDateTime = ZonedDateTime.of(2025, 4, 14, 14, 30, 45, 0, ZoneId.of("UTC"));
        XMLGregorianCalendar noNanoCalendar = XMLGregorianCalendars
                .gregorianCalendarBuilder()
                .zonedDateTime(noNanoDateTime)
                .build();

        Assertions.assertNotNull(noNanoCalendar);
        Assertions.assertEquals(2025, noNanoCalendar.getYear());
        Assertions.assertEquals(4, noNanoCalendar.getMonth());
        Assertions.assertEquals(14, noNanoCalendar.getDay());
        Assertions.assertEquals(14, noNanoCalendar.getHour());
        Assertions.assertEquals(30, noNanoCalendar.getMinute());
        Assertions.assertEquals(45, noNanoCalendar.getSecond());
        Assertions.assertNull(noNanoCalendar.getFractionalSecond()); // No fractional second when nano = 0
        Assertions.assertEquals(0, noNanoCalendar.getTimezone());
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder creates calendar from GregorianCalendar")
    void fullGregorianCalendarBuilder_gregorianCalendar() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2025, 3, 14, 14, 30, 45); // April 14 (month is 0-based)
        gregorianCalendar.setGregorianChange(new java.util.Date(Long.MIN_VALUE)); // Ensure pure Gregorian
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .gregorianCalendarBuilder()
                .gregorianCalendar(gregorianCalendar)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(14, calendar.getDay());
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder prefers ZonedDateTime over GregorianCalendar")
    void fullGregorianCalendarBuilder_preferZonedDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2025, 4, 14, 14, 30, 45, 0, ZoneId.of("UTC"));
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2024, 3, 15, 15, 31, 46);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .gregorianCalendarBuilder()
                .zonedDateTime(zonedDateTime)
                .gregorianCalendar(gregorianCalendar)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(14, calendar.getDay());
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
    }

    // --- Tests for DateGregorianCalendarBuilder ---

    @Test
    @DisplayName("DateGregorianCalendarBuilder creates date from LocalDate")
    void dateGregorianCalendarBuilder_localDate() {
        LocalDate date = LocalDate.of(2025, 4, 14);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .dateGregorianCalendarBuilder()
                .date(date)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(14, calendar.getDay());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    @Test
    @DisplayName("DateGregorianCalendarBuilder creates date from components")
    void dateGregorianCalendarBuilder_components() {
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .dateGregorianCalendarBuilder()
                .year(2025)
                .month(4)
                .day(14)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(14, calendar.getDay());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    @Test
    @DisplayName("DateGregorianCalendarBuilder prefers LocalDate over components")
    void dateGregorianCalendarBuilder_preferLocalDate() {
        LocalDate date = LocalDate.of(2025, 4, 14);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .dateGregorianCalendarBuilder()
                .date(date)
                .year(2024)
                .month(5)
                .day(15)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(2025, calendar.getYear());
        Assertions.assertEquals(4, calendar.getMonth());
        Assertions.assertEquals(14, calendar.getDay());
    }

    @Test
    @DisplayName("DateGregorianCalendarBuilder throws exception for missing inputs")
    void dateGregorianCalendarBuilder_missingInputs() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .dateGregorianCalendarBuilder()
                    .build();
        });
        Assertions.assertEquals("Either date or year/month/day must be provided.", exception.getMessage());

        // Partial components
        Exception partialException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .dateGregorianCalendarBuilder()
                    .year(2025)
                    .month(4)
                    .build();
        });
        Assertions.assertEquals("Either date or year/month/day must be provided.", partialException.getMessage());
    }

    // --- Tests for TimeGregorianCalendarBuilder ---

    @Test
    @DisplayName("TimeGregorianCalendarBuilder creates time with millisecond precision from LocalTime")
    void timeGregorianCalendarBuilder_localTime_milliseconds() {
        LocalTime time = LocalTime.of(14, 30, 45, 123_000_000); // 123ms
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .timeGregorianCalendarBuilder()
                .time(time)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(123, calendar.getMillisecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    @Test
    @DisplayName("TimeGregorianCalendarBuilder creates time with nanosecond precision from LocalTime")
    void timeGregorianCalendarBuilder_localTime_nanoseconds() {
        LocalTime time = LocalTime.of(14, 30, 45, 123_456_789);
        BigDecimal fractionalSecond = BigDecimal.valueOf(123_456_789, 9);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .timeGregorianCalendarBuilder()
                .time(time)
                .fractionalSecond(fractionalSecond)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(fractionalSecond, calendar.getFractionalSecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    @Test
    @DisplayName("TimeGregorianCalendarBuilder creates time from components with milliseconds")
    void timeGregorianCalendarBuilder_components_milliseconds() {
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .timeGregorianCalendarBuilder()
                .hour(14)
                .minute(30)
                .second(45)
                .millisecond(123)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(123, calendar.getMillisecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    @Test
    @DisplayName("TimeGregorianCalendarBuilder creates time from components with fractional seconds")
    void timeGregorianCalendarBuilder_components_fractionalSeconds() {
        BigDecimal fractionalSecond = BigDecimal.valueOf(123_456_789, 9);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .timeGregorianCalendarBuilder()
                .hour(14)
                .minute(30)
                .second(45)
                .fractionalSecond(fractionalSecond)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(fractionalSecond, calendar.getFractionalSecond());
        Assertions.assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
    }

    @Test
    @DisplayName("TimeGregorianCalendarBuilder prefers LocalTime over components")
    void timeGregorianCalendarBuilder_preferLocalTime() {
        LocalTime time = LocalTime.of(14, 30, 45, 123_000_000);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .timeGregorianCalendarBuilder()
                .time(time)
                .hour(15)
                .minute(31)
                .second(46)
                .millisecond(456)
                .build();

        Assertions.assertNotNull(calendar);
        Assertions.assertEquals(14, calendar.getHour());
        Assertions.assertEquals(30, calendar.getMinute());
        Assertions.assertEquals(45, calendar.getSecond());
        Assertions.assertEquals(123, calendar.getMillisecond());
    }

    @Test
    @DisplayName("TimeGregorianCalendarBuilder throws exception for missing inputs")
    void timeGregorianCalendarBuilder_missingInputs() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .timeGregorianCalendarBuilder()
                    .build();
        });
        Assertions.assertEquals("Either time or hour/minute/second must be provided.", exception.getMessage());

        // Partial components
        Exception partialException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .timeGregorianCalendarBuilder()
                    .hour(14)
                    .minute(30)
                    .build();
        });
        Assertions.assertEquals("Either time or hour/minute/second must be provided.", partialException.getMessage());
    }
}
