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
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Unmarshal operator.
 * @param <T> Class representing XML element.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@FunctionalInterface
public interface UnmarshalOperator<T> {
    /**
     * Applies unmarshalling.
     * @param unmarshaller Unmarshaller.
     * @param declaredType Binding of XML element declaration's type.
     * @return JAXB element.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    JAXBElement<T> unmarshal(Unmarshaller unmarshaller,
                             Class<T> declaredType) throws JAXBException;
}
