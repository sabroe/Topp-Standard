package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
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
}
