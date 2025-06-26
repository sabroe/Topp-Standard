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
 *     It is intended to map between two types of correlation ids,
 *     using basic formatting/parsing to map between the two.
 *     The mapping may be from a domain whose values are private and should not be exposed and used for communication,
 *     to a codomain whose values are neutral and anonymous.
 *     The mapping may be a transformation between syntax and wrapping.
 * </p>
 * <p>
 *     Whether the correlation is thought upon as
 *     source-target, input-output, domain-codomain, left-right, primary-secondary, inner-outer,
 *     or something else, is usage specific.
 * </p>
 * <p>
 *     This may or may not require a reference to a context and per each mapping.
 * </p>
 * <p>
 *     The mapping and its cardinality may or may not be one-to-one.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 * @param <X> Type of domain values.
 * @param <Y> Type of codomain values.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-06-25
 */
@AllArgsConstructor(staticName="of")
public final class Correlation<X,Y> {
    /**
     * Mapping from domain values to codomain values.
     */
    public final Function<X,Y> domainToCodomainMapping;

    /**
     * Mapping from codomain values to domain values.
     */
    public final Function<Y,X> codomainToDomainMapping;

    /**
     * Applies the mapping of a domain value to a codomain value.
     * @param source Domain value.
     * @return Codomain value.
     */
    public Y apply(X source) {
        return domainToCodomainMapping.apply(source);
    }

    /**
     * Applies the mapping of a codomain value to a domain value.
     * @param target Codomain value.
     * @return Domain value.
     */
    public X revert(Y target) {
        return codomainToDomainMapping.apply(target);
    }

    /**
     * Composes this correlation with some other correlation.
     * <p>
     *     This is chaining, X <-> Y <-> Z.
     * </p>
     * @param other Other correlation.
     * @return Resulting correlation.
     * @param <Z> Type of values in the codomain of the other correlation and hence in the resulting correlation.
     */
    public <Z> Correlation<X,Z> compose(Correlation<Y,Z> other) {
        return Correlation.of(
            domainToCodomainMapping.andThen(other.domainToCodomainMapping),
            other.codomainToDomainMapping.andThen(codomainToDomainMapping)
        );
    }

    /*
     * TO-DO: Consider mapping the cardinality!
     *     public enum MappingType {
     *         ONE_TO_ONE, MANY_TO_ONE, ONE_TO_MANY, MANY_TO_MANY
     *     }
     *
     *     public boolean isOneToOne() {
     *         return mappingType == MappingType.ONE_TO_ONE;
     *     }
     */

    /*
     * TO-DO: Add basic formatting/parsing example to clas JavaDoc.
     */

    /*
     * TO-DO: Add chaining example, A <-> B <-> C.
     */

    /*
     * TO-DO: For a lookup-based example, using a backing store, possibly using Guava's BiMap.
     */
}
