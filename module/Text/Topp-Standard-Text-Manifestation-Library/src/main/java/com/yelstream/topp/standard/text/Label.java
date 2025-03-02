package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Label of text.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-21
 */
@AllArgsConstructor(staticName="of")
@SuppressWarnings("LombokGetterMayBeUsed")
public class Label {
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
