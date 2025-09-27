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
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities addressing instances of {@link Matcher}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-09-24
 */
@UtilityClass
public class Patterns {

    @SuppressWarnings("MagicConstant")  //Gee, this is LAME! When trying to "repair" with an enum then this triggers the penalty of not using magic constants?
    public static Pattern create(String regEx,
                                 int flags) {
        return Pattern.compile(regEx,flags);
    }

    public static Pattern create(String regEx,
                                 List<Flag> flags) {
        return create(regEx,Flags.combine(flags));
    }

    public static Pattern create(String regEx,
                                 Flag... flags) {
        return create(regEx,List.of(flags));
    }

    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    private static Pattern createByBuilder(String regEx,
                                           @lombok.Singular List<Flag> flags) {
        return create(regEx,flags);
    }
}
