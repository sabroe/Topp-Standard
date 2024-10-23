package com.yelstream.topp.standard.lang;

import java.util.function.Consumer;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-10-23
 */
public interface FluentConsumer<S, T> extends Consumer<T> {
    S accept2(T t);
}