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

package com.yelstream.topp.standard.empress.xml.validation;

import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import java.io.IOException;

/**
 * Factory of schema instances.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@FunctionalInterface
public interface NewSchemaOperator {
    /**
     * Creates a new schema.
     * @return Schema.
     * @throws IOException Thrown in case of I/O error.
     * @throws SAXException Thrown in case of SAX error.
     */
    Schema newSchema() throws IOException, SAXException;
}
