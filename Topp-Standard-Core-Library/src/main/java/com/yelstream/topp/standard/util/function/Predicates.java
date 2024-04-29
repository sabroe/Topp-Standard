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
