/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.util.function;

import lombok.experimental.UtilityClass;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Utility addressing instances of {@link Predicate}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2023-12-17
 */
@UtilityClass
public class Predicates {
    /**
     * Tests a predicate.
     * @param predicate Predicate.
     *                  This may be {@code null}.
     * @param value Tested value
     * @return Result of test.
     * @param <T> Type of predicate value.
     */
    public static <T> boolean test(Predicate<T> predicate,
                                   T value) {
        return predicate!=null && predicate.test(value);
    }

    /**
     * Combines predicates using {@code and}.
     * <p>
     *     This may be used for chaining predicates, starting with {@code null}.
     * </p>
     * @param predicate1 First predicate.
     *                  This may be {@code null}.
     * @param predicate2 Second predicate.
     *                  This must be non-{@code null}.
     * @return Resulting predicate.
     * @param <T> Type of predicate value.
     */
    public static <T> Predicate<T> and(Predicate<T> predicate1,
                                       Predicate<T> predicate2) {
        return predicate1==null?predicate2:predicate1.and(predicate2);
    }

    /**
     * Combines predicates using {@code or}.
     * <p>
     *     This may be used for chaining predicates, starting with {@code null}.
     * </p>
     * @param predicate1 First predicate.
     *                  This may be {@code null}.
     * @param predicate2 Second predicate.
     *                  This must be non-{@code null}.
     * @return Resulting predicate.
     * @param <T> Type of predicate value.
     */
    public static <T> Predicate<T> or(Predicate<T> predicate1,
                                       Predicate<T> predicate2) {
        return predicate1==null?predicate2:predicate1.or(predicate2);
    }

    /**
     * Creates a textual matcher by matching a specific value.
     * @param value Value to match.
     * @return Created matcher.
     */
    @SuppressWarnings("java:S1201")
    public static Predicate<String> equals(String value) {
        if (value==null) {
            throw new IllegalArgumentException("Failure to construct predicate for 'equals'; argument must be non-null!");
        }
        return x->x!=null && x.equals(value);
    }

    /**
     * Creates a textual matcher by matching a specific value while disregarding the case lettering.
     * @param value Value to match while disregarding the case.
     * @return Created matcher.
     */
    public static Predicate<String> equalsIgnoreCase(String value) {
        if (value==null) {
            throw new IllegalArgumentException("Failure to construct predicate for 'equalsIgnoreCase'; argument must be non-null!");
        }
        return x->x!=null && x.equalsIgnoreCase(value);
    }

    /**
     * Creates a textual matcher which matches a specific sub-value.
     * @param value Sub-value to match.
     * @return Created matcher.
     */
    public static Predicate<String> contains(String value) {
        if (value==null) {
            throw new IllegalArgumentException("Failure to construct predicate for 'contains'; argument must be non-null!");
        }
        return x->x!=null && x.contains(value);
    }

    /**
     * Creates a textual matcher by matching a regular expression.
     * @param regEx Regular expression.
     * @return Created matcher.
     */
    public static Predicate<String> matches(String regEx) {
        if (regEx==null) {
            throw new IllegalArgumentException("Failure to construct predicate for 'matches'; argument must be non-null!");
        }
        return x->x!=null && x.matches(regEx);
    }

    /**
     * Creates a textual matcher by matching a regular expression.
     * @param pattern Regular expression.
     * @return Created matcher.
     */
    public static Predicate<String> matches(Pattern pattern) {
        if (pattern==null) {
            throw new IllegalArgumentException("Failure to construct predicate for 'matches'; argument must be non-null!");
        }
        return x->x!=null && pattern.matcher(x).matches();
    }
}
