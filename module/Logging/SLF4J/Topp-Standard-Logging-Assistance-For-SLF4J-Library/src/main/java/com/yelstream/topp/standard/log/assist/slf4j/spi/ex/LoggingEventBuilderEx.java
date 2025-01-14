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

package com.yelstream.topp.standard.log.assist.slf4j.spi.ex;

import org.slf4j.Marker;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.Supplier;

/**
 * Extended version of {@link org.slf4j.spi.LoggingEventBuilder}.
 * <p>
 *   The operations are mapped one-to-one against the signature of native SLF4J {@code LoggingEventBuilder} (currently version 2.0.X).
 *   Applied is a usage of the "self-referential generics pattern" for the return types.
 * </p>
 * <p>
 *   The extended version of the native interface is open to refer to its own type in a type-safe way,
 *   enabling fluent method chaining and other similar constructs.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-19
 *
 * @param <S> Self-referential type.
 */
public interface LoggingEventBuilderEx<S extends LoggingEventBuilderEx<S>> {
    /**
     * Sets a cause.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#setCause(Throwable)}.
     * </p>
     * @param cause Cause.
     * @return Self.
     */
    S setCause(Throwable cause);

    /**
     * Adds a marker.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#addMarker(Marker)}.
     * </p>
     * @param marker Marker.
     * @return Self.
     */
    S addMarker(Marker marker);

    /**
     * Adds an argument.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#addArgument(Object)}.
     * </p>
     * @param argument Argument.
     * @return Self.
     */
    S addArgument(Object argument);

    /**
     * Adds an argument.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#addArgument(Supplier)}.
     * </p>
     * @param argumentSupplier Supplier of argument
     * @return Self.
     */
    S addArgument(Supplier<?> argumentSupplier);

    /**
     * Adds a (key,value) pair.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#addKeyValue(String, Object)}.
     * </p>
     * @param key Key.
     * @param value Value.
     * @return Self.
     */
    S addKeyValue(String key,
                  Object value);

    /**
     * Adds a (key,value) pair.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#addKeyValue(String, Supplier)}.
     * </p>
     * @param key Key.
     * @param valueSupplier Supplier of value.
     * @return Self.
     */
    S addKeyValue(String key,
                  Supplier<Object> valueSupplier);

    /**
     * Sets the message.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#setMessage(String)}.
     * </p>
     * @param message Message.
     * @return Self.
     */
    S setMessage(String message);

    /**
     * Sets the message.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#setMessage(Supplier)}.
     * </p>
     * @param messageSupplier Supplier of message.
     * @return Self.
     */
    S setMessage(Supplier<String> messageSupplier);

    /**
     * Finalizes the logging.
     * <p>
     *   See {@link LoggingEventBuilder#log()}.
     * </p>
     */
    void log();

    /**
     * Finalizes the logging.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#log(String)}.
     * </p>
     * @param message Message.
     */
    void log(String message);

    /**
     * Finalizes the logging.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#log(String, Object)}.
     * </p>
     * @param message Message.
     * @param argument Argument.
     */
    void log(String message,
             Object argument);

    /**
     * Finalizes the logging.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#log(String,Object,Object)}.
     * </p>
     * @param message Message.
     * @param argument1 First argument.
     * @param argument2 Second argument.
     */
    void log(String message,
             Object argument1,
             Object argument2);

    /**
     * Finalizes the logging.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#log(String,Object...)}.
     * </p>
     * @param message Message.
     * @param arguments Arguments.
     */
    void log(String message,
             Object... arguments);

    /**
     * Finalizes the logging.
     * <p>
     *   See {@link org.slf4j.spi.LoggingEventBuilder#log(Supplier)}.
     * </p>
     * @param messageSupplier Supplier of message.
     */
    void log(Supplier<String> messageSupplier);
}
