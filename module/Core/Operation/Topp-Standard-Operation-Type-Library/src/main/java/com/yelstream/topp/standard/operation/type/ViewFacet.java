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

import java.util.function.Function;

/**
 * Read-only derived view of a subject.
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ViewFacet<T> {  //XXX!

    @NonNull
    private final Subject<T> subject;

    /**
     * Projects value without modifying subject.
     */
    public <R> R view(Function<T, R> projection) {
        return projection.apply(subject.getValue());
    }

    /**
     * Safe peek operation.
     */
    public T peek() {
        return subject.getValue();
    }
}
