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

package com.yelstream.topp.standard.load.contain;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class Containers {

    public static <X> Container<X> createContainer(X item) {
        return SimpleContainer.of(item);
    }

    public static <X> Container<X> createContainer(Supplier<X> itemSupplier) {
        return LazyContainer.of(itemSupplier);
    }

    public static <X> ResetableContainer<X> createResetableContainer(Supplier<X> itemSupplier) {
        return ResetableLazyContainer.of(itemSupplier);
    }


}
