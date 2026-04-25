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
 *
 * <p>
 *     This is basically a Optional-like read-only / action-oriented facet.
 *     It overlaps NullFacet, but with a different style:
 * </p>
 * <ul>
 *     <li>NullFacet → returns Subject</li>
 *     <li>PresenceFacet → acts like Optional</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class PresenceFacet<T> {

    @NonNull
    public final Subject<T> subject;

    /**
     * Indicates whether the subject value is present.
     */
    public boolean isPresent() {
        return subject.getValue() != null;
    }

    /**
     * Indicates whether the subject value is absent.
     */
    public boolean isEmpty() {
        return subject.getValue() == null;
    }

    /**
     * Executes consumer if value is present.
     */
    public void ifPresent(Consumer<T> consumer) {
        if (isPresent()) {
            consumer.accept(subject.getValue());
        }
    }

    /**
     * Executes action if value is absent.
     */
    public void ifEmpty(Runnable action) {
        if (isEmpty()) {
            action.run();
        }
    }

    /**
     * Returns value if present; otherwise fallback.
     */
    public T orElse(T fallback) {
        return isPresent() ? subject.getValue() : fallback;
    }
}
