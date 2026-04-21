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

package com.yelstream.topp.standard.time.format;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

/**
 * Utilities addressing temporal parsing.
 * <p>
 *     This addresses instances of {@link TemporalParse}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
@UtilityClass
public class TemporalParses {


    public static TemporalParse<TemporalAccessor> from(DateTimeFormatter formatter) {
        return formatter::parse;
    }

    public static <T extends TemporalAccessor> TemporalParse<T> from(DateTimeFormatter formatter,
                                                                     TemporalQuery<T> query) {
        return text -> formatter.parse(text,query);
    }


/*
    OffsetTime     ot = parseOffsetTime("14:30:00+02:00");
    ZonedDateTime  zdt = parseZonedDateTime("2025-04-19T14:30:00+02:00[Europe/Copenhagen]");
    LocalDateTime  ldt = parseLocalDateTime("2025-04-19T14:30:00");
    LocalDate      ld  = parseLocalDate("2025-04-19");
    LocalTime      lt  = parseLocalTime("14:30:00");
    OffsetDateTime odt = parseOffsetDateTime(...);
    Year           year = parseYear(...);
    YearMonth      ym   = parseYearMonth(...);
    MonthDay       md   = parseMonthDay(...);
    ZonedDateTime zdt = parseZonedDateTime(text, SomeFormatter);
*/
}
