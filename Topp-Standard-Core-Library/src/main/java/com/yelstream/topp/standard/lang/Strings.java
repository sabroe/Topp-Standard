package com.yelstream.topp.standard.lang;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

/**
 * Addresses instances of {@link String}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@UtilityClass
public class Strings {
    /**
     * Trims a text.
     * @param text Text to trim.
     *             This may be {@code null}.
     * @return Trimmed text.
     */
    public static String trim(String text) {
        return text==null?null:text.trim();
    }

    /**
     * Indicates, if a text is empty.
     * @param text Text to test.
     *             This may be {@code null}.
     * @return Indicates, if text is empty.
     */
    public static boolean isEmpty(String text) {
        return text==null || text.isEmpty();
    }

    public static String nonNull(String text,
                                 String defaultText) {
        return text!=null?text:defaultText;
    }

    public static String nonNull(String text,
                                 Supplier<String> defaultTextSupplier) {
        return text!=null?text:defaultTextSupplier.get();
    }
}
