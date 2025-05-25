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

package com.yelstream.topp.standard.nil.util;

import com.yelstream.topp.standard.nil.Nil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Date;

/**
 * Utilities addressing {@code Nil<Date>}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class DateNils {
    @SuppressWarnings("java:S2386")
    public static final Date DEFAULT_NIL_VALUE = new Date(0);

    public static final Nil<Date> DEFAULT_NIL=Nil.of(DEFAULT_NIL_VALUE);

    @SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
    @AllArgsConstructor
    public enum Standard {
        Default(Nil.of(DEFAULT_NIL_VALUE)),
        High(Nil.of(new Date(Long.MIN_VALUE))),
        Low(Nil.of(new Date(Long.MAX_VALUE)));

        @Getter
        private final Nil<Date> nil;
    }

    public static Nil<Date> getNil() {
        return DEFAULT_NIL;
    }
}
