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

package com.yelstream.topp.standard.xml.validation;

import lombok.experimental.UtilityClass;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.catalog.Catalog;
import javax.xml.validation.SchemaFactory;
import java.util.Objects;

/**
 * Utility addressing instances of {@link SchemaFactory}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@UtilityClass
public class SchemaFactories {
    /**
     * Creates a schema factory.
     * @return Schema factory.
     */
    public static SchemaFactory createSchemaFactory() {
        SchemaFactory factory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        disableExternalAccess(factory);
        return factory;
    }

    /**
     * Creates a schema factory.
     * @param externalAccess Indicates, if external access is to be enabled.
     * @return Schema factory.
     */
    @SuppressWarnings("java:S2755")
    public static SchemaFactory createSchemaFactory(boolean externalAccess) {
        SchemaFactory factory=SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (!externalAccess) {
            disableExternalAccess(factory);
        }
        return factory;
    }

    /**
     * Disables access to external entities and schemas.
     * @param factory Schema factory.
     */
    public static void disableExternalAccess(SchemaFactory factory) {
        Objects.requireNonNull(factory);
        try {
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD,"");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA,"");
        } catch (SAXException ex) {
            throw new IllegalStateException("Failure to create schema-factory!",ex);
        }
    }

    /**
     * Adds an XML catalog to a schema factory.
     * @param factory Schema factory.
     * @param catalog XML catalog.
     */
    public static void addCatalog(SchemaFactory factory,
                                  Catalog catalog) {
        Objects.requireNonNull(factory);
        Objects.requireNonNull(catalog);
        try {
            factory.setFeature("http://javax.xml.XMLConstants/feature/xml-catalog",true);
            factory.setProperty("http://javax.xml.XMLConstants/property/xml-catalog",catalog);
        } catch (SAXException ex) {
            throw new IllegalStateException("Failure add catalog to schema-factory!",ex);
        }
    }

    /**
     * Creates a schema factory.
     * @param externalAccess Indicates, if external access is to be enabled.
     * @param catalog XML catalog.
     * @return Schema factory.
     */
    @lombok.Builder(builderClassName="Builder")
    private static SchemaFactory createSchemaFactoryByBuilder(boolean externalAccess,
                                                              Catalog catalog) {
        SchemaFactory factory=createSchemaFactory(externalAccess);
        if (catalog!=null) {
            addCatalog(factory,catalog);
        }
        return factory;
    }
}
