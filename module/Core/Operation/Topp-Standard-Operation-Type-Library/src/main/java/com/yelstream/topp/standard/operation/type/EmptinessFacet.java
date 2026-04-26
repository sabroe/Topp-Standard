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

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Semantic emptiness evaluation for a subject.
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class EmptinessFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Default emptiness: null check.
     */
    public boolean isEmpty() {
        return subject.getValue() == null;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Custom emptiness rule.
     */
    public boolean isEmpty(Predicate<T> rule) {
        Objects.requireNonNull(rule, "rule");
        return rule.test(subject.getValue());
    }

    /**
     * Collection-aware emptiness helper.
     */
    public boolean isEmptyCollection() {
        T value = subject.getValue();
        return value instanceof Collection<?> c && c.isEmpty();
    }
}
