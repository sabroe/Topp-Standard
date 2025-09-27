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

import java.util.regex.Pattern;

/**
 * Utilities addressing instances of {@link Pattern}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-09-24
 */
@UtilityClass
public class Patterns {  //TO-DO: Move to regex-specific module!

    public static Matchers.MatchedInput matches(Pattern pattern,
                                                CharSequence input) {
        return Matchers.MatchedInput.of(input,pattern.matcher(input));
    }
}
