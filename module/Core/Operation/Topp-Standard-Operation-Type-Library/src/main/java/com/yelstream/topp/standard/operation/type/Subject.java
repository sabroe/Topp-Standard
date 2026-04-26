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
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 *
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-25
 */
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@EqualsAndHashCode
public class Subject<T> {
    /**
     * Object value held by this subject.
     */
    @Getter
    private final T value;

    public Subject<T> withValue(T newValue) {  //Preserved identity!
        if (Objects.equals(newValue, value)) {  //TO-DO: ObjectOps.same(newValue,value) ?
            return this;
        }
        return toBuilder().value(newValue).build();
    }

    public <R> Subject<R> mapValue(Function<T, R> mapper) {  //Creates a new typed subject!
//        R newValue = (value == null) ? null : mapper.apply(value);
        R newValue = mapper.apply(value);
        return replaceValue(newValue);
    }

    public <R> Subject<R> replaceValue(R newValue) {  //Creates a new typed subject!
        return Subject.<R>builder().value(newValue).build();
    }

    public int identityHash() {
        return ObjectOps.identityHash(value);
    }

    public boolean isInstance(Class<?> type) {
        return ObjectOps.isInstance(value,type);
    }

    public <R> void ifInstance(Class<R> type,
                               Consumer<Subject<R>> action) {
        if (ObjectOps.isInstance(value,type)) {
            action.accept(Subject.of(type.cast(value)));
        }
    }

    public <R> Optional<Subject<R>> tryCast(Class<R> type) {
        return ObjectOps.tryCast(value,type).map(Subject::of);
    }

    public <R> Subject<R> tryCastOrNull(Class<R> type) {
        return tryCast(type).orElse(null);
    }

    public <R> Subject<R> tryCastOr(Class<R> type,
                                    Subject<R> fallback) {
        return tryCast(type).orElse(fallback);
    }

    public <R> R cast(Class<R> type) {
        return ObjectOps.cast(value, type);
    }

/*
    public <R> Optional<R> map(Function<T, R> mapper) {
        return ObjectOps.map(value,mapper);
    }
*/

    public static <T> Subject<T> empty() {
        return of(null);
    }

/*
    Each facet must answer exactly ONE question:

    Facet	Question
    MapFacet	“what becomes the value?”
    TypeFacet	“what type is it?”
    NullFacet	“what if it is missing?”
    IdentityFacet	“is it the same thing?”
*/

    public PresenceFacet<T> presence() {
        return PresenceFacet.of(this);
    }

    public NullFacet<T> nulls() {
        return NullFacet.of(this);
    }

    public ValidationFacet<T> require() {
        return ValidationFacet.of(this);
    }

    public InspectionFacet<T> inspect() {
        return InspectionFacet.of(this);
    }

    public TypeFacet<T> type() {
        return TypeFacet.of(this);
    }

    public EqualityFacet<T> equality() {
        return EqualityFacet.of(this);
    }

    public IdentityFacet<T> identity() {
        return IdentityFacet.of(this);
    }

    /**
     *
     * <p>
     *     Usage:
     * </p>
     * <ul>
     *     <li>{@code subject.compare(Comparator.naturalOrder())}</li>
     *     <li>{@code subject.compare(String.CASE_INSENSITIVE_ORDER)}</li>
     * </ul>
     *
     * @param comparator
     * @return
     */
    public ComparisonFacet<T> compare(Comparator<T> comparator) {
        return ComparisonFacet.of(this, comparator);
    }

    public static <T extends Comparable<? super T>> Comparator<T> natural() {
        return Comparator.naturalOrder();
    }

    public MapFacet<T> map() {
        return MapFacet.of(this);
    }

/*
a fluent DSL syntax like:

subject
  .null().coalesce("default")
  .type().isInstance(String.class)
  .compare().gt("abc")
  .map(String::trim)

a single unified Facet gateway:
subject.dsl()
  */
}
