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

package com.yelstream.topp.standard.text;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

public class LineNumberPrintStream extends PrintStream {
    private final AtomicInteger lineNumber = new AtomicInteger(1);
    private final PrintStream original;

    public LineNumberPrintStream(PrintStream original) {
        super(new OutputStream() {
            @Override
            public void write(int b) {
                // This will be handled through the overridden print methods
                original.write(b);
            }
        });
        this.original = original;
    }

    @Override
    public void println(String x) {
        original.println(lineNumber.getAndIncrement() + ": " + x);
    }

    @Override
    public void print(String x) {
        original.print(x); // Let normal print() behavior remain unchanged
    }

    public static void main(String[] args) {
        System.setOut(new LineNumberPrintStream(System.out));

        System.out.println("Hello, World!");
        System.out.println("This is a test.");
        System.out.println("Each line has a number.");
    }
}
