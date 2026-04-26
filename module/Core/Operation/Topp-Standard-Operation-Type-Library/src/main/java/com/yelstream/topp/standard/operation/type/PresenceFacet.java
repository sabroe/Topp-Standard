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

/**
 * Presence facet for a subject.
 * <p>
 *     Provides presence-based operations on the subject value.
 * </p>
 * <p>
 *     This is a read-only, action-oriented facet similar to {@link java.util.Optional}.
 *     It overlaps {@link NullFacet}, but with a different style:
 * </p>
 * <ul>
 *     <li>{@link NullFacet} returns {@link Subject} instances.</li>
 *     <li>{@link PresenceFacet} provides inspection and action-oriented methods.</li>
 * </ul>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class PresenceFacet<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    /**
     * Indicates whether the subject value is present.
     * @return True if the subject value is non-null.
     */
    public boolean isPresent() {
        return subject.getValue() != null;
    }

    /**
     * Indicates whether the subject value is absent.
     * @return True if the subject value is null.
     */
    public boolean isEmpty() {
        return subject.getValue() == null;
    }

    /**
     * Executes a consumer if the subject value is present.
     * @param consumer Consumer invoked.
     */
    public void ifPresent(Consumer<T> consumer) {
        if (isPresent()) {
            consumer.accept(subject.getValue());
        }
    }

    /**
     * Executes an action if the subject value is absent.
     * @param action Action invoked.
     */
    public void ifEmpty(Runnable action) {
        if (isEmpty()) {
            action.run();
        }
    }

    /**
     * Returns the subject value if present.
     * Otherwise, returns a fallback value.
     * @param fallback Fallback value.
     * @return Subject value or fallback.
     */
    public T orElse(T fallback) {
        return isPresent() ? subject.getValue() : fallback;
    }
}
