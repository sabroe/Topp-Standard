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

import lombok.Singular;
import lombok.experimental.UtilityClass;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.util.Map;

/**
 * Utility addressing instances of {@link Validator}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-19
 */
@UtilityClass
public class Validators {
    /**
     * Creates a validator.
     * @param schema Schema.
     * @param resourceResolver Resolver of schema resources.
     * @param errorHandler Handler of errors.
     * @param features Features to be set.
     * @param properties Properties to be set.
     * @return Validator.
     * @throws SAXException Thrown in case of SAX error.
     */
    @lombok.Builder(builderClassName="Builder")
    public static Validator createValidator(Schema schema,
                                            LSResourceResolver resourceResolver,
                                            ErrorHandler errorHandler,
                                            @Singular Map<String,Boolean> features,
                                            @Singular Map<String,Object> properties) throws SAXException {
        Validator validator=schema.newValidator();
        if (resourceResolver!=null) {
            validator.setResourceResolver(resourceResolver);
        }
        if (errorHandler!=null) {
            validator.setErrorHandler(errorHandler);
        }
        if (features!=null) {
            for (var entry: features.entrySet()) {
                validator.setFeature(entry.getKey(),entry.getValue());
            }
        }
        if (properties!=null) {
            for (var entry: properties.entrySet()) {
                validator.setProperty(entry.getKey(),entry.getValue());
            }
        }
        return validator;
    }
}
