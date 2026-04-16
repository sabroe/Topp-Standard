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

import com.yelstream.topp.standard.time.InstantSources;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Test of {@link InstantSources}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-28
 */
@Slf4j
class TimeTest {

    public static void main(String[] args) {

/*
        time.map().plus(...)                     // strict
        time.flow().nullable().map().plus(...)  // nullable
*/

    }

    @Test
    void policyStrict() {

/*
        Time result =
                TimeFlow.of(time)
                        .plus(5, ChronoUnit.MINUTES)
                        .toTime();
*/
    }

    @Test
    void policyNullable() {

/*
        Time result =
                TimeFlow.ofNullable(date)
                        .nullable()
                        .plus(5, ChronoUnit.MINUTES)
                        .toTime();
*/
    }

    @Test
    void policyNullAware() {

/*
        Time result =
                TimeFlow.ofNullable(date)
                        .nullAware()
                        .map(i -> i == null ? Instant.EPOCH : i.plusSeconds(60))
                        .toTime();
*/
    }


}
