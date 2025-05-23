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

package com.yelstream.topp.standard.xml.sax;

import lombok.AllArgsConstructor;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Basic error-handler.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-19
 */
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder")
public class BasicErrorHandler implements ErrorHandler {

    @FunctionalInterface
    interface ExceptionHandler {
        void apply(SAXParseException ex) throws SAXException;
    }

    public static final ExceptionHandler DEFAULT_HANDLER=ex -> { throw ex; };

    @lombok.Builder.Default
    private final ExceptionHandler warningHandler=DEFAULT_HANDLER;

    @lombok.Builder.Default
    private final ExceptionHandler errorHandler=DEFAULT_HANDLER;

    @lombok.Builder.Default
    private final ExceptionHandler fatalErrorHandler=DEFAULT_HANDLER;

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        if (warningHandler!=null) {
            warningHandler.apply(exception);
        }
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        if (errorHandler!=null) {
            errorHandler.apply(exception);
        }
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        if (fatalErrorHandler!=null) {
            fatalErrorHandler.apply(exception);
        }
    }
}
