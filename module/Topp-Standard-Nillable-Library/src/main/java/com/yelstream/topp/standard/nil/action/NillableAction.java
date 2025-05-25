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

package com.yelstream.topp.standard.nil.action;

import com.yelstream.topp.standard.nil.Nillable;

/**
 * Defines actions for handling the three states of a {@link Nillable} value: null, nil, or present.
 * <p>
 *     Implementations provide specific behavior for each state, enabling action mapping for data transfer
 *     and processing, such as XML serialization or API workflows.
 * </p>
 * @param <T> Type of value.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
public interface NillableAction<T> {
    /**
     * Handles the null state (unset, no value or element provided).
     */
    void onNull();

    /**
     * Handles the nil state (explicit null, e.g., xsi:nil="true" with possible attributes).
     * @param value The nil value, which may carry metadata (e.g., attributes in a JAXBElement).
     */
    void onNil(T value);

    /**
     * Handles the present state (actual non-nil value).
     * @param value The present value.
     */
    void onPresent(T value);
}
