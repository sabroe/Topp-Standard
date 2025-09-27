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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Associates a matcher with its input text.
 * <p>
 *     Note, that this in essence is a kludge repairing upon the fact that
 *     the method {@code Matcher#text()} is NOT public.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-09-24
 */
public record MatchedPattern(Matcher matcher,
                             CharSequence input) {

    public Pattern pattern() {
        return matcher().pattern();
    }

    public String regEx() {
        return matcher().pattern().pattern();
    }

    public String text() {
        return input().toString();
    }

    public static MatchedPattern of(Matcher matcher,
                                    CharSequence input) {
        return new MatchedPattern(matcher,input);
    }

    public static MatchedPattern of(Pattern pattern,
                                    CharSequence input) {
        return of(pattern.matcher(input),input);
    }
}
