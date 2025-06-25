/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.id;

import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * Correlation between two domains.
 * <p>
 *     It may be the mapping between two types of correlation ids.
 * </p>
 * <p>
 *     Weather the correlation is thought upon as
 *     "source-target", "input-output", "domain-codomain", "left-right", "primary-secondary", "inner-outer",
 *     or something else, is usage specific.
 * </p>
 * <p>
 *     This may or may not require a reference to a context and per each mapping.
 * </p>
 * <p>
 *     The mapping may or may not be one-to-one.
 * </p>
 * @param <A> Type of source format.
 * @param <B> Type of target format.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-06-25
 */
@AllArgsConstructor(staticName="of")
public final class Correlation<A,B> {
    public final Function<A,B> sourceMapping;
    public final Function<B,A> targetMapping;

    public B toTarget(A source) {
        return sourceMapping.apply(source);
    }

    public A toSource(B target) {
        return targetMapping.apply(target);
    }

    public <C> Correlation<A,C> compose(Correlation<B,C> other) {
        return Correlation.of(
            sourceMapping.andThen(other.sourceMapping),
            other.targetMapping.andThen(targetMapping)
        );
    }
}
