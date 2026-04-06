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

package com.yelstream.topp.standard.logging.slf4j.spi.event;

import lombok.experimental.UtilityClass;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.LoggingEvent;
import org.slf4j.event.SubstituteLoggingEvent;

import java.time.Instant;

/**
 * Utilities for instances of {@link org.slf4j.event.LoggingEvent}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-06
 */
@UtilityClass
public class LoggingEvents {

    public static long convertTimestamp(Instant timestamp) {
        return timestamp==null?0:timestamp.toEpochMilli();
    }

    public static Instant convertTimestamp(long timestamp) {
        return timestamp==0?null:Instant.ofEpochMilli(timestamp);  //Note: Do not care about negative values!
    }

    public static String toString(LoggingEvent event) {  //TO-DO: Consider, if this is viable!
        return switch (event) {
            case FixedLoggingEvent ev   -> ev.toString();
            case SimpleLoggingEvent ev  -> ev.toString();
            case DefaultLoggingEvent _ -> FixedLoggingEvent.of(event).toString();  //TO-DO: Consider this; output starts with 'FixedLoggingEvent', is not exactly anonymous!
            case SubstituteLoggingEvent _ -> FixedLoggingEvent.of(event).toString();  //TO-DO: Consider this; output starts with 'FixedLoggingEvent', is not exactly anonymous!
            default -> event.toString();
        };
    }
}
