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

package com.yelstream.topp.standard.util.function;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@UtilityClass
public class SupplierWithExceptions {
    public static <T,E extends Exception> Stream<T> resolve(Stream<SupplierWithException<T,E>> elementStream,
                                                            Function<Exception,E> errorHandler) throws E {
        return elementStream
            .flatMap(supplier -> {
                try {
                    return Stream.of(supplier.get());
                } catch (Exception ex) {
                    E exception=errorHandler.apply(ex);
                    try {
                        throw exception;
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
            });
    }

    public static <T,E extends Exception> List<T> resolve(List<SupplierWithException<T,E>> elements,
                                                          Function<Exception,E> errorHandler) throws E {
        return resolve(elements.stream(),errorHandler).toList();
    }

    public static <T,E extends Exception> List<T> resolveDistinct(List<SupplierWithException<T,E>> elements,
                                                                  Function<Exception,E> errorHandler) throws E {
        return resolve(elements.stream(),errorHandler).distinct().toList();
    }

    public static <T> List<T> distinct(List<T> elements) {
        return elements.stream().distinct().toList();
    }

    public static <T,E extends Exception> List<SupplierWithException<T,E>> fromList(List<T> elements) {
        return elements.stream().map(element->(SupplierWithException<T,E>)()->element).toList();
    }

    public static <T,E extends Exception> List<SupplierWithException<T,E>> distinct(List<SupplierWithException<T,E>> elements,
                                                                                    Function<Exception,E> errorHandler) throws E {
        return resolve(elements.stream(),errorHandler).distinct().map(element->(SupplierWithException<T,E>)()->element).toList();
    }
}
