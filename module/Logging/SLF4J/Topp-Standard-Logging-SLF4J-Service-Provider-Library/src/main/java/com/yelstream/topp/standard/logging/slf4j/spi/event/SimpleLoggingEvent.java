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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.CallerBoundaryAware;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Logging-event with all values.
*
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-04
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
public class SimpleLoggingEvent implements LoggingEvent, CallerBoundaryAware {
    private String loggerName;

    private Level level;

    private String message;

    @Singular
    private List<Object> arguments;

    private Throwable throwable;

    @Singular
    private List<KeyValuePair> keyValuePairs;

    @Singular
    private List<Marker> markers;

    private long timeStamp;

    private String threadName;

    private String callerBoundary;

    @Override
    public Object[] getArgumentArray() {
        return arguments.toArray();
    }

    public static class Builder {
        public Builder event(LoggingEvent event) {
            return
                loggerName(event.getLoggerName())
                .level(event.getLevel())
                .message(event.getMessage())
                .arguments(event.getArguments())
                .throwable(event.getThrowable())
                .keyValuePairs(event.getKeyValuePairs())
                .markers(event.getMarkers())
                .timeStamp(event.getTimeStamp())
                .threadName(event.getThreadName())
                .callerBoundary(event.getCallerBoundary());
        }

        public Builder logger(Logger logger) {
            return loggerName(logger.getName());
        }

        public Builder time(Instant instant) {
            return timeStamp(instant.toEpochMilli());
        }

        public Builder keyValue(String key,
                                Object value) {
            return keyValuePair(new KeyValuePair(key,value));
        }

        public Builder keyValues(Map<String,Object> keyValues) {
            Builder builder=this;
            if (keyValues != null) {
                for (Map.Entry<String, Object> entry : keyValues.entrySet()) {
                    builder=builder.keyValue(entry.getKey(),entry.getValue());
                }
            }
            return builder;
        }
    }

    public static SimpleLoggingEvent of(LoggingEvent event) {
        return builder().event(event).build();
    }
}
