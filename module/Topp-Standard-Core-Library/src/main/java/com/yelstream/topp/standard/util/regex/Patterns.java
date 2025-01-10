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

package com.yelstream.topp.standard.util.regex;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Utility addressing instances of {@link URI}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-05
 */
@Slf4j
@UtilityClass
public class Patterns {
    @SuppressWarnings("SameParameterValue")
    private static Pattern createPattern(String regEx,
                                         String defaultRegEx) {  //TO-DO: Consider the applicability of this; may not be too fantastic!
        Pattern pattern;
        try {
            pattern=Pattern.compile(regEx);
        } catch (PatternSyntaxException ex) {
            log.error("Failure to recognize reg-ex; reg-ex configured is {}.",regEx);
            pattern=Pattern.compile(defaultRegEx);
        }
        return pattern;
    }
}
