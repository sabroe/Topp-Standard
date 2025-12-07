/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.util.function.ex;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Utility class for handling collections of {@link SupplierWithException} instances.
 * Provides methods to resolve, distinct, and transform streams and lists of suppliers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-11
 */
@UtilityClass
public class SupplierWithExceptions {
    /**
     * Resolves a stream of suppliers to a stream of results, handling exceptions with the provided error handler.
     * @param elementSupplierStream Stream of suppliers of elements.
     * @param errorHandler Function to convert exceptions.
     * @param <T> Type of the element.
     * @param <E> Type of the exception.
     * @return Resolved results.
     * @throws E Thrown in case an exception occurs.
     */
    public static <T,E extends Exception> Stream<T> resolve(Stream<SupplierWithException<T,E>> elementSupplierStream,
                                                            Function<Exception,E> errorHandler) throws E {
        return elementSupplierStream.flatMap(elementSupplier -> {
            try {
                return Stream.of(elementSupplier.get());
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

    /**
     * Resolves a list of suppliers to a list of results, handling exceptions with the provided error handler.
     * @param elementSuppliers Suppliers of elements.
     * @param errorHandler Function to convert exceptions.
     * @param <T> Type of the element.
     * @param <E> Type of the exception.
     * @return Resolved results.
     * @throws E Thrown in case an exception occurs.
     */
    public static <T,E extends Exception> List<T> resolve(List<SupplierWithException<T,E>> elementSuppliers,
                                                          Function<Exception,E> errorHandler) throws E {
        return resolve(elementSuppliers.stream(),errorHandler).toList();
    }

    /**
     * Resolves a list of suppliers to a distinct list of results, handling exceptions with the provided error handler.
     * @param elementSuppliers Suppliers of elements.
     * @param errorHandler Function to convert exceptions.
     * @param <T> Type of the element.
     * @param <E> Type of exception.
     * @return Resolved results.
     * @throws E Thrown in case an exception occurs.
     */
    public static <T,E extends Exception> List<T> resolveDistinct(List<SupplierWithException<T,E>> elementSuppliers,
                                                                  Function<Exception,E> errorHandler) throws E {
        return resolve(elementSuppliers.stream(),errorHandler).distinct().toList();
    }

    /**
     * Returns the distinct elements.
     * @param elements Elements.
     * @param <T> Type of the element.
     * @return Distinct elements.
     */
    public static <T> List<T> distinct(List<T> elements) {
        return elements.stream().distinct().toList();
    }

    /**
     * Converts a list of elements to a list of suppliers that return these elements.
     * @param elements Elements.
     * @param <T> Type of the element.
     * @param <E> Type of exception.
     * @return List of suppliers of elements.
     */
    public static <T,E extends Exception> List<SupplierWithException<T,E>> fromList(List<T> elements) {
        return elements.stream().map(element->(SupplierWithException<T,E>)()->element).toList();
    }

    /**
     * Returns a distinct list of suppliers, handling exceptions with the provided error handler.
     * @param elementSuppliers Suppliers of elements.
     * @param errorHandler Function to convert exceptions.
     * @param <T> Type of the element.
     * @param <E> Type of exception.
     * @return List of suppliers all providing distinct elements.
     * @throws E Thrown in case an exception occurs.
     */
    public static <T,E extends Exception> List<SupplierWithException<T,E>> distinct(List<SupplierWithException<T,E>> elementSuppliers,
                                                                                    Function<Exception,E> errorHandler) throws E {
        return resolve(elementSuppliers.stream(),errorHandler).distinct().map(element->(SupplierWithException<T,E>)()->element).toList();
    }
}
