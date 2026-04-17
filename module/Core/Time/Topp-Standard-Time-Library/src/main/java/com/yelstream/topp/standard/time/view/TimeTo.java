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

package com.yelstream.topp.standard.time.view;

import com.yelstream.topp.standard.time.codec.TemporalCodec;
import com.yelstream.topp.standard.time.codec.TimeCodec;
import com.yelstream.topp.standard.time.format.TemporalFormat;
import com.yelstream.topp.standard.time.format.TimeFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Capability view on extraction from {@link Time} instances.
 * <p>
 *     This is a fluent helper.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeTo {
    /**
     * Absolute time.
     */
    private final Time time;

    public Instant instant() {
        return time.toInstant();
    }

    public Date date() {
        return time.toDate();
    }

    public ZonedTime zonedTime(ZoneId zone) {
        return time.toZonedTime(zone);
    }

    public String iso() {
        return format(DateTimeFormatter.ISO_INSTANT);
    }

    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter);
        return formatter.format(time.toInstant());
    }

    public String format(TimeFormat format) {
        Objects.requireNonNull(format, "format");
        return format.format(time.toInstant());
    }

    public String format(TemporalFormat<Instant> format) {
        Objects.requireNonNull(format, "format");
        return format.format(time.toInstant());
    }

    public String format(TimeCodec codec) {
        Objects.requireNonNull(codec, "codec");
        return codec.format(time.toInstant());
    }

    public String format(TemporalCodec<Instant> codec) {
        Objects.requireNonNull(codec, "codec");
        return codec.format(time.toInstant());
    }
}
