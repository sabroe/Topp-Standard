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

package com.yelstream.topp.standard.text.line;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Textual line separator.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class LineSeparator {
    private final String sequence;

    public boolean matches(String line) {
        return line.contains(sequence);
    }

    public String normalize(String text) {
        return text.replaceAll("\\R",sequence);
    }

    public List<String> split(String text) {
        String escapedSequence=Pattern.quote(sequence);
        return List.of(text.split(escapedSequence));
    }

    public String join(List<String> lines) {
        return String.join(sequence,lines);
    }

    public static LineSeparator of(String sequence) {
        return new LineSeparator(sequence);
    }
}
