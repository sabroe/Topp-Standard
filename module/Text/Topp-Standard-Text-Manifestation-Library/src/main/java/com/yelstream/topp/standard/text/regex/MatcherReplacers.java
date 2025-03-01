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

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;

/**
 * Utilities addressing instances of matcher-replacers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
@UtilityClass
public class MatcherReplacers {
    /**
     * Provides a replacer not changing any matched group.
     * @return Replacer not changing any matched group.
     */
    public static MatcherReplacer replaceNone() {
        return (matcher,map) -> matcher.replaceAll("$0");
    }

    /**
     * Provides a replacer for the first matched group.
     * @return Replacer for the first matched group.
     */
    public static MatcherReplacer replaceFirst() {
        return Matcher::replaceFirst;
    }

    /**
     * Provides a replacer for all matched groups.
     * @return Replacer for all matched groups.
     */
    public static MatcherReplacer replaceAll() {
        return Matcher::replaceAll;
    }

    /**
     * Provides a replacer for the second matched group.
     * @return Replacer for the second matched group.
     */
    public static MatcherReplacer replaceSecond() {
        return (matcher,map) -> {
            StringBuilder sb=new StringBuilder();
            int count=0;
            while (matcher.find()) {
                count++;
                if (count==2) {
                    matcher.appendReplacement(sb,map.apply(matcher));
                } else {
                    matcher.appendReplacement(sb,matcher.group());
                }
            }
            matcher.appendTail(sb);
            return sb.toString();
        };
    }

    /**
     * Provides a replacer for the last matched group.
     * @return Replacer for the last matched group.
     */
    public static MatcherReplacer replaceLast() {
        return (matcher,map) -> {
            StringBuilder sb=new StringBuilder();
            int lastMatchStart=-1;
            while (matcher.find()) {
                lastMatchStart=matcher.start();
            }
            if (lastMatchStart==-1) {
                return matcher.replaceAll("");
            }
            matcher.reset();
            while (matcher.find()) {
                if (matcher.start()==lastMatchStart) {
                    matcher.appendReplacement(sb,map.apply(matcher));
                } else {
                    matcher.appendReplacement(sb,matcher.group());
                }
            }
            matcher.appendTail(sb);
            return sb.toString();
        };
    }
}
