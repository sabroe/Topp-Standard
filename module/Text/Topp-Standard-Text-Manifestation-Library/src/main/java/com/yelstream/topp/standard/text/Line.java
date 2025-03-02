package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Line of text.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-02
 */
@AllArgsConstructor(staticName="of")
@SuppressWarnings("LombokGetterMayBeUsed")
public class Line {
    /**
     *
     */
    @Getter
    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
