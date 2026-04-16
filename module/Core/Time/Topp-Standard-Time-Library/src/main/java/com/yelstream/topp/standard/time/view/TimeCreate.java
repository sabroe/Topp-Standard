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

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * Creates time instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeCreate {
    public Time from(Instant instant) {
        return Time.of(instant);
    }

    public Time from(InstantSource instantSource) {
        return Time.of(instantSource);
    }

    public Time from(Temporal temporal) {
        return Time.of(Instant.from(temporal));
    }

    public Time from(Date date) {
        return Time.of(date);
    }

    public Time now() {
        return Time.of(Instant.now());
    }

    public Time now(Clock clock) {
        return Time.of(clock);
    }
}
