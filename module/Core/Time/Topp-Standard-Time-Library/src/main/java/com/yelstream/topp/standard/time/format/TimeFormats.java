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

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class TimeFormats {

    public static TimeFormat of(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter);
        return instant -> formatter.format(instant);
    }

    public static final TimeFormat ISO =
            of(DateTimeFormatter.ISO_INSTANT);

    public static final TimeFormat RFC_1123 =
            of(DateTimeFormatter.RFC_1123_DATE_TIME);

    /*
    Usage:
TimeFormat f = TimeFormats.ISO;
String s = f.format(time);
     */
}
