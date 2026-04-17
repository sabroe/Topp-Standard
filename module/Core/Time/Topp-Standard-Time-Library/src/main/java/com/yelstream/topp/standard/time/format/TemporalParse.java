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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Strategy for how to parse a temporal.
 * @param <T> Type of temporal.
 *            This may be an instance of {@link java.time.temporal.Temporal}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
@FunctionalInterface
public interface TemporalParse<T> {  //TO-DO: Why not "TemporalParser" ?
    /**
     * Parses a text into a temporal.
     * @param text Text.
     * @return Parsed temporal.
     */
    T parse(CharSequence text);




    public static TemporalParse<ZonedDateTime> zoned(DateTimeFormatter formatter) {
        return text -> ZonedDateTime.parse(text, formatter);
    }

/*
Usage:
ZonedTime zt = ZonedTime.of(
    zonedParser.parse("2026-04-17T12:00:00+02:00[Europe/Copenhagen]")
);
 */

    /*
    Bridging Time ↔ ZonedTime:

    Convert during parsing
Time t = Time.of(
    zonedParser.parse(text).toInstant()
);

Convert during formatting:
format.format(time.toInstant().atZone(zone));


     */


/*
Final insight (this is the “architect level” takeaway):
Separation of:
- Representation (TemporalParse / Format)
- Domain (Time / ZonedTime)
- Behavior (map / mutate / flow)
- Policy (NullPolicy)
 */
}
