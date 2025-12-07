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

package com.yelstream.topp.standard.text.io;

import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.function.LongConsumer;

/**
 * Utilities addressing instances of print-writers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-25
 */
@UtilityClass
public class PrintWriters {
    @lombok.Builder(builderClassName="Builder")
    public static PrintWriter create(Writer writer, boolean autoFlush) {
        return new PrintWriter(writer,autoFlush);
    }

    @SuppressWarnings({"unused","FieldMayBeFinal"})
    public static class Builder {
        private boolean autoFlush=true;
    }

    public static CountPrintWriter count(Writer out, boolean autoFlush, LongConsumer countConsumer) {
        return CountPrintWriter.builder().out(out).autoFlush(autoFlush).countConsumer(countConsumer).build();
    }
}
