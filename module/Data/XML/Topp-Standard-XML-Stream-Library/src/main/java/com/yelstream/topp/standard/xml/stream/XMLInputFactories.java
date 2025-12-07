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

package com.yelstream.topp.standard.xml.stream;

import lombok.experimental.UtilityClass;

import javax.xml.XMLConstants;
import javax.xml.catalog.Catalog;
import javax.xml.stream.XMLInputFactory;
import java.util.Objects;

/**
 * Utility addressing instances of {@link XMLInputFactory}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-06-18
 */
@UtilityClass
public class XMLInputFactories {
    /**
     * Creates a stream factory.
     * @return stream factory.
     */
    public static XMLInputFactory createXMLInputFactory() {
        XMLInputFactory factory=XMLInputFactory.newInstance();
        disableExternalAccess(factory);
        return factory;
    }

    /**
     * Creates a stream factory.
     * @param externalAccess Indicates, if external access is to be enabled.
     * @return Stream factory.
     */
    @SuppressWarnings("java:S2755")
    public static XMLInputFactory createXMLInputFactory(boolean externalAccess) {
        XMLInputFactory factory=XMLInputFactory.newInstance();
        if (!externalAccess) {
            disableExternalAccess(factory);
        }
        return factory;
    }

    /**
     * Disables access to external entities and schemas.
     * @param factory Stream factory.
     */
    public static void disableExternalAccess(XMLInputFactory factory) {
        Objects.requireNonNull(factory);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD,"");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA,"");
    }

    /**
     * Adds an XML catalog to a stream factory.
     * @param factory Stream factory.
     * @param catalog XML catalog.
     */
    public static void addCatalog(XMLInputFactory factory,
                                  Catalog catalog) {
        Objects.requireNonNull(factory);
        Objects.requireNonNull(catalog);
        factory.setProperty("http://javax.xml.XMLConstants/property/xml-catalog",catalog);
    }

    /**
     * Creates a stream factory.
     * @param externalAccess Indicates, if external access is to be enabled.
     * @param catalog XML catalog.
     * @return Stream factory.
     */
    @lombok.Builder(builderClassName="Builder")
    private static XMLInputFactory createSchemaFactoryByBuilder(boolean externalAccess,
                                                                Catalog catalog) {
        XMLInputFactory factory=createXMLInputFactory(externalAccess);
        if (catalog!=null) {
            addCatalog(factory,catalog);
        }
        return factory;
    }

    //TO-DO: Add Lombok builder?
}