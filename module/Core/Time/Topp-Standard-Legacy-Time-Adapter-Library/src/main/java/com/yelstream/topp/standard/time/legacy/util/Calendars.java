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

package com.yelstream.topp.standard.time.legacy.util;

import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link java.util.Calendar}.
 * <p>
 *     Note that {@link java.util.Calendar} is mutable!
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-11
 */
@UtilityClass
public class Calendars {
}


/*
You’re designing a tiny time abstraction layer over Date.

That’s a different level.



Even with your clean API, usage still looks like:

Date result =
    Dates.map(date, i -> i.plus(1, DAYS));

LocalDate d =
    Dates.toLocalDate(date, zone);

Date nextMonth =
    Dates.mapZoned(date, zone, z -> z.plusMonths(1));



Wrap Date in a fluent, immutable view:

Dates.view(date)
     .plus(1, DAYS)
     .at(zone)
     .toLocalDate();

👉 Now it reads like a domain language, not utilities.



Usage (this is the payoff)
Before
Date result =
    Dates.mapZoned(date, zone, z -> z.plusMonths(1));
After
Date result =
    Dates.view(date)
         .at(zone)
         .plusMonths(1)
         .toDate();
Before
LocalDate d =
    Dates.toLocalDate(date, zone);
After
LocalDate d =
    Dates.view(date)
         .at(zone)
         .toLocalDate();


Why this is “next level”
1. Composability
Dates.view(date)
     .plus(1, DAYS)
     .minus(2, HOURS)
     .at(zone)
     .withDayOfMonth(1)
     .toDate();

👉 No lambdas, no nesting.




When this is worth it

This level makes sense if:

you use Date a lot (interop, legacy systems)
you want clean call sites
you care about API elegance (which you clearly do)



Honest assessment

Right now your utility class is:

Production-ready and well-designed

Adding this makes it:

A small, elegant time API on top of legacy Date




Mental model (this is the key takeaway)

Think of it like this:

Date  → (bridge) → Instant → DateView → ZonedView


 Instant
             │
        TimeView
             │
        ZonedView
             │
     ZonedDateTime

   ↑             ↓
 Date         LocalDate
              LocalDateTime




✔ Composability
TimeView.of(date)
        .plus(1, DAYS)
        .at(zone)
        .plusMonths(1)
        .toDate();
*/
