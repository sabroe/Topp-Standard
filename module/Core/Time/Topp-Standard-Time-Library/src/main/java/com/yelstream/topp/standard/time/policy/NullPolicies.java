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

package com.yelstream.topp.standard.time.policy;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Utility addressing instances of {@link NullPolicy}.
 * <p>
 *     Provides standard null-policy implementations and convenient helper methods for common use cases.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-16
 */
@UtilityClass
public class NullPolicies {

    public static final NullPolicy STRICT_NULL_POLICY = NullPolicies::strict;

    public static final NullPolicy NULLABLE_NULL_POLICY = NullPolicies::nullable;

    public static final NullPolicy NULLAWARE_NULL_POLICY = NullPolicies::nullAware;

    public static <T,R> R strict(T input,
                                 Function<T, R> present,
                                 Supplier<R> absent) {
        Objects.requireNonNull(present, "present");
        Objects.requireNonNull(absent, "absent");
        return present.apply(Objects.requireNonNull(input,"input"));
    }

    public static <T,R> R nullable(T input,
                                   Function<T, R> present,
                                   Supplier<R> absent) {
        Objects.requireNonNull(present, "present");
        Objects.requireNonNull(absent, "absent");
        return input == null ? absent.get() : present.apply(input);
    }

    public static <T,R> R nullAware(T input,
                                    Function<T, R> present,
                                    Supplier<R> absent) {
        Objects.requireNonNull(present, "present");
        Objects.requireNonNull(absent, "absent");
        return present.apply(input);
    }

    public <T> T map(NullPolicy policy,
                     T input,
                     UnaryOperator<T> operator,
                     Supplier<T> absent) {
        absent = absent==null ? ()->null : absent;
        return policy.apply(input, operator, absent);
    }

    public <T,R> R from(NullPolicy policy,
                        T input,
                        Function<T,R> operator,
                        Supplier<R> absent) {
        absent = absent==null ? ()->null : absent;
        return policy.apply(input, operator, absent);
    }

    public <T> void consume(NullPolicy policy,
                            T input,
                            Consumer<T> consumer) {
        policy.apply(input, i -> { consumer.accept(i); return null; }, ()->null);
    }

    /**
     * Converts a value + {@link NullPolicy} into an {@link Optional}.
     * <p>
     *     Useful to expose a more modern, fluent API to the caller.
     * </p>
     * @param policy Policy to apply.
     * @param input Input value.
     * @param <T> Type of the value.
     * @return An {@code Optional} containing the value according to the policy.
     */
    public <T> Optional<T> toOptional(NullPolicy policy,
                                      T input) {
        Objects.requireNonNull(policy, "policy");
        return from(policy,input,Optional::of,Optional::empty);
    }

    /**
     * Applies a {@link NullPolicy} on top of an {@link Optional}.
     * <p>
     *     This allows to take an {@code Optional} and still apply strict/nullable/null-aware semantics if needed.
     * </p>
     * @param policy Policy to apply.
     * @param optional Optional to unwrap.
     * @param present Function to apply if a value is present.
     * @param absent Fallback supplier.
     * @param <T> Type of the value.
     * @param <R> Type of the result.
     * @return Result after applying policy.
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public <T, R> R fromOptional(NullPolicy policy,
                                 Optional<T> optional,
                                 Function<T,R> present,
                                 Supplier<R> absent) {
        Objects.requireNonNull(policy, "policy");
        Objects.requireNonNull(optional, "optional");
        Objects.requireNonNull(present, "present");
        Objects.requireNonNull(absent, "absent");
        return policy.apply(optional.orElse(null), present, absent);
    }

    /**
     * Maps the value inside an {@link Optional} using a {@link NullPolicy}.
     * @param policy Policy to apply.
     * @param optional Optional to unwrap.
     * @param transformation Transformation.
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public <T, R> Optional<R> mapOptional(NullPolicy policy,
                                          Optional<T> optional,
                                          Function<T, R> transformation) {
        Objects.requireNonNull(policy, "policy");
        Objects.requireNonNull(optional, "optional");
        Objects.requireNonNull(transformation, "transformation");
        return fromOptional(policy, optional, t -> Optional.ofNullable(transformation.apply(t)), Optional::empty);
    }

    /**
     * Consumes the value inside an {@link Optional} according to a {@link NullPolicy}.
     * <p>
     *     If the value is considered "present" according to the policy, the consumer is called.
     *     Otherwise, nothing happens (no exception, no action).
     * </p>
     * <p>
     *     This is the {@code Optional} equivalent of {@link #consume(NullPolicy, Object, Consumer)}.
     * </p>
     * @param policy Policy to apply.
     * @param optional Optional to consume.
     * @param consumer Action to perform if a value is considered present.
     * @param <T> Type of the value.
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public <T> void consumeOptional(NullPolicy policy,
                                    Optional<T> optional,
                                    Consumer<? super T> consumer) {
        Objects.requireNonNull(policy, "policy");
        Objects.requireNonNull(optional, "optional");
        Objects.requireNonNull(consumer, "consumer");
        policy.apply(optional.orElse(null), t -> { consumer.accept(t); return null; }, () -> null);
    }
}
