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

import com.yelstream.topp.standard.text.line.LineBreaks;
import com.yelstream.topp.standard.text.line.LineSeparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

/**
 * Multiline text block.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-20
 */
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@SuppressWarnings("LombokGetterMayBeUsed")
public final class Text {
    /**
     * Default line separator.
     */
    public static final LineSeparator DEFAULT_LINE_SEPARATOR=LineBreaks.DEFAULT_LINE_BREAK;

    /**
     * Lines.
     */
    @Getter
    @Singular
    @NonNull
    private final List<String> lines;

    /**
     * Line separator.
     */
    @Getter
    @lombok.Builder.Default
    private final LineSeparator separator=DEFAULT_LINE_SEPARATOR;

    public int length() {
        return lines.size();
    }

    public List<String> toLines() {
        return new ArrayList<>(lines);  //Yes, return a mutable list!
    }

    public String toString() {
        return separator.join(lines);
    }

    public Text head(int count) {
        if (count<0) {
            throw new IllegalArgumentException(String.format("Failure to create new text block; count must be non-negative, but is %d!",count));
        }
        List<String> headLines=lines.subList(0,Math.min(count,lines.size()));
        return toBuilder().clearLines().lines(headLines).build();
    }

    public Text tail(int count) {
        if (count<0) {
            throw new IllegalArgumentException(String.format("Failure to create new text block; count must be non-negative, but is %d!",count));
        }
        int start=Math.max(lines.size()-count,0);
        List<String> tailLines=lines.subList(start,lines.size());
        return toBuilder().clearLines().lines(tailLines).build();
    }

    public Text replace(LineSeparator separator) {
        return toBuilder().separator(separator).build();
    }

    /**
     * Transform each line individually.
     * This creates a new text block.
     * @param operator Transforms a single line.
     * @return Created text block.
     */
    public Text map(UnaryOperator<String> operator) {
        List<String> newLines=lines.stream().map(operator).toList();
        return toBuilder().clearLines().lines(List.copyOf(newLines)).build();
    }

    /**
     * Transform each indexed line individually.
     * This creates a new text block.
     * @param operator Transforms a single line.
     *                 Index starts at zero.
     * @return Created text block.
     */
    public Text map(BiFunction<Integer,String,String> operator) {
        List<String> newLines=IntStream.range(0,lines.size()).mapToObj(i->operator.apply(i,lines.get(i))).toList();
        return toBuilder().clearLines().lines(List.copyOf(newLines)).build();
    }

    /**
     * Transforms the entire list of lines at once.
     * This creates a new text block.
     * @param operator Transforms the entire list of lines.
     * @return Created text block.
     */
    @SuppressWarnings("java:S1117")
    public Text adjust(UnaryOperator<List<String>> operator) {
        List<String> lines=new ArrayList<>(this.lines);  //Mutable copy of lines.
        List<String> newLines=List.copyOf(operator.apply(lines));
        return toBuilder().clearLines().lines(newLines).build();
    }

    public Text append(Text text) {
        return toBuilder().appendText(text).build();
    }

    public Text prepend(Text text) {
        return toBuilder().prependText(text).build();
    }

    public Text subText(int start, int end) {
        if (start<0 || end<0 || start>end) {
            throw new IllegalArgumentException(String.format("Failure to create new text block; start and end indices must be non-negative, and start must not be greater than end, but start is %d and end is %d!!",start,end));
        }
        List<String> subLines=lines.subList(start,Math.min(end,lines.size()));
        return toBuilder().clearLines().lines(subLines).build();
    }

    @SuppressWarnings("FieldMayBeFinal")
    public static class Builder {
        public Builder appendLines(List<String> lines) {
            lines.forEach(this::line);
            return this;
        }

        public Builder prependLines(List<String> lines) {
            List<String> newLines=new ArrayList<>(lines);
            newLines.addAll(this.lines);
            return clearLines().lines(newLines);
        }

        public Builder text(String text) {
            if (separator$value==null) {
                separator$value=DEFAULT_LINE_SEPARATOR;  //Note: IDEA may have trouble have its compiler recognize '$default$separator()' as accessible!
            }
            String normalizedText=separator$value.normalize(text);
            List<String> lines=separator$value.split(normalizedText);
            return clearLines().lines(lines);
        }

        public Builder appendText(String text) {
            if (separator$value==null) {
                separator$value=DEFAULT_LINE_SEPARATOR;  //Note: IDEA may have trouble have its compiler recognize '$default$separator()' as accessible!
            }
            String normalizedText=separator$value.normalize(text);
            List<String> newLines=separator$value.split(normalizedText);
            return appendLines(newLines);
        }

        public Builder prependText(String text) {
            if (separator$value==null) {
                separator$value=DEFAULT_LINE_SEPARATOR;  //Note: IDEA may have trouble have its compiler recognize '$default$separator()' as accessible!
            }
            String normalizedText=separator$value.normalize(text);
            List<String> newLines=separator$value.split(normalizedText);
            return prependLines(newLines);
        }

        public Builder appendText(Text text) {
            return appendLines(text.lines);  //Yes, ignore 'text.getLineBreak()', pick the lines only!
        }

        public Builder prependText(Text text) {
            return prependLines(text.lines);  //Yes, ignore 'text.getLineBreak()', pick the lines only!
        }

        public Text build() {
            return new Text(List.copyOf(lines),this.separator$value);
        }
    }

    public static Text of(String text,
                          LineSeparator separator) {
        return builder().separator(separator).text(text).build();
    }

    public static Text of(String text) {
        return builder().text(text).build();
    }

    public static Text of(List<String> lines) {
        return builder().lines(lines).build();
    }
}
