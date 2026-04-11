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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.function.UnaryOperator;

/**
 * Transforms time.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeMap {
    /**
     * Absolute time.
     */
    private final Time time;

    public Time map(UnaryOperator<Instant> operator) {
        return time.map(operator);
    }

    public Time plus(long amount,
                     TemporalUnit unit) {
        return time.plus(amount, unit);
    }

    public Time plus(TemporalAmount amount) {
        return time.plus(amount);
    }

    public Time minus(long amount,
                      TemporalUnit unit) {
        return time.minus(amount, unit);
    }

    public Time minus(TemporalAmount amount) {
        return time.minus(amount);
    }
}
