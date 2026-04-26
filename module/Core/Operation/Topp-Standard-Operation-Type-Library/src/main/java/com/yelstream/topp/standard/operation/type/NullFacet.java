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

import java.util.function.Supplier;

/**
 * Null-handling facet for a subject.
 * <p>
 *     Provides inspection and fallback operations for nullable values,
 *     hence a single-subject recovery model.
 * </p>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class NullFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Indicates whether the subject value is null.
     * @return True if the subject value is null.
     */
    public boolean isNull() {
        return subject.getValue() == null;
    }

    /**
     * Indicates whether the subject value is non-null.
     * @return True if the subject value is non-null.
     */
    public boolean isNotNull() {
        return subject.getValue() != null;
    }

    /**
     * Returns the subject unchanged if non-null.
     * Otherwise, returns a subject with fallback value.
     * @param fallback Fallback value.
     * @return Subject with resolved value.
     */
    public Subject<T> or(T fallback) {
        return Subjects.or(subject, fallback);
    }

    /**
     * Returns the subject unchanged if non-null.
     * Otherwise, returns the fallback subject.
     * @param other Fallback subject.
     * @return Subject with resolved value.
     */
    public Subject<T> or(Subject<T> other) {
        return isNotNull() ? subject : other;
    }

    /**
     * Returns the subject unchanged if non-null.
     * Otherwise, returns a subject with supplied fallback.
     * @param supplier Supplier of fallback value.
     * @return Subject with resolved value.
     */
    public Subject<T> orGet(Supplier<T> supplier) {
        return isNotNull() ? subject : subject.withValue(supplier.get());
    }

    /**
     * Returns the subject unchanged if non-null.
     * Otherwise, returns a subject with the first non-null value.
     * @param values Fallback values.
     * @return Subject with resolved value.
     */
    public Subject<T> orAny(Iterable<T> values) {
        if (isNotNull()) {
            return subject;
        }
        if (values != null) {
            for (T value : values) {
                if (value != null) {
                    return subject.withValue(value);
                }
            }
        }
        return subject;
    }
}
