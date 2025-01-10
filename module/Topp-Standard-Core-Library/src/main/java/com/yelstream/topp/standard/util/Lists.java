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

package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Utility addressing instances of {@link List}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-24
 */
@UtilityClass
public class Lists {
    /**
     * Creates a list from an {@link Iterable} instance.
     * <p>
     *     Note that the create list is always a copy, it is not immutable, and it may contain {@code null} elements.
     * </p>
     * @param iterable Iterable.
     * @return Created list.
     * @param <T> Type of elements.
     */
    public static <T> List<T> of(Iterable<T> iterable) {
        List<T> result=null;
        if (iterable!=null) {
            result=new ArrayList<>();
            iterable.forEach(result::add);
        }
        return result;
    }

    /**
     * Creates a list from an {@link Iterator} instance.
     * <p>
     *     Note that the create list is always a copy, it is not immutable, and it may contain {@code null} elements.
     * </p>
     * @param iterator Iterator.
     * @return Created list.
     * @param <T> Type of elements.
     */
    public static <T> List<T> of(Iterator<T> iterator) {
        List<T> result=null;
        if (iterator!=null) {
            result=new ArrayList<>();
            iterator.forEachRemaining(result::add);
        }
        return result;
    }

    /**
     * Creates a list from a {@link Spliterator} instance.
     * <p>
     *     Note that the create list is always a copy, it is not immutable, and it may contain {@code null} elements.
     * </p>
     * @param spliterator Spliterator.
     * @return Created list.
     * @param <T> Type of elements.
     */
    public static <T> List<T> of(Spliterator<T> spliterator) {
        List<T> result=null;
        if (spliterator!=null) {
            result=StreamSupport.stream(spliterator,false).collect(Collectors.toCollection(ArrayList::new));
        }
        return result;
    }

    /**
     * Creates a list of elements.
     * <p>
     *     Note that the create list is always a copy, it is not immutable, and it may contain {@code null} elements.
     *     The last contrary to {@link List#of(Object[])}.
     * </p>
     * @param elements Elements.
     * @return Created list.
     * @param <T> Type of elements.
     */
    @SafeVarargs
    public static <T> List<T> of(T... elements) {
        return ofArray(elements);
    }

    /**
     * Creates a list of elements.
     * <p>
     *     Note that the create list is always a copy, it is not immutable, and it may contain {@code null} elements.
     *     The last contrary to {@link List#of(Object[])}.
     * </p>
     * @param elements Elements.
     * @return Created list.
     * @param <T> Type of elements.
     */
    public static <T> List<T> ofArray(T[] elements) {
        return elements==null?null:new ArrayList<>(Arrays.asList(elements));
    }

    /**
     * Indicates, if a list is empty.
     * @param list List to test.
     *             This may be {@code null}.
     * @return Indicates, if list is empty.
     * @param <T> Type of elements.
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list==null || list.isEmpty();
    }

    /**
     * Indicates, if a list is non-empty.
     * @param list List to test.
     *             This may be {@code null}.
     * @return Indicates, if list is non-empty.
     * @param <T> Type of elements.
     */
    public static <T> boolean isNonEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * Gets the first element of a list.
     * @param list List.
     *             This may be {@code null}.
     * @return First element
     *         This may be {@code null}.
     * @param <T> Type of elements.
     */
    public static <T> T getFirst(List<T> list) {
        return isEmpty(list)?null:list.getFirst();
    }

    /**
     * Gets the last element of a list.
     * @param list List.
     *             This may be {@code null}.
     * @return Last element
     *         This may be {@code null}.
     * @param <T> Type of elements.
     */
    public static <T> T getLast(List<T> list) {
        return isEmpty(list)?null:list.getLast();
    }

    /**
     * Removes the first element of a list.
     * @param list List.
     *             This may be {@code null}.
     * @return Removed element
     *         This may be {@code null}.
     * @param <T> Type of elements.
     */
    public static <T> T removeFirst(List<T> list) {
        return isEmpty(list)?null:list.removeFirst();
    }

    /**
     * Removes the last element of a list.
     * @param list List.
     *             This may be {@code null}.
     * @return Last element
     *         This may be {@code null}.
     * @param <T> Type of elements.
     */
    public static <T> T removeLast(List<T> list) {
        return isEmpty(list)?null:list.removeLast();
    }

    public static <T> List<T> filter(List<T> list,
                                     Predicate<T> predicate) {
        return list==null?null:list.stream().filter(predicate).toList();
    }

    public static <T> List<T> nonNull(List<T> list) {
        return filter(list,Objects::nonNull);
    }
}
