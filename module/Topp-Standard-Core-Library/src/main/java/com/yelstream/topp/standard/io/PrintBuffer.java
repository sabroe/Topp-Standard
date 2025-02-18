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

package com.yelstream.topp.standard.io;

import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Maintainer of a buffered {@link PrintStream}.
 * <p>
 *     This class encapsulates a {@link ByteArrayOutputStream} and {@link PrintStream}
 *     to provide a convenient way to capture printed output into a buffer.
 *     The buffer can then be converted to a string using the specified charset.
 * </p>
 * <p>
 *     It implements {@link AutoCloseable} to ensure proper resource management.
 * </p>
 * <p>
 *     Example usage:
 * </p>
 * <pre>
 *     try (PrintBuffer buffer = PrintBuffer.of()) {
 *         PrintStream out = buffer.getPrintStream();
 *         out.println("Hello, World!");
 *         String text = buffer.toString();
 *         System.out.println(text);
 *     }
 * </pre>
 * <p>
 *     Example usage:
 * </p>
 * <pre>
 *     String text = PrintBuffer.print(out -> out.print("Hello, World!);
 *     System.out.println(text);
 * </pre>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-14
 */
public class PrintBuffer implements AutoCloseable {
    /**
     * Character set.
     */
    private final Charset charset;

    /**
     * Output buffer.
     */
    private final ByteArrayOutputStream buffer;

    /**
     * Print stream.
     */
    @Getter
    private final PrintStream printStream;

    /**
     * Constructor.
     * @param charset Character set.
     */
    private PrintBuffer(Charset charset) {
        this.charset=charset;
        this.buffer=new ByteArrayOutputStream();
        this.printStream=new PrintStream(buffer,true,charset);
    }

    @Override
    public void close() {
        printStream.close();
    }

    @Override
    public String toString() {
        return buffer.toString(charset);
    }

    /**
     * Creates a new instance.
     * @param charset Character set.
     */
    public static PrintBuffer of(Charset charset) {
        return new PrintBuffer(charset);
    }

    /**
     * Creates a new instance.
     */
    public static PrintBuffer of() {
        return of(StandardCharsets.UTF_8);
    }

    /**
     * Prints using a print-stream buffer.
     * @param bufferSupplier Source of print-stream buffer.
     * @param task Print task.
     * @return Resulting text.
     */
    public static String print(Supplier<PrintBuffer> bufferSupplier,
                               Consumer<PrintStream> task) {
        String text;
        try (var buffer=bufferSupplier.get()) {
            var out=buffer.getPrintStream();
            task.accept(out);
            text=buffer.toString();
        }
        return text;
    }

    /**
     * Prints using a print-stream buffer.
     * @param task Print task.
     * @return Resulting text.
     */
    public static String print(Consumer<PrintStream> task) {
        return print(PrintBuffer::of,task);
    }

    /**
     * Prints using a print-stream buffer.
     * @param  charset Character set.
     * @param task Print task.
     * @return Resulting text.
     */
    public static String print(Charset charset,
                               Consumer<PrintStream> task) {
        return print(()-> PrintBuffer.of(charset),task);
    }
}
