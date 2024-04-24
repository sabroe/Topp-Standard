package com.yelstream.topp.standard.lang;

import lombok.experimental.UtilityClass;

import java.util.List;
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

    /**
     * Indicates, if a text is non-empty.
     * @param text Text to test.
     *             This may be {@code null}.
     * @return Indicates, if text is non-empty.
     */
    public static boolean isNonEmpty(String text) {
        return !isEmpty(text);
    }

    /**
     * Returns the given text if it is non-{@code null} and otherwise an alternative text.
     * @param text Tested text.
     * @param alternativeText Alternative text.
     * @return Resulting text.
     */
    public static String nonNullOrElse(String text,
                                       String alternativeText) {
        return text!=null?text:alternativeText;
    }

    /**
     * Returns the given text if it is non-{@code null} and otherwise an alternative text.
     * @param text Tested text.
     * @param alternativeTextSupplier Supplier of alternative text.
     * @return Resulting text.
     */
    public static String nonNullOrElse(String text,
                                       Supplier<String> alternativeTextSupplier) {
        return text!=null?text:alternativeTextSupplier.get();
    }

    /**
     * Returns the given text if it is non-empty and otherwise an alternative text.
     * @param text Tested text.
     * @param alternativeText Alternative text.
     * @return Resulting text.
     */
    public static String nonEmptyOrElse(String text,
                                        String alternativeText) {
        return isNonEmpty(text)?text:alternativeText;
    }

    /**
     * Returns the given text if it is non-empty and otherwise an alternative text.
     * @param text Tested text.
     * @param alternativeTextSupplier Supplier of alternative text.
     * @return Resulting text.
     */
    public static String nonEmptyOrElse(String text,
                                        Supplier<String> alternativeTextSupplier) {
        return isNonEmpty(text)?text:alternativeTextSupplier.get();
    }

    /**
     * Filters a list of texts to a reduced list containing non-{@code null}, non-empty values.
     * @param texts Texts to reduce.
     * @return Reduced texts.
     */
    public static List<String> distinct(List<String> texts) {
        return texts==null?null:texts.stream().map(Strings::trim).filter(Strings::isNonEmpty).distinct().toList();
    }
}
