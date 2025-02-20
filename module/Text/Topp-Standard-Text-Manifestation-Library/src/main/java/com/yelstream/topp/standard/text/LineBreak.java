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

package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Textual line break.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@AllArgsConstructor
@Getter
public enum LineBreak {  //TO-DO: Consider splitting into enum 'StandardLineBreak' and class 'LineBreak'; does it have any value?
    /**
     * Line Feed (Unix, Linux, macOS).
     */
    LF("\n"),

    /**
     * Carriage Return + Line Feed (Windows).
     */
    CRLF("\r\n"),

    /**
     * Carriage Return (Older Mac).
     */
    CR("\r");

    private final String pattern;

    public boolean matches(String line) {  //TO-DO: Consider the sanity of this! Naming 'matches' -> 'ends'? Useful?
        return line.endsWith(pattern);
    }

    public String normalize(String text) {
        return text.replaceAll("\\R",pattern);
    }

    public List<String> split(String text) {
        return List.of(text.split(pattern));
    }

    public String join(List<String> lines) {
        return String.join(pattern,lines);
    }
}
