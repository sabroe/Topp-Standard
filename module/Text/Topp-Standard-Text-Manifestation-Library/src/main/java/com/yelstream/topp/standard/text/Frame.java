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
 * Textual frame decorating a multiline text with a start line and an end line.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@SuppressWarnings("LombokGetterMayBeUsed")
public class Frame {
    /**
     *
     */
    @Getter
    private final Label beginLabel;  //TO-DO: Consider the type, possibly 'Line' or 'Supplier<String>'?

    /**
     *
     */
    @Getter
    private final Label endLabel;  //TO-DO: Consider the type, possibly 'Line' or 'Supplier<String>'?

    /**
     *
     */
    @Getter
    private final Text text;

    public Text toText() {
        Text.Builder builder=text!=null?text.toBuilder():Text.builder();
        if (beginLabel!=null) {
            builder=builder.prependLine(beginLabel.toString());
        }
        if (endLabel!=null) {
            builder=builder.appendLine(endLabel.toString());
        }
        return builder.build();
    }

    public List<String> toLines() {
        return toText().toLines();
    }

    public String toString() {
        return toText().toString();
    }
}
