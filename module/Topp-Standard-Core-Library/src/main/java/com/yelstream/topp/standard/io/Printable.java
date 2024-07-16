/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * A printable implies an object capable of printing some part of itself upon a print stream.
 * <p>
 *    Helper methods for printing and capturing the result as a text are provided.
 * </p>
 * <p>
 *    Example usage:
 * </p>
 * <pre>
 *     public class MyPrintable implements Printable {
 *         {@literal @}Override
 *         public void print(PrintStream out) {
 *             out.println("Hello, World!");
 *         }
 *     }
 *     MyPrintable printable = new MyPrintable();
 *     String text = printable.capture();
 *     System.out.println(text);
 * </pre>
 * <p>
 *    Example usage with charset:
 * </p>
 * <pre>
 *     String text = printable.capture(StandardCharsets.UTF_8);
 *     System.out.println(text);
 * </pre>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-11
 */
@FunctionalInterface
public interface Printable {
    /**
     * Prints a textual message to the provided output stream.
     * @param out Output stream.
     */
    void print(PrintStream out);

    /**
     * Prints the textual message to normal system output.
     */
    @SuppressWarnings("java:S106")
    default void print() {
        print(System.out);
    }

    /**
     * Prints the textual message and captures this output of the {@link #print(PrintStream)} method as a text.
     * @return Printed text.
     */
    default String capture() {
        return PrintBuffer.print(this::print);
    }

    /**
     * Prints the textual message and captures this output of the {@link #print(PrintStream)} method as a text.
     * @param charset Character set.
     * @return Printed text.
     */
    default String capture(Charset charset) {
        return PrintBuffer.print(charset,this::print);
    }
}
