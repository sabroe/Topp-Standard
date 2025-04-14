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

import lombok.experimental.UtilityClass;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

/**
 * Utilities addressing instances of {@link XMLGregorianCalendar}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class XMLGregorianCalendars {

    public static XMLGregorianCalendar createGregorianCalendar() {
        DatatypeFactory datatypeFactory=DatatypeFactories.createDataTypeFactory();
        return datatypeFactory.newXMLGregorianCalendar();
    }

    public static XMLGregorianCalendar createGregorianCalendar(GregorianCalendar calendar) {
        DatatypeFactory datatypeFactory=DatatypeFactories.createDataTypeFactory();
        return datatypeFactory.newXMLGregorianCalendar(calendar);
    }

    public static XMLGregorianCalendar createGregorianCalendar(ZonedDateTime dateTime) {
        return createGregorianCalendar(GregorianCalendar.from(dateTime));
    }

    public static XMLGregorianCalendar createGregorianCalendarDate(LocalDate date) {
        DatatypeFactory datatypeFactory=DatatypeFactories.createDataTypeFactory();
        return datatypeFactory.newXMLGregorianCalendarDate(date.getYear(),
                                                           date.getMonth().getValue(),  //Offset 1; range is [1..12]
                                                           date.getDayOfMonth(),
                                                           DatatypeConstants.FIELD_UNDEFINED);
    }

    public static XMLGregorianCalendar createGregorianCalendarTimeWithMilliseconds(LocalTime time) {
        DatatypeFactory datatypeFactory=DatatypeFactories.createDataTypeFactory();
        return datatypeFactory.newXMLGregorianCalendarTime(time.getHour(),
                                                           time.getMinute(),
                                                           time.getSecond(),
                                                 time.getNano()/1_000_000,  //Convert nanoseconds to milliseconds.
                                                           DatatypeConstants.FIELD_UNDEFINED);
    }

    public static XMLGregorianCalendar createGregorianCalendarTimeWithNanoseconds(LocalTime time) {
        DatatypeFactory datatypeFactory=DatatypeFactories.createDataTypeFactory();
        BigDecimal fractionalSecond=BigDecimal.valueOf(time.getNano(),9);  //Scale is 9; nano = 10^-9.
        return datatypeFactory.newXMLGregorianCalendarTime(time.getHour(),
                                                           time.getMinute(),
                                                           time.getSecond(),
                                                           fractionalSecond,
                                                           DatatypeConstants.FIELD_UNDEFINED);
    }

    /**
     * Builder for creating full XMLGregorianCalendar instances (date and time).
     */
    @lombok.Builder(builderClassName = "FullGregorianCalendarBuilder", builderMethodName = "fullGregorianCalendarBuilder")
    private static XMLGregorianCalendar createFullGregorianCalendar(
            ZonedDateTime zonedDateTime,
            GregorianCalendar gregorianCalendar
    ) {
        DatatypeFactory datatypeFactory = DatatypeFactories.createDataTypeFactory();
        if (zonedDateTime != null) {
            // Format ZonedDateTime to XML Schema dateTime format with nanosecond precision
            StringBuilder lexical = new StringBuilder();
            lexical.append(zonedDateTime.getYear())
                    .append('-')
                    .append(String.format("%02d", zonedDateTime.getMonthValue()))
                    .append('-')
                    .append(String.format("%02d", zonedDateTime.getDayOfMonth()))
                    .append('T')
                    .append(String.format("%02d", zonedDateTime.getHour()))
                    .append(':')
                    .append(String.format("%02d", zonedDateTime.getMinute()))
                    .append(':')
                    .append(String.format("%02d", zonedDateTime.getSecond()));

            // Append fractional second if nanoseconds exist
            int nano = zonedDateTime.getNano();
            if (nano > 0) {
                lexical.append('.');
                // Convert nanoseconds to string, removing trailing zeros
                String nanoStr = String.format("%09d", nano); // e.g., 123456789
                nanoStr = nanoStr.replaceAll("0+$", ""); // Remove trailing zeros
                lexical.append(nanoStr);
            }

            // Append timezone (Z for UTC, or offset like +01:00)
            ZoneOffset offset = zonedDateTime.getOffset();
            if (offset.getTotalSeconds() == 0) {
                lexical.append('Z');
            } else {
                int offsetMinutes = offset.getTotalSeconds() / 60;
                lexical.append(offsetMinutes >= 0 ? "+" : "-")
                        .append(String.format("%02d", Math.abs(offsetMinutes / 60)))
                        .append(':')
                        .append(String.format("%02d", Math.abs(offsetMinutes % 60)));
            }

            return datatypeFactory.newXMLGregorianCalendar(lexical.toString());
        } else if (gregorianCalendar != null) {
            return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        } else {
            return datatypeFactory.newXMLGregorianCalendar();
        }
    }

    /**
     * Builder for creating date-only XMLGregorianCalendar instances.
     */
    @lombok.Builder(builderClassName = "DateGregorianCalendarBuilder", builderMethodName = "dateGregorianCalendarBuilder")
    private static XMLGregorianCalendar createDateGregorianCalendar(
            LocalDate date,
            Integer year,
            Integer month,
            Integer day
    ) {
        DatatypeFactory datatypeFactory = DatatypeFactories.createDataTypeFactory();
        if (date != null) {
            return datatypeFactory.newXMLGregorianCalendarDate(
                    date.getYear(),
                    date.getMonth().getValue(),
                    date.getDayOfMonth(),
                    DatatypeConstants.FIELD_UNDEFINED
            );
        } else if (year != null && month != null && day != null) {
            return datatypeFactory.newXMLGregorianCalendarDate(
                    year,
                    month,
                    day,
                    DatatypeConstants.FIELD_UNDEFINED
            );
        }
        throw new IllegalArgumentException("Either date or year/month/day must be provided.");
    }

    /**
     * Builder for creating time-only XMLGregorianCalendar instances.
     */
    @lombok.Builder(builderClassName = "TimeGregorianCalendarBuilder", builderMethodName = "timeGregorianCalendarBuilder")
    private static XMLGregorianCalendar createTimeGregorianCalendar(
            LocalTime time,
            Integer hour,
            Integer minute,
            Integer second,
            Integer millisecond,
            BigDecimal fractionalSecond
    ) {
        DatatypeFactory datatypeFactory = DatatypeFactories.createDataTypeFactory();
        if (time != null) {
            if (fractionalSecond != null) {
                // Nanosecond precision
                return datatypeFactory.newXMLGregorianCalendarTime(
                        time.getHour(),
                        time.getMinute(),
                        time.getSecond(),
                        fractionalSecond,
                        DatatypeConstants.FIELD_UNDEFINED
                );
            } else {
                // Millisecond precision
                return datatypeFactory.newXMLGregorianCalendarTime(
                        time.getHour(),
                        time.getMinute(),
                        time.getSecond(),
                        time.getNano() / 1_000_000,
                        DatatypeConstants.FIELD_UNDEFINED
                );
            }
        } else if (hour != null && minute != null && second != null) {
            if (fractionalSecond != null) {
                // Nanosecond precision via fractionalSecond
                return datatypeFactory.newXMLGregorianCalendarTime(
                        hour,
                        minute,
                        second,
                        fractionalSecond,
                        DatatypeConstants.FIELD_UNDEFINED
                );
            } else {
                // Millisecond precision or none
                return datatypeFactory.newXMLGregorianCalendarTime(
                        hour,
                        minute,
                        second,
                        millisecond != null ? millisecond : DatatypeConstants.FIELD_UNDEFINED,
                        DatatypeConstants.FIELD_UNDEFINED
                );
            }
        }
        throw new IllegalArgumentException("Either time or hour/minute/second must be provided.");
    }
}
