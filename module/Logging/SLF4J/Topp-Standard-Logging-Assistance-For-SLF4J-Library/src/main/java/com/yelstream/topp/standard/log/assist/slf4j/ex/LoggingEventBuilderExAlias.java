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

package com.yelstream.topp.standard.log.assist.slf4j.ex;

import org.slf4j.Marker;

import java.util.function.Supplier;

/**
 * Logging event creation using aliases with a moderately more fluent naming schema.
 * <p>
 *     All additional methods address syntax only.
 *     The applied style of method naming is slightly shorter and more fluent by leaving out
 *     the {@code set} and {@code get} prefixes.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-19
 *
 * @param <S> Self-referential type.
 */
public interface LoggingEventBuilderExAlias<S extends LoggingEventBuilderExAlias<S>> extends LoggingEventBuilderEx<S>{
    /**
     * Sets a cause.
     * <p>
     *   See {@link LoggingEventBuilderEx#setCause(Throwable)}.
     * </p>
     * @param cause Cause.
     * @return Self.
     */
    default S cause(Throwable cause) {
        return setCause(cause);
    }

    /**
     * Adds a marker.
     * <p>
     *   See {@link LoggingEventBuilderEx#addMarker(Marker)}.
     * </p>
     * @param marker Marker.
     * @return Self.
     */
    default S mark(Marker marker) {
        return addMarker(marker);
    }

    /**
     * Adds an argument.
     * <p>
     *   See {@link LoggingEventBuilderEx#addArgument(Object)}.
     * </p>
     * @param argument Argument.
     * @return Self.
     */
    default S arg(Object argument) {
        return addArgument(argument);
    }

    /**
     * Adds an argument.
     * <p>
     *   See {@link LoggingEventBuilderEx#addArgument(Supplier)}.
     * </p>
     * @param argumentSupplier Supplier of argument
     * @return Self.
     */
    default S arg(Supplier<?> argumentSupplier) {
        return addArgument(argumentSupplier);
    }

    /**
     * Adds a (key,value) pair.
     * <p>
     *   See {@link LoggingEventBuilderEx#addKeyValue(String, Object)}.
     * </p>
     * @param key Key.
     * @param value Value.
     * @return Self.
     */
    default S key(String key, Object value) {
        return addKeyValue(key,value);
    }

    /**
     * Adds a (key,value) pair.
     * <p>
     *   See {@link LoggingEventBuilderEx#addKeyValue(String, Supplier)}.
     * </p>
     * @param key Key.
     * @param valueSupplier Supplier of value.
     * @return Self.
     */
    default S key(String key, Supplier<Object> valueSupplier) {
        return addKeyValue(key,valueSupplier);
    }

    /**
     * Sets the message.
     * <p>
     *   See {@link LoggingEventBuilderEx#setMessage(String)}.
     * </p>
     * @param message Message.
     * @return Self.
     */
    default S message(String message) {
        return setMessage(message);
    }

    /**
     * Sets the message.
     * <p>
     *   See {@link LoggingEventBuilderEx#setMessage(Supplier)}.
     * </p>
     * @param messageSupplier Supplier of message.
     * @return Self.
     */
    default S message(Supplier<String> messageSupplier) {
        return setMessage(messageSupplier);
    }
}
