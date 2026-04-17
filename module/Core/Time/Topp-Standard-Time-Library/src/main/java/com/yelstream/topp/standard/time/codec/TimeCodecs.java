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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Utilities addressing temporal formatting and parsing.
 * <p>
 *     This addresses instances of {@link TimeCodec}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
@UtilityClass
public final class TimeCodecs {

    @AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
    static class SimpleTimeCodec implements TimeCodec {
        private final TemporalCodec<Instant> codec;

        @Override
        public String format(Instant temporal) {
            return codec.format(temporal);
        }

        @Override
        public Instant parse(CharSequence text) {
            return codec.parse(text);
        }
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static TimeCodec from(TemporalCodec<Instant> codec) {
        return
            switch (codec) {
                case TimeCodec timeCodec -> timeCodec;
                default -> SimpleTimeCodec.of(codec);
            };
    }

    public static TimeCodec from(DateTimeFormatter formatter) {
        return from(TemporalCodecs.SimpleTemporalCodec.of(formatter,Instant::from));
    }

    public static final TimeCodec ISO = from(DateTimeFormatter.ISO_INSTANT);
}
