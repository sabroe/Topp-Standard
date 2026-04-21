package com.yelstream.topp.standard.operation;

import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * Utilities addressing instances of {@link Class}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-21
 */
@UtilityClass
public class ClassOps {
    /**
     * Returns the fully qualified name of a class.
     * <p>
     *     If the provided class reference is {@code null}, an empty {@link Optional} is returned.
     *     Otherwise, the class's fully qualified name (as returned by {@link Class#getName()}) is provided.
     * </p>
     * <p>
     *     This method is a null-safe convenience wrapper around {@link Class#getName()}.
     * </p>
     * @param clazz Class whose name should be returned.
     *              This may be {@link null}.
     * @return Contains the fully qualified class name, or empty if the class reference is {@code null}.
     */
    public static Optional<String> getName(Class<?> clazz) {
        return Optional.ofNullable(clazz).map(Class::getName);
    }
}
