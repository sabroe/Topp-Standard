/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

    /**
     * Indicates, if two texts are equal.
     * <p>
     *     Note that values may be {@code null}.
     * </p>
     * @param a First text.
     *          This may be {@code null}.
     * @param b Second text.
     *          This may be {@code null}.
     * @return Indicates, if texts are equal.
     *         This may be {@code null}.
     */
    public static boolean equals(String a, String b) {
        return Comparables.equals(a,b);
    }
}
