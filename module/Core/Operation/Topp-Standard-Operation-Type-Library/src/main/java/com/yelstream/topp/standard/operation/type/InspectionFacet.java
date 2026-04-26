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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Inspection facet for a subject.
 * <p>
 *     Provides predicate-based operations on the subject value.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class InspectionFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Evaluates a predicate against the subject value.
     * @param predicate Predicate evaluated.
     * @return True if the predicate matches.
     */
    public boolean matches(Predicate<T> predicate) {
        return predicate.test(subject.getValue());
    }

    /**
     * Executes a consumer if a predicate matches.
     * @param predicate Predicate evaluated.
     * @param consumer Consumer invoked.
     */
    public void ifMatches(Predicate<T> predicate,
                          Consumer<T> consumer) {
        if (matches(predicate)) {
            consumer.accept(subject.getValue());
        }
    }

    /**
     * Returns the subject if a predicate matches.
     * Otherwise, returns an empty subject.
     * @param predicate Predicate evaluated.
     * @return Matching subject or empty subject.
     */
    public Subject<T> filter(Predicate<T> predicate) {
        return matches(predicate) ? subject : Subjects.empty();
    }
}
