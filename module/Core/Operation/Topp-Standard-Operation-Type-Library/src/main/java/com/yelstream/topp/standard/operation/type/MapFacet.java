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
 *
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class MapFacet<T> {
    @NonNull
    public final Subject<T> subject;

    public <R> Subject<R> map(Function<T, R> mapper) {
//        return Subjects.mapByValue(subject,mapper);
        return null;  //TO-DO: Fix!
    }

    public <R> Subject<R> flatMap(Function<T, Subject<R>> mapper) {
        return null;  //TO-DO: Fix!
    }

    public <R> Subject<R> to(Function<T, R> mapper) {
        return Subjects.to(subject,mapper);
    }
}
