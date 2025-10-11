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

package com.yelstream.topp.standard.util.concurrent;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Callable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-10-11
 */
@UtilityClass
public class Callables {

    public static <T> T requireNonException(Callable<T> callable,
                                            Supplier<String> messageSupplier) throws IllegalStateException {
        try {
            return callable.call();
        } catch (Exception ex) {
            throw new IllegalStateException(messageSupplier.get(),ex);
        }
    }

    public static <T> T requireNonException(Callable<T> callable,
                                            String message) throws IllegalStateException {
        Supplier<String> messageSupplier=()->message;
        return requireNonException(callable,messageSupplier);
    }

    public static <T> T requireNonException(Callable<T> callable) throws IllegalStateException {
        return requireNonException(callable,"Failure to verify non-exception!");
    }
}
