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

package com.yelstream.topp.standard.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Pattern match flag.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-09-24
 */
@SuppressWarnings({"LombokGetterMayBeUsed","java:S115"})
@AllArgsConstructor
public enum Flag {
    /**
     * Enables canonical equivalence.
     */
    CanonicalEquivalence(Pattern.CANON_EQ),

    /**
     * Enables case-insensitive matching.
     */
    CaseInsensitive(Pattern.CASE_INSENSITIVE),

    /**
     * Permits whitespace and comments in pattern.
     */
    Comments(Pattern.COMMENTS),

    /**
     * Enables dotall mode.
     */
    DotAll(Pattern.DOTALL),

    /**
     * Enables literal parsing of the pattern.
     */
    LiteralParsing(Pattern.LITERAL),

    /**
     * Enables multiline mode.
     */
    MultiLine(Pattern.MULTILINE),

    /**
     * Enables Unicode-aware case folding.
     */
    UnicodeCase(Pattern.UNICODE_CASE),

    /**
     * Enables the Unicode version of Predefined character classes and POSIX character classes.
     */
    UnicodeCharacterClass(Pattern.UNICODE_CHARACTER_CLASS),

    /**
     * Enables Unix lines mode.
     */
    UNIXLines(Pattern.UNIX_LINES);

    @Getter
    private final int value;

    public static Flag valueByFlag(int flag) {
        return Arrays.stream(values()).filter(x->x.value==flag).findFirst().orElse(null);
    }
}
