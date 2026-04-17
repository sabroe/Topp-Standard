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

/**
 * Strategy for how to format and parse a temporal.
 * @param <T> Type of temporal.
 *            This may be an instance of {@link java.time.temporal.Temporal}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
public interface TemporalCodec<T> {  //TO-DO: Why not extend TemporalFormat, TemporalParse ?
    /**
     * Formats a temporal into text.
     * @param temporal Temporal.
     * @return Formatted temporal.
     */
    String format(T temporal);

    /**
     * Parses a text into a temporal.
     * @param text Text.
     * @return Parsed temporal.
     */
    T parse(CharSequence text);
}
