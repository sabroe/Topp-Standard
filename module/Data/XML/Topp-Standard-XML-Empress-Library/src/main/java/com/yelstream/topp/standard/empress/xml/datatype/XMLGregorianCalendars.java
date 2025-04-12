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

    //TO-DO: Consider creating a builder!

/*
    public static void main(String[] args) throws Exception {
        DatatypeFactory df = DatatypeFactory.newInstance();

        // Convert LocalDateTime (no timezone)
        LocalDateTime localDateTime = LocalDateTime.now();
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDateTime.atZone(ZoneId.systemDefault()));
        XMLGregorianCalendar xmlDateTime = df.newXMLGregorianCalendar(gregorianCalendar);

        System.out.println("xsd:dateTime: " + xmlDateTime.toXMLFormat());

        // Convert OffsetDateTime (with explicit offset)
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        XMLGregorianCalendar xmlOffsetDateTime = df.newXMLGregorianCalendar(GregorianCalendar.from(offsetDateTime.toZonedDateTime()));

        System.out.println("xsd:dateTime (offset): " + xmlOffsetDateTime.toXMLFormat());

        // Convert ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        XMLGregorianCalendar xmlZonedDateTime = df.newXMLGregorianCalendar(GregorianCalendar.from(zonedDateTime));

        System.out.println("xsd:dateTime (zoned): " + xmlZonedDateTime.toXMLFormat());
    }
*/


}
