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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilities addressing temporal formatting and parsing.
 * <p>
 *     This addresses instances of {@link ZonedTimeCodec}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
@UtilityClass
public class ZonedTimeCodecs {

    @AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
    static class SimpleTimeCodec implements ZonedTimeCodec {
        private final TemporalCodec<ZonedDateTime> codec;

        @Override
        public String format(ZonedDateTime temporal) {
            return codec.format(temporal);
        }

        @Override
        public ZonedDateTime parse(CharSequence text) {
            return codec.parse(text);
        }
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static ZonedTimeCodec from(TemporalCodec<ZonedDateTime> codec) {
        return
            switch (codec) {
                case ZonedTimeCodec zonedTimeCodec -> zonedTimeCodec;
                default -> SimpleTimeCodec.of(codec);
            };
    }

    public static ZonedTimeCodec from(DateTimeFormatter formatter) {
        return from(TemporalCodecs.SimpleTemporalCodec.of(formatter,ZonedDateTime::from));
    }

    public static final ZonedTimeCodec ISO = from(DateTimeFormatter.ISO_INSTANT);
}
