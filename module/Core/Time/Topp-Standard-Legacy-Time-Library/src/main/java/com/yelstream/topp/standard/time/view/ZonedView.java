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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.function.UnaryOperator;

/**
 * Fluent view of human/calendar time.
 * <p>
 *     This view is zoned by being based on an {@code ZonedDateTime}.
 * </p>
 */
@AllArgsConstructor(staticName = "of")
public final class ZonedView {
    /**
     * Human/calendar time.
     */
    private final ZonedDateTime zdt;

    public ZonedView map(UnaryOperator<ZonedDateTime> operator) {  //TO-DO: #map()->MapOperation
        return new ZonedView(operator.apply(zdt));
    }

    public ZonedView plus(long amount, TemporalUnit unit) {  //TO-DO: #map()->MapOperation
        return map(i -> i.plus(amount, unit));
    }

    public ZonedView plus(TemporalAmount amount) {  //TO-DO: #map()->MapOperation
        return map(i -> i.plus(amount));
    }

    public ZonedView minus(long amount, TemporalUnit unit) {  //TO-DO: #map()->MapOperation
        return map(i -> i.minus(amount, unit));
    }

    public ZonedView minus(TemporalAmount amount) {  //TO-DO: #map()->MapOperation
        return map(i -> i.minus(amount));
    }

    public ZonedDateTime toZonedDateTime() {  //TO-DO: #convert()->ConvertOperation
        return zdt;
    }

    public LocalDate toLocalDate() {  //TO-DO: #convert()->ConvertOperation
        return zdt.toLocalDate();
    }

    public LocalDateTime toLocalDateTime() {  //TO-DO: #convert()->ConvertOperation
        return zdt.toLocalDateTime();
    }

    public TimeView toTimeView() {  //TO-DO: #convert()->ConvertOperation
        return TimeView.of(zdt.toInstant());
    }

    public Date toDate() {  //TO-DO: #convert()->ConvertOperation
        return Date.from(zdt.toInstant());
    }

/*

https://javadoc.io/doc/io.smallrye.reactive/mutiny/latest/io.smallrye.mutiny/io/smallrye/mutiny/Multi.html

static MultiCreate createFrom()
static MultiCreateBy createBy()
MultiCollect<T> collect()
MultiBroadcast<T> broadcast()


https://javadoc.io/doc/io.smallrye.reactive/mutiny/latest/io.smallrye.mutiny/io/smallrye/mutiny/Uni.html
UniAwait<T> await()
static UniCombine combine()
UniConvert<T> convert()
static UniCreate createFrom()
Multi<T> toMulti()

 */
}