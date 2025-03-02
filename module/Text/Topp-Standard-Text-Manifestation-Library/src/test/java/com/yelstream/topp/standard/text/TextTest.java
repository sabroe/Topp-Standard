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

import com.yelstream.topp.standard.text.line.LineSeparator;
import com.yelstream.topp.standard.text.line.StandardLineBreak;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests suite for {@code Text}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-02
 */
class TextTest {

    private static final LineSeparator LF = StandardLineBreak.LF.getLineBreak();
    private static final LineSeparator CRLF = StandardLineBreak.CRLF.getLineBreak();
    private static final LineSeparator CR = StandardLineBreak.CR.getLineBreak();

    @Test
    void testLength() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        assertEquals(3, text.length(), "Length of text should be 3");
    }

    @Test
    void testToLines() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        List<String> lines = text.toLines();
        assertEquals(3, lines.size(), "Lines size should be 3");
        assertEquals("Line 1", lines.getFirst(), "First line should be 'Line 1'");
    }

    @Test
    void testToString() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        assertEquals("Line 1\nLine 2\nLine 3", text.toString(), "String representation should match");
    }

    @Test
    void testHead() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text headText = text.head(2);
        assertEquals(2, headText.length(), "Head should have 2 lines");
        assertEquals("Line 1", headText.toLines().getFirst(), "First line of head should be 'Line 1'");
    }

    @Test
    void testTail() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text tailText = text.tail(2);
        assertEquals(2, tailText.length(), "Tail should have 2 lines");
        assertEquals("Line 2", tailText.toLines().getFirst(), "First line of tail should be 'Line 2'");
    }

    @Test
    void testReplaceSeparator() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text replacedText = text.replace(CRLF);
        assertEquals(CRLF.join(List.of("Line 1", "Line 2", "Line 3")), replacedText.toString(), "Separator should be replaced with CRLF");
    }

    @Test
    void testMapWithUnaryOperator() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text mappedText = text.map(line -> line.toUpperCase());
        assertEquals("LINE 1\nLINE 2\nLINE 3", mappedText.toString(), "All lines should be uppercased");
    }

    @Test
    void testMapWithBiFunction() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text mappedText = text.map((index, line) -> line + " (line " + index + ")");
        assertEquals("Line 1 (line 0)\nLine 2 (line 1)\nLine 3 (line 2)", mappedText.toString(), "Each line should be suffixed with its index");
    }

    @Test
    void testAdjust() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text adjustedText = text.adjust(lines -> {
            lines.add("Line 4");
            return lines;
        });
        assertEquals(4, adjustedText.length(), "Text should have 4 lines after adjustment");
    }

    @Test
    void testAppend() {
        Text text1 = Text.of("Line 1\nLine 2", LF);
        Text text2 = Text.of("Line 3\nLine 4", LF);
        Text appendedText = text1.append(text2);
        assertEquals(4, appendedText.length(), "Appended text should have 4 lines");
        assertEquals("Line 1\nLine 2\nLine 3\nLine 4", appendedText.toString(), "Appended text should be correct");
    }

    @Test
    void testPrepend() {
        Text text1 = Text.of("Line 1\nLine 2", LF);
        Text text2 = Text.of("Line 3\nLine 4", LF);
        Text prependedText = text1.prepend(text2);
        assertEquals(4, prependedText.length(), "Prepended text should have 4 lines");
        assertEquals("Line 3\nLine 4\nLine 1\nLine 2", prependedText.toString(), "Prepended text should be correct");
    }

    @Test
    void testAppendText() {
        Text original = Text.of(List.of("Line 1", "Line 2"));
        String additionalText = "Line 3\nLine 4";

        Text modified = original.toBuilder().appendText(additionalText).build();

        assertEquals(List.of("Line 1", "Line 2", "Line 3", "Line 4"), modified.toLines());
        assertNotSame(original, modified, "Text should be immutable and return a new instance");
    }

    @Test
    void testPrependText() {
        Text original = Text.of(List.of("Line 3", "Line 4"));
        String additionalText = "Line 1\nLine 2";

        Text modified = original.toBuilder().prependText(additionalText).build();

        assertEquals(List.of("Line 1", "Line 2", "Line 3", "Line 4"), modified.toLines());
        assertNotSame(original, modified, "Text should be immutable and return a new instance");
    }

    @Test
    void testSubText() {
        Text text = Text.of("Line 1\nLine 2\nLine 3", LF);
        Text subText = text.subText(1, 2);
        assertEquals(1, subText.length(), "Subtext should have 1 line");
        assertEquals("Line 2", subText.toLines().getFirst(), "Subtext should be 'Line 2'");
    }

    @Test
    void testInvalidHead() {
        Text text = Text.of("Line 1\nLine 2", LF);
        assertThrows(IllegalArgumentException.class, () -> text.head(-1), "Head should throw exception for negative count");
    }

    @Test
    void testInvalidTail() {
        Text text = Text.of("Line 1\nLine 2", LF);
        assertThrows(IllegalArgumentException.class, () -> text.tail(-1), "Tail should throw exception for negative count");
    }

    @Test
    void testInvalidSubText() {
        Text text = Text.of("Line 1\nLine 2", LF);
        assertThrows(IllegalArgumentException.class, () -> text.subText(2, 1), "Subtext should throw exception for invalid range");
    }
}
