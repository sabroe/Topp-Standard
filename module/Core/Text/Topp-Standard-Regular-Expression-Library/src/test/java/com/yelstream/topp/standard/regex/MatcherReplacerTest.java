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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Tests {@link MatcherReplacer}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-01
 */
class MatcherReplacerTest {
    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of(MatcherReplacers.replaceNone(),"Order 123 has been updated to 456, not 789."),
            Arguments.of(MatcherReplacers.replaceFirst(),"Order XXX has been updated to 456, not 789."),
            Arguments.of(MatcherReplacers.replaceAll(),"Order XXX has been updated to XXX, not XXX."),
            Arguments.of(MatcherReplacers.replaceSecond(),"Order 123 has been updated to XXX, not 789."),
            Arguments.of(MatcherReplacers.replaceLast(),"Order 123 has been updated to 456, not XXX.")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void replace(MatcherReplacer replacer,
                 String expected) {
        Pattern pattern=Pattern.compile("\\d+");
        String input="Order 123 has been updated to 456, not 789.";

        Matcher matcher=pattern.matcher(input);

        String result=replacer.replace(matcher,matchResult->"XXX");

        Assertions.assertEquals(expected,result);
    }
}
