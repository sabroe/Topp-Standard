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

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
@UtilityClass
public class Matchers {

    @FunctionalInterface
    public interface MatcherReplacer {
        Function<Function<MatchResult,String>,String> replace(Matcher matcher);

        static MatcherReplacer replaceFirst() {
            return matcher-> matcher::replaceFirst;
        }

        static MatcherReplacer replaceAll() {
            return matcher-> matcher::replaceAll;
        }
    }


    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum StandardMatcherReplacer {
        ReplaceFirst(MatcherReplacer.replaceFirst()),
        ReplaceAll(MatcherReplacer.replaceAll());

        private final MatcherReplacer replacer;
    }

}
