/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.io;

import lombok.experimental.UtilityClass;

import java.io.IOException;

/**
 * Utility addressing intances of {@link IOException}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-11
 */
@UtilityClass
public class IOExceptions {

    /**
     * Converts an exception by wrapping it into new I/O exception.
     * @param cause Wrapped exception.
     * @return Created exception.
     */
    public static IOException create(Exception cause) {
        return new IOException(cause);
    }

    /**
     * Converts an exception by wrapping it into new I/O exception.
     * @param message Message for created exception..
     * @param cause Wrapped exception.
     * @return Created exception.
     */
    public static IOException create(String message,
                                     Exception cause) {
        return new IOException(message,cause);
    }
}
