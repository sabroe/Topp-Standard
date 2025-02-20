package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

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
    @Getter
    @Singular
    @NonNull
    private final List<String> lines;

    @Getter
    @lombok.Builder.Default
    private final LineBreak lineBreak=LineBreaks.DEFAULT_LINE_BREAK;

    public int length() {
        return lines.size();
    }

    public List<String> toLines() {
        return new ArrayList<>(lines);  //Yes, return a mutable list!
    }

    public String toString() {
        return lineBreak.join(lines);
    }

    public Text head(int count) {
        if (count<0) {
            throw new IllegalArgumentException(String.format("Failure to create new text block; count must be non-negative, but is %d!",count));
        }
        List<String> headLines=lines.subList(0,Math.min(count,lines.size()));
        return toBuilder().lines(headLines).build();
    }

    public Text tail(int count) {
        if (count<0) {
            throw new IllegalArgumentException(String.format("Failure to create new text block; count must be non-negative, but is %d!",count));
        }
        int start=Math.max(lines.size()-count,0);
        List<String> tailLines=lines.subList(start,lines.size());
        return toBuilder().lines(tailLines).build();
    }

    public Text replace(LineBreak lineBreak) {
        return toBuilder().lineBreak(lineBreak).build();
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

    public static class Builder {
        public Builder appendLines(List<String> lines) {
            lines.forEach(this::line);
            return this;
        }

        public Builder prependLines(List<String> lines) {
            List<String> newLines=new ArrayList<>(lines);
            newLines.addAll(this.lines);
            return lines(newLines);
        }

        public Builder text(String text) {
            String normalizedText=lineBreak$value.normalize(text);
            List<String> lines=lineBreak$value.split(normalizedText);
            return lines(lines);
        }

        public Builder appendText(String text) {
            String normalizedText=lineBreak$value.normalize(text);
            List<String> newLines=lineBreak$value.split(normalizedText);
            return appendLines(newLines);
        }

        public Builder prependText(String text) {
            String normalizedText=lineBreak$value.normalize(text);
            List<String> newLines=lineBreak$value.split(normalizedText);
            return prependLines(newLines);
        }

        public Builder appendText(Text text) {
            return appendLines(text.lines);  //Yes, ignore 'text.getLineBreak()', pick the lines only!
        }

        public Builder prependText(Text text) {
            return prependLines(text.lines);  //Yes, ignore 'text.getLineBreak()', pick the lines only!
        }
    }

    public static Text of(String text,
                          LineBreak lineBreak) {
        return builder().lineBreak(lineBreak).text(text).build();
    }

    public static Text of(String text) {
        return builder().text(text).build();
    }

    public static Text of(List<String> lines) {
        return builder().lines(lines).build();
    }
}
