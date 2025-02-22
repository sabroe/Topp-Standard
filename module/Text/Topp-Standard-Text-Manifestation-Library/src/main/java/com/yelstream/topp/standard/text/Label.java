package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * Label of text.
 *
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-21
 */
@AllArgsConstructor
@SuppressWarnings("LombokGetterMayBeUsed")
public class Label {
    /**
     * Generates a text label.
     * This may depend upon an index, usually a line number.
     */
    @Getter
    private final Function<Integer,String> generator;

    public String getLabel(int index) {
return null;
    }

}
