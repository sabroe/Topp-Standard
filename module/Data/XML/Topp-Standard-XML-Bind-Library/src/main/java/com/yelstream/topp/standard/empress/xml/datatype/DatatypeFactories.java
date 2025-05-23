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

package com.yelstream.topp.standard.empress.xml.datatype;

import lombok.experimental.UtilityClass;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * Utilities addressing instances of {@link DatatypeFactory}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-08
 */
@UtilityClass
public class DatatypeFactories {
    /**
     * Creates a new data-type factory using the normal lookup mechanisms.
     * @return Created instance.
     * @throws IllegalStateException Thrown in case of configuration error.
     */
    public static DatatypeFactory createDataTypeFactory() {
        try {
            return DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            throw new IllegalStateException("Failure to create data-type factory; configuration error!",ex);
        }
    }

    /**
     * Creates a new data-type factory using the normal lookup mechanisms.
     * @param factoryClassName Name of class implementing {@link DatatypeFactory}.
     * @param classLoader Class-loader to load.
     *                    This may be {@code null}.
     * @return Created instance.
     * @throws IllegalStateException Thrown in case of configuration error.
     */
    public static DatatypeFactory createDataTypeFactory(String factoryClassName,
                                                        ClassLoader classLoader) {
        try {
            return DatatypeFactory.newInstance(factoryClassName,classLoader);
        } catch (DatatypeConfigurationException ex) {
            throw new IllegalStateException("Failure to create data-type factory; configuration error!",ex);
        }
    }
}
