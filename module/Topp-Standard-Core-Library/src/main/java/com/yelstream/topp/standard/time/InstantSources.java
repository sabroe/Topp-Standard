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

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;

/**
 * Utility addressing instances of {@link InstantSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-24
 */
@UtilityClass
public class InstantSources {
    /**
     * Creates a source of instants.
     * @return Source of instants.
     */
    public static InstantSource now() {
        return Instant::now;
    }

    /**
     * Creates a source of instants according to a specific clock.
     * @param clock Clock.
     * @return Source of instants.
     */
    public static InstantSource now(Clock clock) {
        return ()->Instant.now(clock);
    }
}
