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

package com.yelstream.topp.standard.load.resource.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple, lightweight resource name.
 * <p>
 *     This contains the name only.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@AllArgsConstructor(staticName="of")
final class SimpleResourceName implements ResourceName {
    @Getter
    private final String name;

    public ResourceName normalize() {
        return null;  //TO-DO: !
    }

    public boolean isFile() {
        return false;  //TO-DO: !
    }

    public boolean isDirectory() {
        return false;  //TO-DO: !
    }

    public boolean isRoot() {
        return false;  //TO-DO: !
    }

    /**
     * Returns name alone, allows for direct usage.
     * @return Name.
     */
    @Override
    public String toString() {
        return name;
    }
}
