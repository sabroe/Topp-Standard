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

package com.yelstream.topp.standard.instance.util.holder;

/**
 * A container for a single item, supporting lazy initialization.
 * <p>
 *     Holds an item, which may be computed on first access to optimize resource usage.
 *     The item is typically cached after creation for subsequent access.
 * </p>
 * <p>
 *     This is thread-safe.
 * </p>
 *
 * @param <X> Type of the item held.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-09
 */
public interface Container<X> {
    /**
     * Retrieves the contained item, computing it lazily if not initialized.
     * @return Contained item.
     */
    X getItem();
}
