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
 * Logging event creation using aliases with an aggressively shorter naming schema.
 * <p>
 *     All additional methods address syntax only.
 *     The applied style of method naming is most aggressive by using single character names only.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-10
 *
 * @param <S> Self-referential type.
 */
public interface LoggingEventBuilderExAliasAggressive<S extends LoggingEventBuilderExAliasAggressive<S>> extends LoggingEventBuilderEx<S>{
    default S c(Throwable throwable) {
        return setCause(throwable);
    }

    default S x(Marker marker) {
        return addMarker(marker);
    }

    default S a(Object o) {
        return addArgument(o);
    }

    default S a(Supplier<?> supplier) {
        return addArgument(supplier);
    }

    default S k(String s, Object o) {
        return addKeyValue(s,o);
    }

    default S k(String s, Supplier<Object> supplier) {
        return addKeyValue(s,supplier);
    }

    default S m(String s) {
        return setMessage(s);
    }

    default S m(Supplier<String> supplier) {
        return setMessage(supplier);
    }

    default void l() {
        log();
    }
}
