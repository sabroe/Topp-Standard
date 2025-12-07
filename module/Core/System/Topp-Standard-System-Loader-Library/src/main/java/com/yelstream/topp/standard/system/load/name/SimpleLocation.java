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

package com.yelstream.topp.standard.system.load.name;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple, lightweight, normalized location.
 * <p>
 *     This contains the name only, the name always being normalized.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-01
 */
@AllArgsConstructor(staticName="of")
@SuppressWarnings("LombokGetterMayBeUsed")
final class SimpleLocation implements Location {
    /**
     * Normalized name.
     */
    @Getter
    private final String name;

    @Override
    public Location normalize() {
        return this;
    }

    @Override
    public boolean isContent() {
        return Locations.isNameForContent(name);
    }

    @Override
    public boolean isContainer() {
        return Locations.isNameForContainer(name);
    }

    @Override
    public boolean isContainerRoot() {
        return Locations.isNameForContainerRoot(name);
    }

    /**
     * Returns name alone, allows for direct usage.
     * @return Name.
     */
    @Override
    public String toString() {
        return Locations.toString(this);
    }
}
