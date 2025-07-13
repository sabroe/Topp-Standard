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

package com.yelstream.topp.standard.clazz.load.util;

import com.yelstream.topp.standard.clazz.load.ClassLoaders;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Standard classloader.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-13
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardClassLoader {
    System(NamedClassLoaderSupplier.of("system",ClassLoaders::getSystemClassLoader)),
    Platform(NamedClassLoaderSupplier.of("platform",ClassLoaders::getPlatformClassLoader)),
    Context(NamedClassLoaderSupplier.of("context",ClassLoaders::getContextClassLoader));

    /**
     * Source of classloader.
     */
    @Getter
    private final NamedClassLoaderSupplier supplier;

    /**
     * Gets value by name.
     * @param name Name.
     *             This is case-insensitive.
     *             This may be {@link null}.
     * @return Value.
     */
    public static StandardClassLoader valueByName(String name) {
        return Arrays.stream(values()).filter(e->e.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Gets value by name of contained supplier.
     * @param supplierName Name of contained supplier.
     *                     This is case-sensitive.
     *                     This may be {@link null}.
     * @return Value.
     */
    public static StandardClassLoader valueBySupplierName(String supplierName) {
        return Arrays.stream(values()).filter(e->e.supplier.getName().equals(supplierName)).findFirst().orElse(null);
    }
}
