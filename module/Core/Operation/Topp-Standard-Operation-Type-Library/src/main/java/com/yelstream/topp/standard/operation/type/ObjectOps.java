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

package com.yelstream.topp.standard.operation.type;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utilities addressing instances of {@link Object}.
 *
 * <p>
 *   This covers the three core “type operations”:
 * </p>
 * <ul>
 *     <li>Name extraction ({@link #getName(Object)})</li>
 *     <li>Instance check ({@link #isInstance(Object, Class)})</li>
 *     <li>Casting ({@link #tryCast(Object, Class)}, {@link #tryCastOrNull(Object, Class)}, {@link #cast(Object, Class)})</li>
 * </ul>
 *
 * <p>
 *     About casting utilities with multiple result semantics:
 * </p>
 * <p>
 *    These methods act as interoperability adapters across different Java ecosystems.
 *    They expose the same core operation with distinct result
 *    models (Optional, null, strict) without enforcing a single style.
 * </p>
 * <pre>
 * ┌────────────────┬─────────────────────┬───────────────────────────────────┐
 * │ Method         │ Semantics           │ Consumers                         │
 * ├────────────────┼─────────────────────┼───────────────────────────────────┤
 * │ tryCast        │ Optional result     │ Functional / pipeline-based APIs  │
 * ├────────────────┼─────────────────────┼───────────────────────────────────┤
 * │ tryCastOrNull  │ Null result         │ Legacy / null-tolerant APIs       │
 * ├────────────────┼─────────────────────┼───────────────────────────────────┤
 * │ cast           │ Strict (fail-fast)  │ Domain logic / invariants         │
 * └────────────────┴─────────────────────┴───────────────────────────────────┘
 * </pre>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-21
 */
@UtilityClass
public class ObjectOps {
    /**
     * Returns the fully qualified name of the runtime class of a value.
     * @param value Value whose class name should be returned.
     *              May be {@code null}.
     * @return Fully qualified name of the runtime class.
     *         Is {@code null} if {@code value} is {@code null}.
     */
    public static String getName(Object value) {
        return value == null ? null : value.getClass().getName();
    }

    /**
     * Determines whether a value is an instance of a specific type.
     * <p>
     *     This method wraps {@link Class#isInstance(Object)} and defines null-tolerant behavior for the tested value.
     * </p>
     * <p>
     *     Behavior:
     * </p>
     * <ul>
     *     <li>If {@code value} is {@code null}, this method returns {@code false}.</li>
     *     <li>If {@code type} is {@code null}, a {@link NullPointerException} is thrown.</li>
     *     <li>Otherwise, returns {@code true} if the value is an instance of the type.</li>
     * </ul>
     * <p>
     *     This method is part of the {@code ObjectOps} type operation contract and
     *     is intended as a predicate primitive for runtime type checking.
     * </p>
     * @param value Value to test.
     *              This may be {@code null}.
     * @param type Type to test against.
     *             This must not be {@code null}.
     * @return Indicates, is the value is an instance of the type.
     */
    public static boolean isInstance(Object value,
                                     Class<?> type) {
        Objects.requireNonNull(type,"type");
        return Core.isInstance(value,type);
    }

    /**
     * Execute action if value is instance of type.
     */
    public static <T> void ifInstance(Object value,
                                      Class<T> type,
                                      Consumer<T> action) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(action, "action");
        if (Core.isInstance(value,type)) {
            action.accept(type.cast(value));
        }
    }

    public static <T> Optional<T> instanceOf(Object value,
                                             Class<T> type) {  //TO-DO: Move to core?
        Objects.requireNonNull(type, "type");
        return Optional.ofNullable(value).filter(type::isInstance).map(type::cast);
    }

    /**
     * Attempts to cast a value to a specific type.
     * <p>
     *     If the value is non-{@code null} and is an instance of the target type,
     *     it is returned wrapped in an {@link Optional}.
     *     Otherwise, an empty optional is returned.
     * </p>
     * <p>
     *     This method is the core casting primitive; all other cast methods delegate to it.
     * </p>
     * @param value Value to cast.
     *              This may be {@code null}
     * @param type Target class to cast to.
     *             This must not be {@code null}.
     * @param <T> Type of target value.
     * @return Container of the cast value if successful, otherwise empty.
     */
    public static <T> Optional<T> tryCast(Object value,
                                          Class<T> type) {
        return Core.tryCast(value,type);
    }

    /**
     * Attempts to cast a value to a specific type.
     * <p>
     *     If the value is not an instance of the target type or is {@code null},
     *     this method returns {@code null}.
     * </p>
     * <p>
     *     This is a convenience wrapper around {@link #tryCast(Object, Class)} for null-based APIs.
     * </p>
     * @param value Value to cast.
     *              This may be {@code null}
     * @param type Target class to cast to.
     *             This must not be {@code null}.
     * @param <T> Type of target value.
     * @return Cast value, or {@code null} if the cast is not possible.
     */
    public static <T> T tryCastOrNull(Object value,
                                      Class<T> type) {
        return Core.tryCast(value,type).orElse(null);
    }

    public static <T> T tryCastOr(Object value,
                                  Class<T> type,
                                  T fallback) {
        return Core.tryCast(value, type).orElse(fallback);
    }

    /**
     * Casts a value to a specific type.
     * <p>
     *     If the value is {@code null} or not an instance of the target type,
     *     a {@link ClassCastException} is thrown.
     * </p>
     * <p>
     *     This method provides a strict casting contract and delegates to {@link #tryCast(Object, Class)} for evaluation.
     * </p>
     * @param value Value to cast.
     *              This may be {@code null}
     * @param type Target class to cast to.
     *             This must not be {@code null}.
     * @param <T> Type of target value.
     * @return Cast value.
     * @throws ClassCastException Thrown in case the value is {@code null} or not of the specified type.
     */
    public static <T> T cast(Object value,
                             Class<T> type) {
        return Core.tryCast(value,type).orElseThrow(() -> new ClassCastException(
            "Failure to cast value; value type '%s' is not of type '%s'!".formatted(getName(value),ClassOps.getName(type))
        ));
    }

    public static <T> Optional<T> ofNullable(T value) {  //TO-DO: To core?
        return Optional.ofNullable(value);
    }

    public static <T> Optional<T> filter(T value,
                                         Predicate<T> predicate) {  //TO-DO: To core?
        Objects.requireNonNull(predicate, "predicate");
        return Optional.ofNullable(value).filter(predicate);
    }

    /**
     * Null-safe mapping over Object domain.
     */
    public static <T, R> Optional<R> map(T value,
                                         Function<T, R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return Core.map(value,mapper);
    }

    public static <T, R> Optional<R> flatMap(Object value,
                                             Class<T> type,
                                             Function<T, Optional<R>> mapper) {
        return Core.tryCast(value, type).flatMap(mapper);
    }

    public static <T, R> R mapOrNull(Object value,
                                     Class<T> type,
                                     Function<T, R> mapper) {
        return tryCast(value, type).map(mapper).orElse(null);
    }

/*
    public static <T, R> Optional<R> mapNonNull(Object value,
                                                Function<T, R> fn) {
        return Optional.ofNullable(value).map(v -> fn.apply((T) v));
    }
*/




    /**
     * Identity-based hash code.>
     */
    public static int identityHash(Object value) {
        return Core.identityHash(value);
    }




    /**
     * Internal semantic primitives for {@link ObjectOps}.
     * <p>
     *     This class contains the minimal implementations of object operations.
     *     Methods in the enclosing {@link ObjectOps} class delegate to these
     *     primitives and may expose alternative result styles, overloads, or convenience wrappers.
     * </p>
     * <p>
     *     This class is intended for internal reuse only and is not part of the primary public API surface.
     * </p>
     * <p>
     *     The outer {@link ObjectOps} contains presentation and trivial wrappers.
     *     So delegate to {@link Core} when the outer method is merely exposing another “view” of the same operation.
     *     But trivial one-liners that are already “presentation” do not need {@link Core}.
     * </p>
     * <p>
     *     Put in {@link Core}:
 *   * </p>
     * <ul>
     *     <li>Multi-step logic</li>
     *     <li>Reusable logic</li>
     *     <li>Logic shared by multiple public methods</li>
     *     <li>Semantic primitives</li>
     * </ul>
     * <p>
     *     Keep in outer {@link ObjectOps}:
     * </p>
     * <ul>
     *     <li>Wrappers / overloads</li>
     *     <li>Style variants</li>
     *     <li>Direct trivial operations</li>
     *     <li>Java façade methods</li>
     * </ul>
     */
    @UtilityClass
    static final class Core {
        /**
         * Determines whether a value is an instance of a specific type.
         * <p>
         *     This method wraps {@link Class#isInstance(Object)} and defines null-tolerant behavior for the tested value.
         * </p>
         * <p>
         *     Behavior:
         * </p>
         * <ul>
         *     <li>If {@code value} is {@code null}, this method returns {@code false}.</li>
         *     <li>If {@code type} is {@code null}, a {@link NullPointerException} is thrown.</li>
         *     <li>Otherwise, returns {@code true} if the value is an instance of the type.</li>
         * </ul>
         * <p>
         *     This method is part of the {@code ObjectOps} type operation contract and
         *     is intended as a predicate primitive for runtime type checking.
         * </p>
         * @param value Value to test.
         *              This may be {@code null}.
         * @param type Type to test against.
         *             This must not be {@code null}.
         * @return Indicates, is the value is an instance of the type.
         */
        public static boolean isInstance(Object value,
                                         Class<?> type) {
            Objects.requireNonNull(type,"type");
            return type.isInstance(value);
        }

        /**
         * Attempts to cast a value to a specific type.
         * <p>
         *     If the value is non-{@code null} and is an instance of the target type,
         *     it is returned wrapped in an {@link Optional}.
         *     Otherwise, an empty optional is returned.
         * </p>
         * <p>
         *     This method is the core casting primitive; all other cast methods delegate to it.
         * </p>
         * @param value Value to cast.
         *              This may be {@code null}
         * @param type Target class to cast to.
         *             This must not be {@code null}.
         * @param <T> Type of target value.
         * @return Container of the cast value if successful, otherwise empty.
         */
        public static <T> Optional<T> tryCast(Object value,
                                              Class<T> type) {
            Objects.requireNonNull(type,"type");
            return Optional.ofNullable(value).filter(type::isInstance).map(type::cast);
        }

        /**
         * Null-safe mapping.
         */
        public static <T,R> Optional<R> map(T value,
                                           Function<T, R> mapper) {
            Objects.requireNonNull(mapper, "mapper");
            return Optional.ofNullable(value).map(mapper);
        }

        /**
         * Identity-based hash code.
         */
        public static int identityHash(Object value) {
            return System.identityHashCode(value);
        }

        public static boolean same(Object a, Object b) {
            return a == b;
        }
    }

    public static <T> Subject<T> subject(T value) {
        return Subject.of(value);
    }
}
