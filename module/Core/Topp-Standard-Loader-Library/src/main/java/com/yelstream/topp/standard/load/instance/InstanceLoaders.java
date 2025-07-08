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

package com.yelstream.topp.standard.load.instance;

import com.yelstream.topp.standard.load.service.ServiceLoaders;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link InstanceLoader}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-08
 */
@UtilityClass
public class InstanceLoaders {



    /**
     *
     * @param instanceClass
     * @return
     * @param <I> Type of instance loaded.
     */
    public static <I> InstanceLoader<I> forClass(Class<I> instanceClass) {
        Supplier<List<I>> supplier=()->ServiceLoaders.loadServices(instanceClass);
        InstanceLoader<I> loader=InstanceLoader.of();
        return loader.getRegistry().add(supplier);
    }
}
