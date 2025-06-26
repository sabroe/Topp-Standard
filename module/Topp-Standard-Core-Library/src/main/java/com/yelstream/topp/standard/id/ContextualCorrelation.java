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

import java.util.function.BiFunction;

/**
 * Contextual aware correlation between two domains.
 * <p>
 *     See {@link Correlation}.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 * @param <C> Type of context.
 * @param <X> Type of domain values.
 * @param <Y> Type of codomain values.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-06-25
 */
@AllArgsConstructor(staticName="of")
public final class ContextualCorrelation<C,X,Y> {
    /**
     * Context used by mappings.
     */
    public final C context;

    /**
     * Mapping from domain values to codomain values, with access to the context.
     */
    public final BiFunction<C,X,Y> domainToCodomainMapping;

    /**
     * Mapping from codomain values to domain values, with access to the context.
     */
    public final BiFunction<C,Y,X> codomainToDomainMapping;

    /**
     * Applies the mapping of a domain value to a codomain value.
     * @param source Domain value.
     * @return Codomain value.
     */
    public Y apply(X source) {
        return domainToCodomainMapping.apply(context,source);
    }

    /**
     * Applies the mapping of a codomain value to a domain value.
     * @param target Codomain value.
     * @return Domain value.
     */
    public X revert(Y target) {
        return codomainToDomainMapping.apply(context,target);
    }

    /**
     * Creates a correlation, no context, hooking directly into this contextual-aware correlation.
     * @return Created correlation.
     */
    public Correlation<X,Y> toCorrelation() {
        return Correlation.of(this::apply,this::revert);
    }
}
