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

package com.yelstream.topp.standard.time.codec;

import com.yelstream.topp.standard.time.format.TemporalFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

/**
 * Utilities addressing temporal formatting.
 * <p>
 *     This addresses instances of {@link TemporalFormat}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
@UtilityClass
public class TemporalCodecs {
    /**
     *
     * @param <T>
     */
    @AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
    static class SimpleTemporalCodec<T extends TemporalAccessor> implements TemporalCodec<T> {
        private final DateTimeFormatter formatter;
        private final TemporalQuery<T> query;

        @Override
        public String format(T temporal) {
            return formatter.format(temporal);
        }

        @Override
        public T parse(CharSequence text) {
            return formatter.parse(text,query);
        }
    }
}
