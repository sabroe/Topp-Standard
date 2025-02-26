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

package com.yelstream.topp.standard.text.io;

import lombok.experimental.UtilityClass;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.function.LongConsumer;

/**
 * Utilities addressing instances of writers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-26
 */
@UtilityClass
public class Writers {
    public static CountWriter count(Writer out,
                                    LongConsumer countConsumer) {
        return CountWriter.builder().out(out).countConsumer(countConsumer).build();
    }

    public static BufferedWriter buffered(Writer out) {
        return (out instanceof BufferedWriter bw)?bw:new BufferedWriter(out);
    }

    public static BufferedWriter buffered(OutputStream out) {
        return new BufferedWriter(new OutputStreamWriter(out));
    }

    public static BufferedWriter buffered(OutputStream out,
                                          Charset charset) {
        return new BufferedWriter(new OutputStreamWriter(out,charset));
    }

    public static BufferedWriter buffered(OutputStream out,
                                          CharsetEncoder enc) {
        return new BufferedWriter(new OutputStreamWriter(out,enc));
    }
}
