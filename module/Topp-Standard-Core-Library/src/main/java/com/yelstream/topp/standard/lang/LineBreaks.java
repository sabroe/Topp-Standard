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

package com.yelstream.topp.standard.lang;

import lombok.experimental.UtilityClass;

/**
 * Textual line break.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class LineBreaks { //TO-DO: Remove this from 'com.yelstream.topp.standard.lang', please! Duplicate. MSM, 2025-02-20.

    public static LineBreak DEFAULT_LINE_BREAK=LineBreak.LF;

    public static boolean matches(String line) {
        return DEFAULT_LINE_BREAK.matches(line);
    }

    public static String normalize(String text) {
        return DEFAULT_LINE_BREAK.normalize(text);
    }
}
