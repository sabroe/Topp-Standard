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
public interface LoggingEventBuilderExAlias<S extends LoggingEventBuilderExAlias<S>> extends LoggingEventBuilderEx<S>{
    default S cause(Throwable throwable) {
        return setCause(throwable);
    }

    default S mark(Marker marker) {
        return addMarker(marker);
    }

    default S arg(Object o) {
        return addArgument(o);
    }

    default S arg(Supplier<?> supplier) {
        return addArgument(supplier);
    }

    default S key(String s, Object o) {
        return addKeyValue(s,o);
    }

    default S key(String s, Supplier<Object> supplier) {
        return addKeyValue(s,supplier);
    }

    default S message(String s) {
        return setMessage(s);
    }

    default S message(Supplier<String> supplier) {
        return setMessage(supplier);
    }
}
