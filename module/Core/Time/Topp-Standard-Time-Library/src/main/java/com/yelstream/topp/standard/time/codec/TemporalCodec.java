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
 * A bidirectional representation that can convert temporal values to and from text.
 * <p>
 *     This type is defined as a composition of two independent operations rather than through inheritance.
 *     Although formatting and parsing are often used together,
 *     they are not inherently symmetrical: a textual representation may be lossy or ambiguous,
 *     and a parser may accept inputs that do not have a single canonical form.
 *     By keeping the two directions loosely coupled,
 *     this design remains flexible and avoids imposing artificial constraints on either operation.
 * </p>
 * @param <T> Type of temporal.
 *            This may be an instance of {@link java.time.temporal.Temporal},
 *            but is not restricted to that.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-17
 */
public interface TemporalCodec<T> {
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
