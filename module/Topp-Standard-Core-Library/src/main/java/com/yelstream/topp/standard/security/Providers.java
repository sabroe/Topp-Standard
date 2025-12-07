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

package com.yelstream.topp.standard.security;

import lombok.experimental.UtilityClass;

import java.security.Provider;
import java.security.Security;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility addressing {@link Provider} instances.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-04-04
 */
@UtilityClass
public class Providers {
    @lombok.Builder(builderClassName="Builder")
    private static Provider getProvider(List<Provider> providers,
                                        Function<List<Provider>,Provider> selector) {
        if (providers==null) {
            providers=List.of(Security.getProviders());
        }
        return selector.apply(providers);
    }

    public static class Builder {
        public Builder criterion(String criterion) {
            return providers(List.of(Security.getProviders(criterion)));
        }

        public Builder filter(Map<String,String> filter) {
            return providers(List.of(Security.getProviders(filter)));
        }

        public Builder select(Predicate<Provider> predicate) {
            return selector(providers->providers.stream().filter(predicate).findFirst().orElseThrow(()->new IllegalStateException("Failure to find provider!")));
        }
    }
}
