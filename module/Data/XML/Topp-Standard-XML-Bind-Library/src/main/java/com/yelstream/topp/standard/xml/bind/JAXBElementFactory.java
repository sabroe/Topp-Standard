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

package com.yelstream.topp.standard.xml.bind;

import jakarta.xml.bind.JAXBElement;

/**
 * Factory of {@link JAXBElement} instances.
 * @param <T> Type of value of created elements.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-19
 */
@FunctionalInterface
public interface JAXBElementFactory<T> {
    /**
     * Creates a new element.
     * @param value Value of element.
     *              This may be {@code null}.
     * @return Created element.
     */
    JAXBElement<T> create(T value);

    /**
     * Creates a new element.
     * @param value Value of element.
     *              This may be {@code null}.
     * @param nil Indicates, if element attribute 'nil' is set.
     * @return Created element.
     */
    default JAXBElement<T> create(T value,
                                  boolean nil) {
        JAXBElement<T> element=create(value);
        element.setNil(nil);
        return element;
    }
}
