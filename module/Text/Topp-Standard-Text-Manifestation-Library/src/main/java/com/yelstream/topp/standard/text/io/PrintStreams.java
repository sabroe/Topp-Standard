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

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.LongConsumer;

/**
 * Utilities addressing instances of print-streams.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-25
 */
@UtilityClass
public class PrintStreams {
    @lombok.Builder(builderClassName="Builder")
    public static PrintStream create(OutputStream out, boolean autoFlush, Charset charset) {
        return new PrintStream(out,autoFlush,charset);
    }

    @SuppressWarnings({"unused","FieldMayBeFinal"})
    public static class Builder {
        private boolean autoFlush=true;
        private Charset charset=StandardCharsets.UTF_8;
    }

    public static CountPrintStream count(OutputStream out, boolean autoFlush, Charset charset, LongConsumer countConsumer) {
        return CountPrintStream.builder().out(out).autoFlush(autoFlush).charset(charset).countConsumer(countConsumer).build();
    }
}
