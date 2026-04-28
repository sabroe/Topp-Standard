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

import java.util.Comparator;

/**
 * Entry point for domain-oriented facet operations over a {@link Subject}.
 * <p>
 *     Facets represent semantic domains of operation over a subject.
 *     Operations within a facet should remain semantically coherent.
 *     Return values may continue fluent flow where meaningful.
 * </p>
 * <p>
 *     Chaining across unrelated semantic domains should is avoided.
 * </p>
 * <p>
 *     The model is based on a fixed set of domain return contracts:
 * </p>
 * <ul>
 *     <li><b>State</b> → State operations return either a {@code Subject} or a {@code boolean}</li>
 *     <li><b>Transform</b> → Transformation operations return either a {@code Subject<R>} or a projection result {@code R}</li>
 *     <li><b>Logic</b> → Logical operations return either a {@code boolean} or a {@code Subject}</li>
 *     <li><b>Relation</b> → Relational operations return either a {@code boolean} or a specialized facet type</li>
 *     <li><b>Type</b> → Type operations return either a {@code Subject<R>} or a {@code boolean}</li>
 *     <li><b>Compose</b> → Composition operations always return a {@code Subject}</li>
 * </ul>
 *
 * @param <T> Value type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-26
 */
@SuppressWarnings({"java:S101", "NonAsciiCharacters"})
@AllArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class Façade<T> {
    /**
     * Subject addressed.
     */
    @NonNull
    private final Subject<T> subject;

    // =========================================================
    // STATE DOMAIN
    // =========================================================

    public PresenceFacet<T> presence() {
        return PresenceFacet.of(subject);
    }

    public NullFacet<T> nulls() {
        return NullFacet.of(subject);
    }

    // =========================================================
    // VALIDATION / LOGIC DOMAIN
    // =========================================================

    public ValidationFacet<T> require() {
        return ValidationFacet.of(subject);
    }

    public InspectionFacet<T> inspect() {
        return InspectionFacet.of(subject);
    }

    // =========================================================
    // TYPE DOMAIN
    // =========================================================

    public TypeFacet<T> type() {
        return TypeFacet.of(subject);
    }

    public CastFacet<T> casting() {
        return CastFacet.of(subject);
    }

    // =========================================================
    // RELATIONAL DOMAIN
    // =========================================================

    public EqualityFacet<T> equality() {
        return EqualityFacet.of(subject);
    }

    public IdentityFacet<T> identity() {
        return IdentityFacet.of(subject);
    }

    public ComparisonFacet<T> compare(Comparator<T> comparator) {
        return ComparisonFacet.of(subject, comparator);
    }

    // =========================================================
    // TRANSFORMATION DOMAIN
    // =========================================================

    public MapFacet<T> map() {
        return MapFacet.of(subject);
    }
}
