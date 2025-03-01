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

package com.yelstream.topp.standard.text.regex;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * Transformation to matched groups within a regular expression.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
@FunctionalInterface
public interface MatcherReplacer {
    /**
     * Replaces selected occurrences of matched groups using a transformation from {@link MatchResult} to a value.
     * @param matcher The matcher containing matched groups.
     * @param map A function transforming {@link MatchResult} to a replacement string.
     * @return The resulting string after replacements.
     */
    String replace(Matcher matcher,
                   Function<MatchResult,String> map);
}
