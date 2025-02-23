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

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Print-writer that prefixes each line with a given imprint (prefix).
 * Supports strict and lazy prefixing semantics.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
//@SuppressWarnings("LombokGetterMayBeUsed")
//@AllArgsConstructor(staticName="of")
public class ImprintPrintWriter extends PrintWriter {

    @Getter
    @AllArgsConstructor
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class Imprint {
        private final String lineEnding;

        private final String prefix;
    }

    @Getter
    @AllArgsConstructor
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class Position {
        /**
         * Indicates weather a new line is starting.
         */
        @lombok.Builder.Default
        private int column = 0;

        @lombok.Builder.Default
        private int line = 0;

        /**
         * Indicates weather a "line start" (prefix) has been printed for the current line.
         */
        @lombok.Builder.Default
        private boolean imprintedLineStart = false;

        public void nextLine() {
            column = 0;
            line++;
            imprintedLineStart = false;
        }
    }

    @Getter
    @AllArgsConstructor
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class Semantics {
        private final PrefixSemantics prefixSemantics;
    }

    private final Imprint imprint;

    private final Position position;

    private final Semantics semantics;

    @Getter
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum PrefixSemantics {  //Eager, Lazy?
        Strict,//(ImprintPrintWriter::strictProcessText),
        Lazy;//(ImprintPrintWriter::lazyProcessText);

//        private final BiFunction<ImprintPrintWriter,String,String> processText;
    }

    private final Writer writer;  //Note: THis to allow for 'toBuilder' set to true.

    @lombok.Builder(builderClassName = "Builder", toBuilder = true)
    private ImprintPrintWriter(Writer writer,
                               Imprint imprint,
                               Position position,
                               Semantics semantics) {
        super(writer);
        this.writer = writer;
        this.imprint = imprint;
        this.position = position;
        this.semantics = semantics;
    }

    public static class Builder {
        public ImprintPrintWriter build() {
            if (imprint==null) {
                imprint=Imprint.builder().build();
            }
            if (position==null) {
                position=Position.builder().build();
            }
            if (semantics==null) {
                semantics=Semantics.builder().build();
            }
            ImprintPrintWriter instance = new ImprintPrintWriter(writer, imprint, position, semantics);
            instance.write("");
            return instance;
        }
    }

    @Override
    public void write(String s, int off, int len) {
        String text = s.substring(off, off + len);
        text = processText(text);
        super.write(text, 0, text.length());
    }

    @Override
    public void println() {
        write("\n");  //Write logical newline, triggering processing logic!
    }

    @Override
    public void flush() {
        if (semantics.prefixSemantics == PrefixSemantics.Strict) {
            if (!position.imprintedLineStart) {
                String prefix = imprint.prefix;
                if (prefix!=null) {
                    super.write(prefix,0,prefix.length());
                }
                position.imprintedLineStart = true;
            }
        }
        super.flush();
    }

    @Override
    public void close() {

        //TO-DO: If not actually closed then <<add suffix 'XXX' without break '\n'>>!
        super.close();
    }

    private String processText(String text) {
        StringBuilder result = new StringBuilder();

        if (semantics.prefixSemantics == PrefixSemantics.Strict) {
            if (!position.imprintedLineStart) {
                if (imprint.prefix!=null) {
                    result.append(imprint.prefix);
                }
                position.imprintedLineStart = true;
            }
        }

        if (text != null) {
            for (char c : text.toCharArray()) {

                if (!position.imprintedLineStart) {
                    if (imprint.prefix!=null) {
                        result.append(imprint.prefix);
                    }
                    position.imprintedLineStart = true;
                }

                if (c == '\n') { // Handle new line correctly
                    if (imprint.lineEnding==null) {
                        result.append(c);
                    } else {
                        result.append(imprint.lineEnding);
                    }
                    position.nextLine();
                } else {
                    result.append(c);
                    position.line++;
                }
            }
        }
        return result.toString();
    }

    public static ImprintPrintWriter of(Writer writer) {
        return builder().writer(writer).build();
    }

    public static ImprintPrintWriter of(Writer writer,
                                        String prefix) {
        ImprintPrintWriter.Builder builder=builder().writer(writer);
        builder.imprint(Imprint.builder().prefix(prefix).build());
        return builder.build();
    }


    public static ImprintPrintWriter of(Writer writer,
                                        String prefix,
                                        String lineEnding) {
        ImprintPrintWriter.Builder builder=builder().writer(writer);
        builder.imprint(Imprint.builder().prefix(prefix).lineEnding(lineEnding).build());
        return builder.build();
    }

    public static ImprintPrintWriter of(Writer writer,
                                        String prefix,
                                        String lineEnding,
                                        PrefixSemantics prefixSemantics) {
        ImprintPrintWriter.Builder builder=builder().writer(writer);
        builder.imprint(Imprint.builder().prefix(prefix).lineEnding(lineEnding).build());
        builder.semantics(Semantics.builder().prefixSemantics(prefixSemantics).build());
        return builder.build();
    }

    public static ImprintPrintWriter of(Writer writer,
                                        Imprint imprint) {
        return builder().writer(writer).imprint(imprint).build();
    }

    public static ImprintPrintWriter of(Writer writer,
                                        Imprint imprint,
                                        PrefixSemantics prefixSemantics) {
        ImprintPrintWriter.Builder builder=builder().writer(writer);
        builder.imprint(imprint);
        builder.semantics(Semantics.builder().prefixSemantics(prefixSemantics).build());
        return builder.build();
    }
}
