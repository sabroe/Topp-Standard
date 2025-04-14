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

package com.yelstream.topp.standard.empress.xml.datatype;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link XMLGregorianCalendars} builder methods.
 */
class XMLGregorianCalendarsTest {

    private DatatypeFactory datatypeFactory;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize DatatypeFactory (use DatatypeFactories.createDataTypeFactory() if available)
        datatypeFactory = DatatypeFactory.newInstance();
    }

    // --- Tests for FullGregorianCalendarBuilder ---

    @Test
    @DisplayName("FullGregorianCalendarBuilder creates empty calendar when no fields are set")
    void fullGregorianCalendarBuilder_empty() {
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .fullGregorianCalendarBuilder()
                .build();

        assertNotNull(calendar);
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder creates calendar from ZonedDateTime")
    void fullGregorianCalendarBuilder_zonedDateTime() {
        // Test with nanosecond precision
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2025, 4, 14, 14, 30, 45, 123_456_789, ZoneId.of("UTC"));
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .fullGregorianCalendarBuilder()
                .zonedDateTime(zonedDateTime)
                .build();

        assertNotNull(calendar);
        assertEquals(2025, calendar.getYear());
        assertEquals(4, calendar.getMonth());
        assertEquals(14, calendar.getDay());
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
        assertEquals(BigDecimal.valueOf(123_456_789, 9), calendar.getFractionalSecond());
        assertEquals(0, calendar.getTimezone());

        // Test with zero nanoseconds
        ZonedDateTime noNanoDateTime = ZonedDateTime.of(2025, 4, 14, 14, 30, 45, 0, ZoneId.of("UTC"));
        XMLGregorianCalendar noNanoCalendar = XMLGregorianCalendars
                .fullGregorianCalendarBuilder()
                .zonedDateTime(noNanoDateTime)
                .build();

        assertNotNull(noNanoCalendar);
        assertEquals(2025, noNanoCalendar.getYear());
        assertEquals(4, noNanoCalendar.getMonth());
        assertEquals(14, noNanoCalendar.getDay());
        assertEquals(14, noNanoCalendar.getHour());
        assertEquals(30, noNanoCalendar.getMinute());
        assertEquals(45, noNanoCalendar.getSecond());
        assertNull(noNanoCalendar.getFractionalSecond()); // No fractional second when nano = 0
        assertEquals(0, noNanoCalendar.getTimezone());
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder creates calendar from GregorianCalendar")
    void fullGregorianCalendarBuilder_gregorianCalendar() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2025, 3, 14, 14, 30, 45); // April 14 (month is 0-based)
        gregorianCalendar.setGregorianChange(new java.util.Date(Long.MIN_VALUE)); // Ensure pure Gregorian
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .fullGregorianCalendarBuilder()
                .gregorianCalendar(gregorianCalendar)
                .build();

        assertNotNull(calendar);
        assertEquals(2025, calendar.getYear());
        assertEquals(4, calendar.getMonth());
        assertEquals(14, calendar.getDay());
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
    }

    @Test
    @DisplayName("FullGregorianCalendarBuilder prefers ZonedDateTime over GregorianCalendar")
    void fullGregorianCalendarBuilder_preferZonedDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2025, 4, 14, 14, 30, 45, 0, ZoneId.of("UTC"));
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2024, 3, 15, 15, 31, 46);
        XMLGregorianCalendar calendar = XMLGregorianCalendars
                .fullGregorianCalendarBuilder()
                .zonedDateTime(zonedDateTime)
                .gregorianCalendar(gregorianCalendar)
                .build();

        assertNotNull(calendar);
        assertEquals(2025, calendar.getYear());
        assertEquals(4, calendar.getMonth());
        assertEquals(14, calendar.getDay());
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
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

        assertNotNull(calendar);
        assertEquals(2025, calendar.getYear());
        assertEquals(4, calendar.getMonth());
        assertEquals(14, calendar.getDay());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
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

        assertNotNull(calendar);
        assertEquals(2025, calendar.getYear());
        assertEquals(4, calendar.getMonth());
        assertEquals(14, calendar.getDay());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getHour());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMinute());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getSecond());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
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

        assertNotNull(calendar);
        assertEquals(2025, calendar.getYear());
        assertEquals(4, calendar.getMonth());
        assertEquals(14, calendar.getDay());
    }

    @Test
    @DisplayName("DateGregorianCalendarBuilder throws exception for missing inputs")
    void dateGregorianCalendarBuilder_missingInputs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .dateGregorianCalendarBuilder()
                    .build();
        });
        assertEquals("Either date or year/month/day must be provided.", exception.getMessage());

        // Partial components
        Exception partialException = assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .dateGregorianCalendarBuilder()
                    .year(2025)
                    .month(4)
                    .build();
        });
        assertEquals("Either date or year/month/day must be provided.", partialException.getMessage());
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

        assertNotNull(calendar);
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getYear());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getMonth());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getDay());
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
        assertEquals(123, calendar.getMillisecond());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
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

        assertNotNull(calendar);
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
        assertEquals(fractionalSecond, calendar.getFractionalSecond());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
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

        assertNotNull(calendar);
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
        assertEquals(123, calendar.getMillisecond());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
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

        assertNotNull(calendar);
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
        assertEquals(fractionalSecond, calendar.getFractionalSecond());
        assertEquals(DatatypeConstants.FIELD_UNDEFINED, calendar.getTimezone());
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

        assertNotNull(calendar);
        assertEquals(14, calendar.getHour());
        assertEquals(30, calendar.getMinute());
        assertEquals(45, calendar.getSecond());
        assertEquals(123, calendar.getMillisecond());
    }

    @Test
    @DisplayName("TimeGregorianCalendarBuilder throws exception for missing inputs")
    void timeGregorianCalendarBuilder_missingInputs() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .timeGregorianCalendarBuilder()
                    .build();
        });
        assertEquals("Either time or hour/minute/second must be provided.", exception.getMessage());

        // Partial components
        Exception partialException = assertThrows(IllegalArgumentException.class, () -> {
            XMLGregorianCalendars
                    .timeGregorianCalendarBuilder()
                    .hour(14)
                    .minute(30)
                    .build();
        });
        assertEquals("Either time or hour/minute/second must be provided.", partialException.getMessage());
    }
}
