package com.yelstream.topp.standard.text.io;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class CustomPrintWriter extends PrintWriter {
    private final String lineEnding;
    private final String prefix;
    private boolean atLineStart = true; // Track whether a new line is starting

    public CustomPrintWriter(Writer out, String prefix, String lineEnding) {
        super(out);
        this.prefix = prefix;
        this.lineEnding = lineEnding;
    }

    @Override
    public void write(String s, int off, int len) {
        String text = s.substring(off, off + len);
        text = processText(text);
        super.write(text, 0, text.length());
    }

    @Override
    public void println() {
        write("\n"); // Only insert a logical newline, processText will handle the custom line-ending
    }

    private String processText(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (atLineStart) {
                result.append(prefix);
                atLineStart = false;
            }
            if (c == '\n') { // Handle new line correctly
                result.append(lineEnding);
                atLineStart = true;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }


    public static void main(String[] args) {
        StringWriter sw = new StringWriter();
        try (CustomPrintWriter writer = new CustomPrintWriter(sw, "[PREFIX] ", " XXX\n")) {
            writer.println("Hello, world!");
            writer.println("This is a test.");
            writer.print("Partial line... ");
            writer.println("continued.");
        }
        System.out.println(sw.toString());
    }


}
