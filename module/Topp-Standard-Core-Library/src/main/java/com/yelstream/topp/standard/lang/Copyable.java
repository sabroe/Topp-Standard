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

package com.yelstream.topp.standard.lang;

/**
 *  Creates a copy of this object.
 *  <p>
 *      Implementations must specify whether the copy is shallow or deep.
 *  </p>
 * @param <T> The type of object being copied.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-10-23
 */
@FunctionalInterface
public interface Copyable<T> {
    /**
     * Returns a copy of this object.
     * The copy may be shallow or deep depending on implementation.
     * @return Copy of this object.
     */
    T copy();
}
