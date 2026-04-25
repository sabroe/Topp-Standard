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
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility addressing instances of {@link Subject}.
 * <p>
 *     This is a universal semantic utility layer over {@code Subject<T>}.
 *     Meaning it is allowed to:
 * </p>
 * <ul>
 *     <li>Support fluent operations.</li>
 *     <li>Suppoert non-fluent operationbs.</li>
 *     <li>Support {@link Optional}.</li>
 *     <li>Support null-tolerant APIs.</li>
 *     <li>Support strict APIs.</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@UtilityClass
public class Subjects {

    public static <T> Subject<T> empty() {
        return Subject.of(null);
    }

    public static <T, R> Optional<Subject<R>> tryCast(Subject<T> subject,
                                                      Class<R> type) {
        Objects.requireNonNull(subject, "subject");
        return ObjectOps.instanceOf(subject.getValue(), type).map(subject::replaceValue);
    }

    /**
     *
     * This is on the "type interpretation axis".
     * @param subject
     * @param type
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T, R> Subject<R> as(Subject<T> subject,
                                       Class<R> type) {
        Objects.requireNonNull(subject, "subject");
        return tryCast(subject, type).orElseGet(Subjects::empty);  //Type-resolution policy is 'or else empty'!
    }

    public <T, R> Optional<Subject<R>> mapByValue(Subject<T> subject,
                                                  Function<T, R> mapper) {
        return Optional.ofNullable(subject).map(s -> s.mapValue(mapper));
    }

    /**
     * Unconditional replacement.
     * This is an active, deterministic transformation.
     * This is on the "transformation axis".
     * @param subject
     * @param mapper
     * @return
     * @param <T>
     * @param <R>
     */
    public <T, R> Subject<R> to(Subject<T> subject,
                                Function<T, R> mapper) {
        Objects.requireNonNull(subject, "subject");
        return subject.mapValue(mapper);
    }

    public <T> Optional<Subject<T>> filterByValue(Subject<T> subject,
                                                  Predicate<T> predicate) {
        return Optional.ofNullable(subject).filter(s -> predicate.test(s.getValue()));
    }

    /**
     * Conditional preservation or repair mechanism.
     * This is a fallback correction.
     * This is on the "state completion axis".
     * @param subject
     * @param value
     * @return
     * @param <T>
     */
    public <T> Subject<T> or(Subject<T> subject,
                             T value) {
        Objects.requireNonNull(subject, "subject");
        return subject.getValue() != null ? subject : subject.withValue(value);  //State-preservation policy.
    }
}
