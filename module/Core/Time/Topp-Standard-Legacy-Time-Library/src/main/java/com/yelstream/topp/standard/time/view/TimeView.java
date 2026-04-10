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

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.InstantSource;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Fluent view of absolute time.
 * <p>
 *     Internally, this is based on an {@code Instant}.
 * </p>
 */
@AllArgsConstructor(staticName = "of")
public final class TimeView {
    /**
     * Absolute time.
     */
    private final Instant instant;

    public TimeView map(UnaryOperator<Instant> operator) {  //TO-DO: #map()->MapOperation
        return new TimeView(operator.apply(instant));
    }

    public TimeView plus(long amount, TemporalUnit unit) {  //TO-DO: #map()->MapOperation
        return map(i -> i.plus(amount, unit));
    }

    public TimeView plus(TemporalAmount amount) {  //TO-DO: #map()->MapOperation
        return map(i -> i.plus(amount));
    }

    public TimeView minus(long amount, TemporalUnit unit) {  //TO-DO: #map()->MapOperation
        return map(i -> i.minus(amount, unit));
    }

    public TimeView minus(TemporalAmount amount) {  //TO-DO: #map()->MapOperation
        return map(i -> i.minus(amount));
    }

    public ZonedView at(ZoneId zone) {  //TO-DO: #convert()->ConvertOperation
        return ZonedView.of(instant.atZone(zone));
    }

    public Instant toInstant() {
        return instant;
    }

    public Date toDate() {
        return Date.from(instant);
    }

    public static TimeView of(Date date) {  //TO-DO: #convert()->ConvertOperation
        Objects.requireNonNull(date, "date");
        return new TimeView(date.toInstant());
    }

    public static TimeView of(InstantSource instantSource) {  //TO-DO: #convert()->ConvertOperation
        Objects.requireNonNull(instantSource, "instantSource");
        return new TimeView(instantSource.instant());
    }

    public static TimeViewCreate create() {  //TO-DO: '#from()' ?
        return TimeViewCreate.of();
    }

    public TimeViewMap map() {
        return TimeViewMap.of(this);
    }

    public TimeViewTo to() {
        return TimeViewTo.of(this);
    }
}
