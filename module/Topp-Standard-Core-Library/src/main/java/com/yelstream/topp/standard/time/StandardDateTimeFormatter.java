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

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * Standard date-time formatters.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-17
 */
@AllArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed")
public enum StandardDateTimeFormatter {
    /**
     *
     */
    ISO_8601_OFFSET_DATE_TIME(DateTimeFormatter.ISO_OFFSET_DATE_TIME),

    /**
     *
     */
    ISO_8601_INSTANT(DateTimeFormatter.ISO_INSTANT),

    /**
     *
     */
    ISO_8601_LOCAL_DATE_TIME(DateTimeFormatter.ISO_LOCAL_DATE_TIME),

    /**
     *
     */
    ISO_8601_ZULU_DATE_TIME_FORMATTER(DateTimeFormatters.ISO_8601_ZULU_DATE_TIME_FORMATTER),

    /**
     *
     */
    RFC_3339_OFFSET_DATE_TIME_FORMATTER(DateTimeFormatters.RFC_3339_OFFSET_DATE_TIME_FORMATTER),

    /**
     *
     */
    FIXED_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER(DateTimeFormatters.FIXED_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER),

    /**
     *
     */
    OPTIONAL_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER(DateTimeFormatters.OPTIONAL_FRACTION_RFC_3339_OFFSET_DATE_TIME_FORMATTER),

    /**
     *
     */
    RFC_3339_NO_FRACTION_OFFSET_DATE_TIME_FORMATTER(DateTimeFormatters.RFC_3339_NO_FRACTION_OFFSET_DATE_TIME_FORMATTER),

    /**
     *
     */
    FIXED_MICROSECOND_RFC_3339_OFFSET_DATE_TIME_FORMATTER(DateTimeFormatters.FIXED_MICROSECOND_RFC_3339_OFFSET_DATE_TIME_FORMATTER),

    /**
     *
     */
    COMPACT_RFC_3339_OFFSET_DATE_TIME_FORMATTER(DateTimeFormatters.COMPACT_RFC_3339_OFFSET_DATE_TIME_FORMATTER);

    @Getter
    private final DateTimeFormatter formatter;
}
