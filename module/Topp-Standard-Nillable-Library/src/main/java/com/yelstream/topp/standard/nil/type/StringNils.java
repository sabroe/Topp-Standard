/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.nil.type;

import com.yelstream.topp.standard.nil.Nil;
import lombok.experimental.UtilityClass;

/**
 * Utilities addressing {@code Nil<String>}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class StringNils {
    @SuppressWarnings("java:S2386")
    public static final String DEFAULT_NIL_VALUE="";

    public static final Nil<String> DEFAULT_NIL=Nil.of(DEFAULT_NIL_VALUE);

    public static Nil<String> getNil() {
        return DEFAULT_NIL;
    }
}
