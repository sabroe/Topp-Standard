/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-19
 */
public interface LoggingEventBuilderEx<S extends LoggingEventBuilderEx<S>> {
    S setCause(Throwable throwable);

    S addMarker(Marker marker);

    S addArgument(Object o);

    S addArgument(Supplier<?> supplier);

    S addKeyValue(String s, Object o);

    S addKeyValue(String s, Supplier<Object> supplier);

    S setMessage(String s);

    S setMessage(Supplier<String> supplier);

    void log();

    void log(String s);

    void log(String s, Object o);

    void log(String s, Object o, Object o1);

    void log(String s, Object... objects);

    void log(Supplier<String> supplier);
}
