package com.yelstream.topp.standard.gradle.contribution.tool.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Supplier;

/**
 * Utilities addressing configuration by Gradle properties.
 * <p>
 *     Provides methods to resolve configuration values by evaluating
 *     multiple sources in order of priority.
 * </p>
 * @author Morten Sabroe Mortensen
 * @version 1.1
 * @since 2024-11-26
 */
@UtilityClass
public class Configuration {
    /**
     * Resolves the first non-null boolean value from the provided suppliers.
     * The suppliers are evaluated in the order they are provided,
     * and the first non-null value is returned.
     * If all suppliers return null, the default value of {@code false} is returned.
     * @param sources An array of boolean suppliers, evaluated in order.
     * @return The first non-null boolean value, or {@code false} if no value is found.
     */
    public static boolean resolveBoolean(Supplier<Boolean>... sources) {
        for (Supplier<Boolean> source : sources) {
            Boolean value = safeGet(source);
            if (value != null) {
                return value;
            }
        }
        return false;
    }

    /**
     * Resolves the first non-null boolean value from the provided array.
     * The array is evaluated in the order the elements are provided,
     * and the first non-null value is returned.
     * If all elements are null, the default value of {@code false} is returned.
     * @param sources An array of boolean values, evaluated in order.
     * @return The first non-null boolean value, or {@code false} if no value is found.
     */
    public static boolean resolveBoolean(Boolean... sources) {
        for (Boolean source : sources) {
            if (source != null) {
                return source;
            }
        }
        return false;
    }

    /**
     * Resolves the first non-null boolean value from a list of suppliers.
     * The suppliers in the list are evaluated in order, and the first non-null value is returned.
     * If all suppliers return null, the default value of {@code false} is returned.
     * @param sources A list of boolean suppliers, evaluated in order.
     * @return The first non-null boolean value, or {@code false} if no value is found.
     */
    public static boolean resolveBoolean(List<Supplier<Boolean>> sources) {
        for (Supplier<Boolean> source : sources) {
            Boolean value = safeGet(source);
            if (value != null) {
                return value;
            }
        }
        return false;
    }

    /**
     * Resolves the first non-null boolean value from a list of booleans.
     * The list is evaluated in order, and the first non-null value is returned.
     * If all values are null, the default value of {@code false} is returned.
     * @param sources A list of boolean values, evaluated in order.
     * @return The first non-null boolean value, or {@code false} if no value is found.
     */
/*
    public static boolean resolveBoolean(List<Boolean> sources) {
        for (Boolean source : sources) {
            if (source != null) {
                return source;
            }
        }
        return false;
    }
*/

    /**
     * Resolves the first non-null, non-empty string value from the provided suppliers.
     * The suppliers are evaluated in the order they are provided,
     * and the first non-null, non-empty string value is returned (after trimming).
     * If all suppliers return null or empty strings, {@code null} is returned.
     * @param sources An array of string suppliers, evaluated in order.
     * @return The first non-null, non-empty string value, or {@code null} if no value is found.
     */
    public static String resolveString(Supplier<String>... sources) {
        for (Supplier<String> source : sources) {
            String value = safeGet(source);
            if (isNonEmpty(value)) {
                return value.trim();
            }
        }
        return null;
    }

    /**
     * Resolves the first non-null, non-empty string value from the provided array.
     * The array is evaluated in the order the elements are provided,
     * and the first non-null, non-empty string value is returned (after trimming).
     * If all elements are null or empty strings, {@code null} is returned.
     * @param sources An array of string values, evaluated in order.
     * @return The first non-null, non-empty string value, or {@code null} if no value is found.
     */
    public static String resolveString(String... sources) {
        for (String source : sources) {
            if (isNonEmpty(source)) {
                return source.trim();
            }
        }
        return null;
    }

    /**
     * Resolves the first non-null, non-empty string value from a list of suppliers.
     * The suppliers in the list are evaluated in order,
     * and the first non-null, non-empty string value is returned (after trimming).
     * If all suppliers return null or empty strings, {@code null} is returned.
     * @param sources A list of string suppliers, evaluated in order.
     * @return The first non-null, non-empty string value, or {@code null} if no value is found.
     */
    public static String resolveString(List<Supplier<String>> sources) {
        for (Supplier<String> source : sources) {
            String value = safeGet(source);
            if (isNonEmpty(value)) {
                return value.trim();
            }
        }
        return null;
    }

    /**
     * Resolves the first non-null, non-empty string value from a list of strings.
     * The list is evaluated in order, and the first non-null, non-empty string value is returned (after trimming).
     * If all values are null or empty strings, {@code null} is returned.
     * @param sources A list of string values, evaluated in order.
     * @return The first non-null, non-empty string value, or {@code null} if no value is found.
     */
/*
    public static String resolveString(List<String> sources) {
        for (String source : sources) {
            if (isNonEmpty(source)) {
                return source.trim();
            }
        }
        return null;
    }
*/

    /**
     * Safely retrieves the value from a supplier.
     * @param supplier The supplier to evaluate.
     * @param <T> The type of the value.
     * @return The value, or null if the supplier is null or throws an exception.
     */
    private static <T> T safeGet(Supplier<T> supplier) {
        try {
            return supplier != null ? supplier.get() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a string is non-null, trimmed, and has a length greater than zero.
     * @param value The string to check.
     * @return True if the string is non-empty, false otherwise.
     */
    private static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


    /**
     * Determines whether any of the provided sources resolves to a truthy value.
     * A truthy value is a non-null boolean true or a non-null,
     * non-empty string that evaluates to true when parsed as a boolean.
     * @param sources An array of sources, evaluated in order.
     *                Sources may be direct values or suppliers.
     * @return True if any source resolves to a truthy value, false otherwise.
     */
    public static boolean isEnabled(Object... sources) {
        for (Object source : sources) {
            Boolean resolved = resolveBooleanFromObject(source);
            if (Boolean.TRUE.equals(resolved)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether any of the provided sources resolves to a truthy value.
     * A truthy value is a non-null boolean true or a non-null,
     * non-empty string that evaluates to true when parsed as a boolean.
     * @param sources A list of sources, evaluated in order.
     *                Sources may be direct values or suppliers.
     * @return True if any source resolves to a truthy value, false otherwise.
     */
    public static boolean isEnabled(List<Object> sources) {
        for (Object source : sources) {
            Boolean resolved = resolveBooleanFromObject(source);
            if (Boolean.TRUE.equals(resolved)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the first valid definition from the provided sources.
     * A valid definition is a non-null, non-empty string or a number
     * that is convertible to a string.
     * @param sources An array of sources, evaluated in order.
     *                Sources may be direct values or suppliers.
     * @return The first valid definition as a string, or null if no definition is found.
     */
    public static String getDefinition(Object... sources) {
        for (Object source : sources) {
            String resolved = resolveStringFromObject(source);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
    }

    /**
     * Retrieves the first valid definition from the provided sources.
     * A valid definition is a non-null, non-empty string or a number
     * that is convertible to a string.
     * @param sources A list of sources, evaluated in order.
     *                Sources may be direct values or suppliers.
     * @return The first valid definition as a string, or null if no definition is found.
     */
    public static String getDefinition(List<Object> sources) {
        for (Object source : sources) {
            String resolved = resolveStringFromObject(source);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
    }

    /**
     * Resolves a boolean value from an object.
     * Accepts booleans, strings (parsed as booleans), and suppliers of these types.
     * @param source The source object to resolve.
     * @return The resolved boolean value, or null if not resolvable.
     */
    private static Boolean resolveBooleanFromObject(Object source) {
        if (source instanceof Boolean) {
            return (Boolean) source;
        }
        if (source instanceof Supplier) {
            return resolveBooleanFromObject(safeGet((Supplier<?>) source));
        }
        if (source instanceof String) {
            return Boolean.parseBoolean(((String) source).trim());
        }
        return null;
    }

    /**
     * Resolves a string value from an object.
     * Accepts strings, numbers, and suppliers of these types.
     * @param source The source object to resolve.
     * @return The resolved string value, or null if not resolvable.
     */
    private static String resolveStringFromObject(Object source) {
        if (source instanceof String) {
            String value = ((String) source).trim();
            return isNonEmpty(value) ? value : null;
        }
        if (source instanceof Number) {
            return source.toString();
        }
        if (source instanceof Supplier) {
            return resolveStringFromObject(safeGet((Supplier<?>) source));
        }
        return null;
    }
}
