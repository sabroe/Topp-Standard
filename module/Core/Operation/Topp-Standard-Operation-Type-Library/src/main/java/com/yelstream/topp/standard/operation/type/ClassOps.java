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

package com.yelstream.topp.standard.operation.type;

import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * Utilities addressing instances of {@link Class}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-21
 */
@UtilityClass
public class ClassOps {
    /**
     * Returns the fully qualified name of a class.
     * @param clazz Class whose name should be returned.
     *              May be {@code null}.
     * @return Fully qualified name of the class.
     *         Is {@code null} if {@code clazz} is {@code null}.
     */
    public static String getName(Class<?> clazz) {
        return clazz == null ? null : clazz.getName();
    }
}
