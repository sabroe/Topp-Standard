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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Tests {@link ImprintPrintWriter}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-01
 */
class ImprentPrintWriterTest {


    @Test
    void printWithNoPrefix() {
        StringWriter sw = new StringWriter();
        try (ImprintPrintWriter writer = ImprintPrintWriter.of(sw)) {
            writer.println("Hello, world!");
            writer.println("This is a test.");
            writer.print("Partial line... ");
            writer.println("continued.");
        }

        String actualOutput=sw.toString();

        String expectedOutput= """
            Hello, world!
            This is a test.
            Partial line... continued.
            """;

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void printWithPrefix() {
        StringWriter sw = new StringWriter();
        try (ImprintPrintWriter writer=ImprintPrintWriter.of(sw,"[PREFIX] ")) {
            writer.println("Hello, world!");
            writer.println("This is a test.");
            writer.print("Partial line... ");
            writer.println("continued.");
        }

        String actualOutput=sw.toString();

        String expectedOutput= """
            [PREFIX] Hello, world!
            [PREFIX] This is a test.
            [PREFIX] Partial line... continued.
            """;

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void printWithPrefixAndSuffix() {
        StringWriter sw = new StringWriter();
        try (ImprintPrintWriter writer=ImprintPrintWriter.of(sw,"[PREFIX] "," XXX\n")) {
            writer.println("Hello, world!");
            writer.println("This is a test.");
            writer.print("Partial line... ");
            writer.println("continued.");

            System.out.println("Statistics: "+writer.getStatistics());
        }

        String actualOutput=sw.toString();

        String expectedOutput= """
            [PREFIX] Hello, world! XXX
            [PREFIX] This is a test. XXX
            [PREFIX] Partial line... continued. XXX
            """;

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void printHalfLineWithNoLineBreak() {
        StringWriter sw = new StringWriter();
        try (ImprintPrintWriter writer=ImprintPrintWriter.of(sw,"[PREFIX] "," XXX\n")) {
            writer.print("Hello, world!");  //No line-break printed!
            writer.flush();
        }
        String actual=sw.toString();
        String expected= "[PREFIX] Hello, world!";
        Assertions.assertEquals(expected,actual);
    }

/*
    @Test
    void printLineWithLineBreak() {
        StringWriter sw = new StringWriter();
        try (ImprintPrintWriter writer = new ImprintPrintWriter(sw, "[PREFIX] ", " XXX\n")) {
            writer.println("Hello, world!");  //No line-break printed!
            writer.flush();
        }
        String actual=sw.toString();
        String expected= "[PREFIX] Hello, world! XXX\n[PREFIX] ";
        Assertions.assertEquals(expected,actual);
    }
*/
}
