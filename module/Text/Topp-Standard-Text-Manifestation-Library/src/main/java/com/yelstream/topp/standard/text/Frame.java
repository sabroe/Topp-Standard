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
import lombok.ToString;

import java.util.function.UnaryOperator;

/**
 * Textual frame to decorate a section of multi-line text with a start line and an end line.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
@AllArgsConstructor
public class Frame {
    /**
     *
     */
    private final Section section;

    /**
     * Text transformation.
     */
    private final UnaryOperator<String> transform;

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Section {
        private final String begin;
        private final String end;
    }

    public String format(String text) {
        StringBuilder sb=new StringBuilder();
        sb.append(section.begin);
        sb.append("\n");
        sb.append(transform.apply(text));
        sb.append("\n");
        return sb.toString();
    }
}
