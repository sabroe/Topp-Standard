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

package com.yelstream.topp.standard.empress.xml.bind;

import jakarta.xml.bind.JAXBElement;
import lombok.experimental.UtilityClass;

import javax.xml.namespace.QName;

/**
 * Utility addressing instances of {@link JAXBElementFactory}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-19
 */
@UtilityClass
public class JAXBElementFactories {
    /**
     * XXX
     * @param name
     * @param declaredType
     * @param scope
     * @return
,     */
    @lombok.Builder(builderClassName="Builder")
    private static <T> JAXBElementFactory<T> createJAXBElementFactory(QName name,
                                                                      Class<T> declaredType,
                                                                      Class<?> scope) {
        return value -> JAXBElements.createJAXBElement(name,declaredType,scope,value);
    }

    /*
    public static class Builder<T> {
        private QName name;
        private Class<T> declaredType;
        private Class<?> scope;
    }
    S
     */
/*
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }


 */
    /*
    public static <T> Builder<T> builder(JAXBElement<T> element) {
        Builder<T> builder=builder();
//        return builder.name(element.getName()).declaredType(element.getDeclaredType()).scope(element.getScope());
        builder=builder.name(element.getName());

Class<T> declaredType= element.getDeclaredType();
        builder=builder.declaredType(declaredType);
        builder=builder.scope(element.getScope());
        return builder;
    }

     */
}
