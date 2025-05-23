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

package com.yelstream.topp.standard.xml.namespace;

import lombok.experimental.UtilityClass;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-20
 */
@UtilityClass
public class QNames {

    /**
     *
     * @param namespaceURI
     * @param localPart
     * @param prefix
     * @return
     */
    @lombok.Builder(builderClassName="Builder")
    private static QName createQName(String namespaceURI,
                                     String localPart,
                                     String prefix) {
        return new QName(namespaceURI,localPart,prefix);
    }

    @SuppressWarnings({"java:S1068","FieldMayBeFinal","unused"})
    public static class Builder {
        private String namespaceURI=XMLConstants.NULL_NS_URI;
        private String localPart=null;
        private String prefix=XMLConstants.DEFAULT_NS_PREFIX;

        public static Builder of(QName name) {
            return builder().namespaceURI(name.getNamespaceURI()).localPart(name.getLocalPart()).prefix(name.getPrefix());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(QName name) {
        return Builder.of(name);
    }
}
